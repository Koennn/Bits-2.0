package me.koenn.bits.listeners;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class FishSlapListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) {
            return;
        }
        Player damager = (Player) event.getDamager();

        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        Player entity = (Player) event.getEntity();

        if (damager.getLocation().getWorld().getEnvironment() != World.Environment.NORMAL) {
            return;
        }

        ItemStack item = damager.getInventory().getItemInMainHand();
        ItemStack fish = new ItemStack(Material.RAW_FISH, 1);

        if (item == null || !item.isSimilar(fish)) {
            return;
        }

        double horizontalPower = 0.5;
        double verticalPower = 0.2;

        Vector launch = damager.getEyeLocation()
                .getDirection()
                .setY(0)
                .normalize()
                .multiply(horizontalPower)
                .setY(verticalPower);

        entity.setVelocity(entity.getVelocity()
                .add(launch));

        damager.getWorld().playSound(damager.getLocation(), Sound.BLOCK_NOTE_BELL, 0.2F, 1.5F);
        damager.getInventory().removeItem(fish);
    }
}
