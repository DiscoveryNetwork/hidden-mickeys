package nl.parrotlync.hiddenmickeys.model;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.Serializable;
import java.util.Objects;

public class HiddenMickey implements Serializable {
    private final Integer x;
    private final Integer y;
    private final Integer z;
    private final String world;

    public HiddenMickey(Location location) {
        this.x = (int) location.getX();
        this.y = (int) location.getY();
        this.z = (int) location.getZ();
        this.world = Objects.requireNonNull(location.getWorld()).getName();
    }

    public Location getLocation() {
        return new Location(Bukkit.getWorld(this.world), x, y, z);
    }
}
