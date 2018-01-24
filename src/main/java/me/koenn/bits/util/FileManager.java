package me.koenn.bits.util;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class FileManager {

    private final String path;
    private FileConfiguration configuration;
    private File file;

    public FileManager(String path) {
        this.path = path;
        this.init();
    }

    private void init() {
        File root = new File("plugins/Bits");
        this.file = new File(root, path);

        if (!this.file.exists()) {
            if (this.file.getParentFile().mkdirs()) {
                try {
                    if (this.file.createNewFile()) {
                        this.configuration = YamlConfiguration.loadConfiguration(this.file);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        this.configuration = YamlConfiguration.loadConfiguration(this.file);
    }

    public void write(String path, Object value) {
        this.configuration.set(path, value);
        try {
            this.configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object read(String path) {
        return this.configuration.get(path);
    }

    public boolean has(String path) {
        return this.configuration.contains(path);
    }

    public Set<String> getKeys(String path) {
        if (this.configuration.getConfigurationSection(path) == null) {
            return null;
        }
        return this.configuration.getConfigurationSection(path).getKeys(false);
    }
}
