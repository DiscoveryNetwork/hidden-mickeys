package nl.parrotlync.hiddenmickeys.command;

import nl.parrotlync.hiddenmickeys.HiddenMickeys;
import nl.parrotlync.hiddenmickeys.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class HiddenMickeysCommandExecutor implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender.hasPermission("hiddenmickeys.admin")) {
            Player player = (Player) sender;
            ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
            SkullMeta skull = (SkullMeta) item.getItemMeta();
            skull.setDisplayName("§bHiddenMickey");
            skull.setOwner(HiddenMickeys.getInstance().getConfig().getString("skull-owner"));
            item.setItemMeta(skull);
            player.getInventory().addItem(item);
            ChatUtil.sendMessage(sender, "§7Given §bHiddenMickey §7item to " + player.getName(), true);
            return true;
        }

        return help(sender);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> suggestions = new ArrayList<>();

        if (args.length == 1) {
            suggestions.add("get");
            StringUtil.copyPartialMatches(args[0], suggestions, new ArrayList<>());
        }

        return suggestions;
    }

    private boolean help(CommandSender sender) {
        if (sender.hasPermission("hiddenmickeys.admin")) {
            ChatUtil.sendMessage(sender, "§7Use §9/hm get §7to get a HiddenMickey!", true);
        } else {
            ChatUtil.sendMessage(sender, "§cYou do not have permission to do that!", true);
        }
        return true;
    }
}
