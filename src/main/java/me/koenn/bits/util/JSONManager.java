package me.koenn.bits.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import me.koenn.bits.Bits;
import org.bukkit.plugin.Plugin;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@SuppressWarnings({"ResultOfMethodCallIgnored", "unused"})
public class JSONManager {

    private static boolean verbose = true;
    private final Plugin plugin;
    private final JSONObject defaultBody;
    private File jsonFile;
    private JSONObject body;

    public JSONManager(Plugin plugin, String fileName) {
        this(plugin, fileName, true, new JSONObject());
    }

    public JSONManager(Plugin plugin, String fileName, boolean inDataFolder, JSONObject defaultBody) {
        this.defaultBody = defaultBody;
        this.plugin = plugin;
        if (inDataFolder) {
            if (!this.plugin.getDataFolder().exists()) {
                this.plugin.getDataFolder().mkdir();
            }
            this.jsonFile = new File(this.plugin.getDataFolder(), fileName);
        } else {
            this.jsonFile = new File(fileName);
        }

        this.reload();
    }

    public static void setVerbose(boolean verbose) {
        JSONManager.verbose = verbose;
    }

    public void saveBodyToFile() {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonElement je = new JsonParser().parse(this.body.toJSONString());
            FileWriter writer = new FileWriter(this.jsonFile);
            writer.write(gson.toJson(je));
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            if (verbose) {
                Bits.log("Failed to save  '" + this.jsonFile.getName() + "' for plugin '" + this.plugin.getName() + "'.");
            }
        }
    }

    public JSONObject getBody() {
        return body;
    }

    public void setInBody(String key, Object value) {
        this.body.put(key, value);
        this.saveBodyToFile();
    }

    public Object getFromBody(String key) {
        return this.body.get(key);
    }

    public void reload() {
        if (!this.jsonFile.exists()) {
            try {
                this.jsonFile.createNewFile();
                if (verbose) {
                    Bits.log("Generating new body in '" + this.jsonFile.getName() + "' for plugin '" + this.plugin.getName() + "'.");
                }
                this.body = this.defaultBody;
                this.saveBodyToFile();
            } catch (IOException e) {
                if (verbose) {
                    Bits.log("Failed to create '" + this.jsonFile.getName() + "' for plugin '" + this.plugin.getName() + "'.");
                }
            }
        }
        this.jsonFile = new File(this.jsonFile.getPath());

        Object obj;
        try {
            JSONParser parser = new JSONParser();
            obj = parser.parse(new FileReader(this.jsonFile));
            this.body = (JSONObject) obj;
        } catch (IOException ex) {
            if (verbose) {
                Bits.log("Failed to load '" + this.jsonFile.getName() + "' for plugin '" + this.plugin.getName() + "'.");
            }
        } catch (ParseException | ClassCastException ex) {
            if (verbose) {
                Bits.log("Invalid JSON format, generating new body in '" + this.jsonFile.getName() + "' for plugin '" + this.plugin.getName() + "'.");
            }
            this.body = this.defaultBody;
            this.saveBodyToFile();
        }
        if (verbose) {
            Bits.log("Plugin '" + this.plugin.getName() + "' loaded JSONManager '" + this.toString().split("@")[1] + "'.");
        }
    }
}
