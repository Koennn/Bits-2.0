package me.koenn.bits.warps;

import me.koenn.bits.util.FileManager;
import org.bukkit.Location;

public class Warp {

    private final String name;
    private final Location location;
    private final FileManager manager;

    public Warp(String name, Location location, FileManager manager) {
        this.name = name;
        this.location = location;
        this.manager = manager;

        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();
        String world = location.getWorld().getName();

        manager.write("warps." + name + ".x", x);
        manager.write("warps." + name + ".y", y);
        manager.write("warps." + name + ".z", z);
        manager.write("warps." + name + ".world", world);
    }

    public String getName() {
        return this.name;
    }

    public Location getLocation() {
        return this.location;
    }

    public void delete() {
        this.manager.write("warps." + name, null);
    }
}
