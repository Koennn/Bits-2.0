package me.koenn.bits.listeners;

import me.koenn.bits.warps.WarpManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;

public class ChunkUnloadListener implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onChunkUnload(ChunkUnloadEvent event) {
        if (WarpManager.INSTANCE.isWarpInsideChunk(event.getChunk())) {
            event.setCancelled(true);
        }
    }
}
