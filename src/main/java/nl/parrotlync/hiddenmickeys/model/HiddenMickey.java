package nl.parrotlync.hiddenmickeys.model;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.Serializable;

public class HiddenMickey implements Serializable {
    private Integer x;
    private Integer y;
    private Integer z;
    private String world;

    public HiddenMickey(Location location) {
        this.x = (int) location.getX();
        this.y = (int) location.getY();
        this.z = (int) location.getZ();
        this.world = location.getWorld().getName();
    }

    public Location getLocation() {
        return new Location(Bukkit.getWorld(this.world), x, y, z);
    }
}
