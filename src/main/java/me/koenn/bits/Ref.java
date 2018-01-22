package me.koenn.bits;

import org.bukkit.ChatColor;

public class Ref {

    private static final String HEADER = "&6";
    private static final String NEUTRAL = "&e";
    private static final String POSITIVE = "&a";
    private static final String NEGATIVE = "&c";

    public static final String MSG_SLEEPING = cc(NEUTRAL + "{sleeping}/{online} player(s) sleeping.");
    public static final String TELEPORTING = cc(NEUTRAL + "Teleporting...");

    public static final String DONATE = cc(POSITIVE + "Donate URL: http://blockgaming.org/donate/bits/");
    public static final String TELEPORTED = cc(POSITIVE + "Teleported!");

    public static final String NO_BED = cc(NEGATIVE + "You don't have a bed.");

    public static final String PERM_COLOREDNAME = "bits.formatname";
    public static final String PERM_BYPASSCOOLDOWN = "bits.bypasscooldown";

    public static final int TELEPORT_WARMUP = 100;
    public static final int TELEPORT_WARMUP_DONOR = 20;

    private static String cc(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
