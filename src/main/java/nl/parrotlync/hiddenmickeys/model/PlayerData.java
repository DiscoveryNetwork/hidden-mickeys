package nl.parrotlync.hiddenmickeys.model;

import nl.parrotlync.hiddenmickeys.HiddenMickeys;
import org.bukkit.Location;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerData implements Serializable {
    private final UUID player;
    private final List<HiddenMickey> hiddenMickeyList;

    public PlayerData(UUID player, List<Location> locations) {
        this.player = player;
        hiddenMickeyList = new ArrayList<>();
        for (Location location : locations) {
            hiddenMickeyList.add(new HiddenMickey(location));
        }
    }

    public UUID getPlayer() {
        return player;
    }

    public List<Location> getLocations() {
        List<Location> locations = new ArrayList<>();
        for (HiddenMickey mickey : hiddenMickeyList) {
            if (HiddenMickeys.getInstance().getMickeyManager().exists(mickey.getLocation())) {
                locations.add(mickey.getLocation());
            }
        }
        return locations;
    }
}
