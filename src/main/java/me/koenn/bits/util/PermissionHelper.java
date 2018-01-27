package me.koenn.bits.util;

import me.koenn.bits.Bits;
import me.koenn.bits.Ref;
import me.koenn.bits.player.CPlayer;
import me.koenn.bits.player.CPlayerRegistry;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.PermissionAttachment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public final class PermissionHelper implements Listener {

    private static final HashMap<UUID, PermissionAttachment> LOADED_PERMS = new HashMap<>();

    private static final String[] GUARD_PERMS = new String[]{
            Ref.PERM_BYPASSCOOLDOWN,
            "minecraft.command.ban",
            "minecraft.command.kick",
            "minecraft.command.pardon",
            "coreprotect.lookup",
            "coreprotect.inspect",
            "griefprevention.softmute",
            "griefprevention.separate",
            "griefprevention.claimslistother"
    };

    private static final String[] DONOR_PERMS = new String[]{
            Ref.PERM_BYPASSCOOLDOWN
    };

    private static void loadPermissions(CPlayer cPlayer) {
        PermissionAttachment perms = cPlayer.getPlayer().addAttachment(Bits.getInstance());
        LOADED_PERMS.put(cPlayer.getUUID(), perms);

        if (cPlayer.isGuard()) {
            Arrays.stream(GUARD_PERMS).forEach(permission -> perms.setPermission(permission, true));
        }
        if (cPlayer.isDonor()) {
            Arrays.stream(DONOR_PERMS).forEach(permission -> perms.setPermission(permission, true));
        }
    }

    public static void reloadPermissions(CPlayer cPlayer) {
        if (!cPlayer.isOnline()) {
            if (LOADED_PERMS.containsKey(cPlayer.getUUID())) {
                LOADED_PERMS.remove(cPlayer.getUUID());
            }
            return;
        }

        if (LOADED_PERMS.containsKey(cPlayer.getUUID())) {
            try {
                if (cPlayer.getPlayer().hasPermission(Ref.PERM_BYPASSCOOLDOWN)) {
                    cPlayer.getPlayer().removeAttachment(LOADED_PERMS.get(cPlayer.getUUID()));
                }
                LOADED_PERMS.remove(cPlayer.getUUID());
            } catch (Exception ex) {
                LOADED_PERMS.remove(cPlayer.getUUID());
            }
        }

        loadPermissions(cPlayer);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event) {
        reloadPermissions(Objects.requireNonNull(CPlayerRegistry.getCPlayer(event.getPlayer().getUniqueId())));
    }
}
