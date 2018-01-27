package me.koenn.bits.listeners;

import me.koenn.bits.Ref;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;

public class SleepListener implements Listener {

    private int sleeping = 0;

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        if (!(world.getEnvironment().equals(World.Environment.NORMAL))) {
            return;
        }

        this.sleeping++;

        int online = Math.toIntExact(Bukkit.getOnlinePlayers().stream().filter(o -> o.getGameMode().equals(GameMode.SURVIVAL)).count());

        if ((double) sleeping / online >= 0.5) {
            world.setTime(0);
        }

        Bukkit.getServer().getOnlinePlayers().forEach(p -> p.sendMessage(
                Ref.NEUTRAL_MSG_SLEEPING
                        .replace("{sleeping}", String.valueOf(this.sleeping))
                        .replace("{online}", String.valueOf(online))
        ));
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onPlayerBedLeave(PlayerBedLeaveEvent event) {
        this.sleeping--;
    }
}
