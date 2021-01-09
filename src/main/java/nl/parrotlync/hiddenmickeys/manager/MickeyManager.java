package nl.parrotlync.hiddenmickeys.manager;

import nl.parrotlync.hiddenmickeys.HiddenMickeys;
import nl.parrotlync.hiddenmickeys.model.HiddenMickey;
import nl.parrotlync.hiddenmickeys.util.DataUtil;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class MickeyManager {
    private List<Location> hiddenMickeys;
    private final String path = "plugins/HiddenMickeys/locations.data";

    public void addMickey(Location location) {
        if (!hiddenMickeys.contains(location)) {
            hiddenMickeys.add(location);
        }
    }

    public void removeMickey(Location location) {
        hiddenMickeys.remove(location);
    }

    public Location getMickey(Integer index) {
        if (index < hiddenMickeys.size()) {
            return hiddenMickeys.get(index);
        }
        return null;
    }

    public boolean exists(Location location) {
        return hiddenMickeys.contains(location);
    }

    public Integer getSize() {
        return hiddenMickeys.size();
    }

    public void load() {
        List<HiddenMickey> mickeys = DataUtil.loadObjectFromPath(path);
        hiddenMickeys = new ArrayList<>();
        if (mickeys != null) {
            for (HiddenMickey mickey : mickeys) {
                try {
                    if (mickey.getLocation().getBlock().getType() == Material.SKULL) {
                        hiddenMickeys.add(mickey.getLocation());
                    }
                } catch (NullPointerException e) {
                    HiddenMickeys.getInstance().getLogger().info("There was an error loading the HiddenMickey at " + mickey.getLocation().toString());
                }
            }
            HiddenMickeys.getInstance().getLogger().info("Loaded " + hiddenMickeys.size() + " HiddenMickeys from file.");
        }
    }

    public void save() {
        if (!hiddenMickeys.isEmpty()) {
            List<HiddenMickey> mickeys = new ArrayList<>();
            for (Location location : hiddenMickeys) {
                mickeys.add(new HiddenMickey(location));
            }
            DataUtil.saveObjectToPath(mickeys, path);
            HiddenMickeys.getInstance().getLogger().info("Saved " + hiddenMickeys.size() + " HiddenMickeys to file.");
        }
    }
}
