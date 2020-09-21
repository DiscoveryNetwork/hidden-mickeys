package nl.parrotlync.hiddenmickeys;

import nl.parrotlync.hiddenmickeys.command.HiddenMickeysCommandExecutor;
import nl.parrotlync.hiddenmickeys.listener.MickeyListener;
import nl.parrotlync.hiddenmickeys.manager.MickeyManager;
import nl.parrotlync.hiddenmickeys.manager.PlayerManager;
import nl.parrotlync.hiddenmickeys.placeholder.HiddenMickeyExpansion;
import org.bukkit.plugin.java.JavaPlugin;

public class HiddenMickeys extends JavaPlugin {
    private static HiddenMickeys instance;
    private PlayerManager playerManager;
    private MickeyManager mickeyManager;

    public HiddenMickeys() {
        instance = this;
        playerManager = new PlayerManager();
        mickeyManager = new MickeyManager();
    }

    @Override
    public void onEnable() {
        getConfig().addDefault("skull-owner", "MickeyMouse");
        getConfig().options().copyDefaults(true);
        saveConfig();
        getCommand("hiddenmickeys").setExecutor(new HiddenMickeysCommandExecutor());
        getServer().getPluginManager().registerEvents(new MickeyListener(), this);
        new HiddenMickeyExpansion().register();
        mickeyManager.load();
        playerManager.load();
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
