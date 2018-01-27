package me.koenn.bits.listeners;

import com.fasterxml.jackson.databind.JsonNode;
import com.maxmind.db.CHMCache;
import com.maxmind.db.Reader;
import me.koenn.bits.Ref;
import me.koenn.bits.command.commands.ChangelogCommand;
import me.koenn.bits.player.CPlayer;
import me.koenn.bits.player.CPlayerRegistry;
import mkremins.fanciful.FancyMessage;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;

public class PlayerListener implements Listener {

    private Reader reader;

    public PlayerListener() {
        File database = new File("plugins/Bits", "GeoLite2-Country.mmdb");
        this.reader = null;
        try {
            reader = new Reader(database, new CHMCache());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String ISO = null;

        try {
            if (reader == null) return;
            JsonNode node = reader.get(player.getAddress().getAddress());
            if (node != null) node = node.get("country").get("iso_code");
            if (node != null) ISO = node.textValue();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        if (ISO == null) ISO = "*";

        if (!player.hasPlayedBefore()) {
            event.setJoinMessage(Ref.MSG_JOIN_NEW.replace("{ISO}", ISO).replace("{player}", player.getName()));

            player.getInventory().addItem(new ItemStack(Material.CAKE, 1));
            player.getInventory().addItem(new ItemStack(Material.BED, 1));
            player.getInventory().addItem(new ItemStack(Material.GOLD_SPADE, 1));
            player.getInventory().addItem(new ItemStack(Material.STONE_AXE, 1));

            player.performCommand("warp spawn");

            CPlayer cPlayer = CPlayerRegistry.getCPlayer(player.getUniqueId());
            if (cPlayer != null) {
                cPlayer.set("version", ChangelogCommand.VERSION);
            }
        } else {
            event.setJoinMessage(Ref.MSG_JOIN.replace("{ISO}", ISO).replace("{player}", player.getDisplayName()));

            CPlayer cPlayer = CPlayerRegistry.getCPlayer(player.getUniqueId());
            if (cPlayer != null && !cPlayer.getLatestVersion().equals(ChangelogCommand.VERSION)) {
                new FancyMessage("There have been changes to the server, use /changelog to check them out!")
                        .color(ChatColor.YELLOW)
                        .style(ChatColor.BOLD)
                        .tooltip("Click to view the changelog!")
                        .command("/changelog")
                        .send(player);
                cPlayer.set("version", ChangelogCommand.VERSION);
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage(Ref.MSG_QUIT.replace("{player}", event.getPlayer().getDisplayName()));
    }
}
