package nl.parrotlync.hiddenmickeys.listener;

import nl.parrotlync.hiddenmickeys.HiddenMickeys;
import nl.parrotlync.hiddenmickeys.util.ChatUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class MickeyListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getBlock().getType() == Material.SKULL) {
            if (event.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals("§bHiddenMickey")) {
                if (event.getPlayer().hasPermission("hiddenmickeys.admin")) {
                    HiddenMickeys.getInstance().getMickeyManager().addMickey(event.getBlock().getLocation());
                    ChatUtil.sendMessage(event.getPlayer(), "§7You created a new §aHiddenMickey§7!", true);
                } else {
                    ChatUtil.sendMessage(event.getPlayer(), "§cYou don't have permission to do that!", true);
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getType() == Material.SKULL) {
            Location location = event.getBlock().getLocation();
            if (HiddenMickeys.getInstance().getMickeyManager().exists(location)) {
                if (event.getPlayer().hasPermission("hiddenmickeys.admin")) {
                    HiddenMickeys.getInstance().getMickeyManager().removeMickey(event.getBlock().getLocation());
                    HiddenMickeys.getInstance().getPlayerManager().removeLocation(event.getPlayer(), event.getBlock().getLocation());
                    ChatUtil.sendMessage(event.getPlayer(), "§7You removed a §cHiddenMickey§7!", true);
                } else {
                    ChatUtil.sendMessage(event.getPlayer(), "§cYou don't have permission to do that!", true);
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null) {
            if (event.getClickedBlock().getType() == Material.SKULL && event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getHand() == EquipmentSlot.HAND) {
                Location location = event.getClickedBlock().getLocation();
                if (HiddenMickeys.getInstance().getMickeyManager().exists(location)) {
                    if (!HiddenMickeys.getInstance().getPlayerManager().exists(event.getPlayer(), location)) {
                        HiddenMickeys.getInstance().getPlayerManager().addLocation(event.getPlayer(), location);
                        Integer found = HiddenMickeys.getInstance().getPlayerManager().getSize(event.getPlayer());
                        Integer total = HiddenMickeys.getInstance().getMickeyManager().getSize();
                        event.getPlayer().spawnParticle(Particle.VILLAGER_HAPPY, location, 30, 1, 1, 1);
                        ChatUtil.sendMessage(event.getPlayer(), "§7(§6§l!§7) Awesome, you found a §bHiddenMickey§7! (" + found + "/" + total + ")", false);
                    } else {
                        ChatUtil.sendMessage(event.getPlayer(), "§7(§6§l!§7) §cYou have already found this §bHiddenMickey§c!", false);
                    }
                }
            }
        }
    }
}
