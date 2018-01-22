package me.koenn.bits.util;

import me.koenn.bits.Bits;
import me.koenn.bits.Ref;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public final class Teleporter {

    public static void teleport(Player player, Location location) {
        location.getChunk().load(true);

        player.sendMessage(Ref.TELEPORTING);
        player.getWorld().spawnParticle(Particle.PORTAL, player.getLocation(), 250, 0.5, 0.5, 0.5);
        player.getWorld().spawnParticle(Particle.PORTAL, location, 250, 0.5, 0.5, 0.5);
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_PORTAL_TRIGGER, 0.2F, 1.5F);

        int time = player.hasPermission(Ref.PERM_BYPASSCOOLDOWN) ? Ref.TELEPORT_WARMUP_DONOR : Ref.TELEPORT_WARMUP;

        Bukkit.getScheduler().scheduleSyncDelayedTask(Bits.getInstance(), () -> {
            player.teleport(location);
            player.sendMessage(Ref.TELEPORTED);
            player.getWorld().spawnParticle(Particle.PORTAL, player.getLocation(), 250, 0.5, 0.5, 0.5);
            player.getWorld().spawnParticle(Particle.PORTAL, player.getLocation(), 50, 0.1, 0.1, 0.1);
            player.getWorld().playSound(player.getLocation(), Sound.BLOCK_PORTAL_TRAVEL, 0.2F, 0.8F);
        }, time);
    }
}
