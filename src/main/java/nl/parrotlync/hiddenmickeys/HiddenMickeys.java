package nl.parrotlync.hiddenmickeys;

import nl.parrotlync.hiddenmickeys.command.HiddenMickeysCommandExecutor;
import nl.parrotlync.hiddenmickeys.listener.MickeyListener;
import nl.parrotlync.hiddenmickeys.manager.MickeyManager;
import nl.parrotlync.hiddenmickeys.manager.PlayerManager;
import nl.parrotlync.hiddenmickeys.placeholder.HiddenMickeyExpansion;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class HiddenMickeys extends JavaPlugin {
    private static HiddenMickeys instance;
    private final PlayerManager playerManager;
    private final MickeyManager mickeyManager;

    public HiddenMickeys() {
        instance = this;
        playerManager = new PlayerManager();
        mickeyManager = new MickeyManager();
    }

    @Override
    public void onEnable() {
        getConfig().addDefault("skull-owner", "af3c2145-8974-41ea-9737-34f259531353");
        getConfig().options().copyDefaults(true);
        saveConfig();
        Objects.requireNonNull(getCommand("hiddenmickeys")).setExecutor(new HiddenMickeysCommandExecutor());
        getServer().getPluginManager().registerEvents(new MickeyListener(), this);

        // Put loading in a scheduler to avoid null errors when the world isn't loaded
        new BukkitRunnable() {
            @Override
            public void run() {
                new HiddenMickeyExpansion().register();
                mickeyManager.load();
                playerManager.load();
            }
        }.runTaskLater(this, 0);
        getLogger().info("HiddenMickeys is now enabled!");
    }

    @Override
    public void onDisable() {
        mickeyManager.save();
        playerManager.save();
        getLogger().info("HiddenMickeys is now disabled!");
    }

    public static HiddenMickeys getInstance() { return instance; }

    public PlayerManager getPlayerManager() { return playerManager; }

    public MickeyManager getMickeyManager() { return mickeyManager; }
}
