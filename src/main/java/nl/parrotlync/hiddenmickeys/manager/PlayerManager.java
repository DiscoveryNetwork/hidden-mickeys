package nl.parrotlync.hiddenmickeys.manager;

import nl.parrotlync.hiddenmickeys.HiddenMickeys;
import nl.parrotlync.hiddenmickeys.model.PlayerData;
import nl.parrotlync.hiddenmickeys.util.DataUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PlayerManager {
    private HashMap<UUID, List<Location>> playerData;
    private final String path = "plugins/HiddenMickeys/players.data";

    public void addLocation(Player player, Location location) {
        if (!playerData.containsKey(player.getUniqueId())) {
            playerData.put(player.getUniqueId(), new ArrayList<>());
        }
        if (!playerData.get(player.getUniqueId()).contains(location)) {
            playerData.get(player.getUniqueId()).add(location);
        }
    }

    public void removeLocation(Player player, Location location) {
        if (!playerData.containsKey(player.getUniqueId())) { return; }
        playerData.get(player.getUniqueId()).remove(location);
    }

    public boolean exists(Player player, Location location) {
        if (!playerData.containsKey(player.getUniqueId())) { return false; }
        return playerData.get(player.getUniqueId()).contains(location);
    }

    public Integer getSize(Player player) {
        if (!playerData.containsKey(player.getUniqueId())) { return 0; }
        return playerData.get(player.getUniqueId()).size();
    }

    public void load() {
        List<PlayerData> players = DataUtil.loadObjectFromPath(path);
        playerData = new HashMap<>();
        if (players != null) {
            for (PlayerData player : players) {
                playerData.put(player.getPlayer(), player.getLocations());
            }
            HiddenMickeys.getInstance().getLogger().info("Loaded player data from file.");
        }
    }

    public void save() {
        if (!playerData.isEmpty()) {
            List<PlayerData> players = new ArrayList<>();
            for (UUID player : playerData.keySet()) {
                players.add(new PlayerData(player, playerData.get(player)));
            }
            DataUtil.saveObjectToPath(players, path);
            HiddenMickeys.getInstance().getLogger().info("Saved player data to file.");
        }
    }
}
