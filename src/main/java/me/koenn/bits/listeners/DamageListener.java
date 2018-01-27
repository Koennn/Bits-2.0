package me.koenn.bits.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageListener implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (isPvpDamage(event.getDamager(), event.getEntity())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onEntityCombustByEntity(EntityCombustByEntityEvent event) {
        if (isPvpDamage(event.getCombuster(), event.getEntity())) {
            event.setCancelled(true);
        }
    }

    private boolean isPvpDamage(Entity source, Entity target) {
        // Returns true if the target is a player, and the source is either a player or a projectile shot by a player.
        return target instanceof Player && (source instanceof Player || (source instanceof Projectile && ((Projectile) source).getShooter() instanceof Player));
    }
}
