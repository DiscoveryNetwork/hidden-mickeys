package nl.parrotlync.hiddenmickeys.manager;

import nl.parrotlync.hiddenmickeys.HiddenMickeys;
import nl.parrotlync.hiddenmickeys.model.HiddenMickey;
import nl.parrotlync.hiddenmickeys.util.DataUtil;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class MickeyManager {
    private List<Location> hiddenMickeys;
    private String path = "plugins/HiddenMickeys/locations.data";

    public void addMickey(Location location) {
        if (!hiddenMickeys.contains(location)) {
            hiddenMickeys.add(location);
        }
    }

    public void removeMickey(Location location) {
        hiddenMickeys.remove(location);
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
                hiddenMickeys.add(mickey.getLocation());
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
