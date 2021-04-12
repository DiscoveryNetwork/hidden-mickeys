package nl.parrotlync.hiddenmickeys.command;

import nl.parrotlync.hiddenmickeys.HiddenMickeys;
import nl.parrotlync.hiddenmickeys.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class HiddenMickeysCommandExecutor implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        if (args.length == 0) {
            return help(sender);
        }

        if (sender.hasPermission("hiddenmickeys.admin")) {
            if (args[0].equalsIgnoreCase("get")) {
                Player player = (Player) sender;
                ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1);
                SkullMeta skull = (SkullMeta) item.getItemMeta();
                assert skull != null;
                skull.setDisplayName("§bHiddenMickey");
                skull.setOwningPlayer(Bukkit.getOfflinePlayer(UUID.fromString(Objects.requireNonNull(HiddenMickeys.getInstance().getConfig().getString("skull-owner")))));
                item.setItemMeta(skull);
                player.getInventory().addItem(item);
                ChatUtil.sendMessage(sender, "§7Given §bHiddenMickey §7item to " + player.getName(), true);
                return true;
            }

            if (args[0].equalsIgnoreCase("reload")) {
                HiddenMickeys.getInstance().getMickeyManager().save();
                HiddenMickeys.getInstance().getPlayerManager().save();
                HiddenMickeys.getInstance().getMickeyManager().load();
                HiddenMickeys.getInstance().getPlayerManager().load();
                ChatUtil.sendMessage(sender, "§7HiddenMickeys has been reloaded", true);
                return true;
            }

            if (args[0].equalsIgnoreCase("tp") && args.length == 2) {
                Location hiddenMickey = HiddenMickeys.getInstance().getMickeyManager().getMickey(Integer.parseInt(args[1]));
                Player player = (Player) sender;
                if (hiddenMickey != null) {
                    player.teleport(hiddenMickey);
                    return true;
                }
            }
        }

        return help(sender);
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        List<String> suggestions = new ArrayList<>();

        if (args.length == 1 && sender.hasPermission("hiddenmickeys.admin")) {
            suggestions.add("get");
            suggestions.add("help");
            suggestions.add("reload");
            StringUtil.copyPartialMatches(args[0], suggestions, new ArrayList<>());
        }

        return suggestions;
    }

    private boolean help(CommandSender sender) {
        if (sender.hasPermission("hiddenmickeys.admin")) {
            ChatUtil.sendMessage(sender, "§7Use §a/hm get §7to get a HiddenMickey!", true);
        } else {
            ChatUtil.sendMessage(sender, "§cYou do not have permission to do that!", true);
        }
        return true;
    }
}
