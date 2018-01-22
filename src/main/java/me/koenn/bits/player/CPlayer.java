package me.koenn.bits.player;

import me.koenn.bits.Bits;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONObject;

import java.util.UUID;

/**
 * Custom <code>Player</code> class for storing <code>PlayerData</code>.
 * <p>
 * Copyright (C) Koenn - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Koen Willemse, June 2017
 */
@SuppressWarnings("unused")
public class CPlayer {

    private final UUID uuid;
    private final PlayerData playerData;

    public CPlayer(UUID uuid) {
        this.uuid = uuid;
        this.playerData = new PlayerData();
        this.set("UUID", this.uuid);
    }

    public CPlayer(JSONObject jsonObject) {
        this(UUID.fromString((String) jsonObject.get("UUID")));
        jsonObject.keySet().stream().filter(key -> !key.equals("UUID")).forEach(key -> this.playerData.put((String) key, (String) jsonObject.get(key)));
    }

    public boolean contains(String key) {
        for (String string : this.playerData.keySet()) {
            if (string.equals(key)) {
                return true;
            }
        }
        return false;
    }

    public void sendMessage(String message) {
        if (!this.isOnline()) {
            throw new IllegalArgumentException("Player is not online!");
        }
        if (message == null) {
            return;
        }
        this.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public boolean hasField(String key) {
        return this.playerData.containsKey(key);
    }

    public void set(String key, Object value) {
        this.playerData.put(key, value.toString());
        Bits.getCPlayerRegistry().savePlayers();
    }

    public String get(String key) {
        return this.playerData.get(key);
    }

    public UUID getUUID() {
        return uuid;
    }

    public OfflinePlayer getOfflinePlayer() {
        return Bukkit.getOfflinePlayer(this.uuid);
    }

    public Player getPlayer() {
        Player player = Bukkit.getPlayer(uuid);
        return player == null || !player.isOnline() ? null : player;
    }

    public void giveItem(ItemStack... items) {
        for (ItemStack item : items) {
            if (item == null) {
                continue;
            }
            if (this.getPlayer().getInventory().firstEmpty() != -1) {
                this.getPlayer().getInventory().addItem(item);
            } else {
                Location location = this.getPlayer().getLocation();
                Item droppedItem = location.getWorld().dropItem(location, item);
                droppedItem.setVelocity(droppedItem.getVelocity().zero());
            }
        }
    }

    public boolean isOnline() {
        return this.getPlayer() != null;
    }

    public String getName() {
        return this.getOfflinePlayer().getName();
    }

    public PlayerData getPlayerData() {
        return playerData;
    }
}
