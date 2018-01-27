package me.koenn.bits.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.Collections;
import java.util.List;

public class KallumsKipper implements Listener {

    private static final ItemStack FISH = makeItemStack(
            Material.RAW_FISH, 1, (short) 1,
            "&b&lKallum's Kipper",
            Collections.singletonList(ChatColor.RESET + "Use this on Kallum.")
    );

    private static ItemStack makeItemStack(Material type, int amount, short meta, String displayName, List<String> lore) {
        ItemStack itemStack = new ItemStack(type, amount, meta);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', displayName));
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

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

        if (item == null || !item.isSimilar(FISH) || !entity.getName().equals("KallumJ")) {
            return;
        }

        double horizontalPower = 3.0;
        double verticalPower = 0.8;

        Vector launch = damager.getEyeLocation()
                .getDirection()
                .setY(0)
                .normalize()
                .multiply(horizontalPower)
                .setY(verticalPower);

        entity.setVelocity(entity.getVelocity().add(launch));

        damager.getWorld().playSound(entity.getLocation(), Sound.BLOCK_NOTE_BASS, 1.0F, 0.5F);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        if (event.getMessage().startsWith("/getfish") && event.getPlayer().isOp()) {
            event.getPlayer().getInventory().addItem(FISH);
            event.setCancelled(true);
        }
    }
}
