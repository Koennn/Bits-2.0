package me.koenn.bits.warps;

import me.koenn.bits.util.FileManager;
import org.bukkit.Location;

public class Warp {

    private final String name;
    private final Location location;
    private final FileManager manager;
    private String category;

    public Warp(String name, Location location, FileManager manager) {
        this(name, location, manager, null);
    }

    public Warp(String name, Location location, FileManager manager, String category) {
        this.name = name;
        this.location = location;
        this.manager = manager;
        this.category = category;

        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();
        String world = location.getWorld().getName();

        manager.write("warps." + name + ".x", x);
        manager.write("warps." + name + ".y", y);
        manager.write("warps." + name + ".z", z);
        manager.write("warps." + name + ".world", world);

        if (category != null) {
            manager.write("warps." + name + ".category", category);
        }
    }

    public String getName() {
        return this.name;
    }

    public Location getLocation() {
        return this.location;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;

        manager.write("warps." + this.name + ".category", category);
    }

    public boolean hasCategory() {
        return this.category != null;
    }

    public void delete() {
        this.manager.write("warps." + this.name, null);
    }
}
