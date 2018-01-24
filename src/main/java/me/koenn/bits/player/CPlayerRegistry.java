package me.koenn.bits.player;

import me.koenn.bits.Bits;
import me.koenn.bits.util.JSONManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

public final class CPlayerRegistry implements Listener {

    private static JSONManager json;
    private final ArrayList<CPlayer> cPlayers;

    public CPlayerRegistry() {
        Bits.log("Loading CPlayerRegistry...");
        this.cPlayers = new ArrayList<>();
        json = new JSONManager(Bits.getInstance(), "players.json");

        Bukkit.getPluginManager().registerEvents(this, Bits.getInstance());
    }

    public static void loadCPlayers() {
        int playerCount = 0;
        JSONArray playerList;

        try {
            playerList = (JSONArray) json.getBody().get("players");
        } catch (NullPointerException ex) {
            return;
        }
        if (playerList == null || playerList.isEmpty()) {
            return;
        }

        for (Object object : playerList) {
            CPlayerRegistry.registerCPlayer(new CPlayer((JSONObject) object));
            playerCount++;
        }

        Bits.getCPlayerRegistry().savePlayers();
        Bits.log("Loaded " + playerCount + " CPlayers.");
    }

    public static void registerCPlayer(CPlayer cPlayer) {
        for (CPlayer player : Bits.getCPlayerRegistry().cPlayers) {
            if (player.getUUID().equals(cPlayer.getUUID())) {
                player.join();
                return;
            }
        }
        Bits.getCPlayerRegistry().cPlayers.add(cPlayer);
    }

    public static CPlayer getCPlayer(UUID uuid) {
        for (CPlayer player : Bits.getCPlayerRegistry().cPlayers) {
            if (player.getUUID().equals(uuid)) {
                return player;
            }
        }
        return null;
    }

    public static CPlayer getCPlayer(String name) {
        for (CPlayer player : Bits.getCPlayerRegistry().cPlayers) {
            if (player.getName().equals(name)) {
                return player;
            }
        }
        return null;
    }

    public static CPlayer getCPlayerByNick(String nick) {
        for (CPlayer player : Bits.getCPlayerRegistry().cPlayers) {
            if (player.getNickname() != null && player.getNickname().equals(nick)) {
                return player;
            }
        }
        return null;
    }

    public static ArrayList<CPlayer> getCPlayers() {
        return Bits.getCPlayerRegistry().cPlayers;
    }

    public void savePlayers() {
        JSONManager manager = json;
        JSONArray playerList = new JSONArray();
        for (CPlayer cPlayer : cPlayers) {
            JSONObject object = new JSONObject();
            for (String key : cPlayer.getPlayerData().keySet()) {
                object.put(key, cPlayer.getPlayerData().get(key));
            }
            playerList.add(object);
        }
        manager.getBody().put("players", playerList);
        manager.saveBodyToFile();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event) {
        CPlayerRegistry.registerCPlayer(new CPlayer(event.getPlayer().getUniqueId()));
        Bits.getCPlayerRegistry().savePlayers();
    }
}
