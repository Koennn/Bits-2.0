package me.koenn.bits;

import org.bukkit.ChatColor;

public class Ref {

    public static final String MSG_JOIN_NEW = "+ [NEW] [{ISO}] {player}";
    public static final String MSG_JOIN = "+ [{ISO}] {player}";
    public static final String MSG_QUIT = "- {player}";
    public static final String PERM_BYPASSCOOLDOWN = "bits.bypasscooldown";
    public static final int TELEPORT_WARMUP = 100;
    public static final int TELEPORT_WARMUP_DONOR = 20;
    private static final String HEADER = "&6";
    public static final String HEADER_COLORS = cc(HEADER + "Colors:");
    public static final String HEADER_WARPS = cc(HEADER + "{category}:");
    public static final String HEADER_CATEGORIES = cc(HEADER + "Categories:");
    public static final String HEADER_DONORS = cc(HEADER + "Donors:");
    public static final String HEADER_GUARDS = cc(HEADER + "Guards:");
    public static final String HEADER_CHANGELOG = cc(HEADER + "Changelog:");
    private static final String NEUTRAL = "&e";
    public static final String NEUTRAL_MSG_SLEEPING = cc(NEUTRAL + "{sleeping}/{online} player(s) sleeping.");
    public static final String NEUTRAL_TELEPORTING = cc(NEUTRAL + "Teleporting...");
    public static final String NEUTRAL_BEAMEDTO = cc(NEUTRAL + "{player} has sent a beam request to you. Write /beam accept or click this message to accept it.");
    public static final String NEUTRAL_CLICK_WARP = cc(NEUTRAL + "&oClick on a warp to go there!");
    public static final String NEUTRAL_CLICK_CATEGORY = cc(NEUTRAL + "&oClick on a category to view the warps!");
    public static final String NEUTRAL_CLICK_COLOR = cc(NEUTRAL + "&oClick on a color to use it!");
    public static final String NEUTRAL_NICK_CLEAR = cc(NEUTRAL + "Nickname cleared.");
    public static final String NEUTRAL_WHOIS = cc(NEUTRAL + "The player behind the nickname \"{nickname}\" is {player}.");
    public static final String NEUTRAL_SEEN = cc(NEUTRAL + "{player} was last seen at {time} on {date}.");
    private static final String POSITIVE = "&a";
    public static final String POSITIVE_DONATE = cc(POSITIVE + "Donate URL: http://blockgaming.org/donate/bits/");
    public static final String POSITIVE_TELEPORTED = cc(POSITIVE + "Teleported!");
    public static final String POSITIVE_COLOR_CHANGE = cc(POSITIVE + "You colored your name {color}.");
    public static final String POSITIVE_BEAM_ACCEPTED = cc(POSITIVE + "Beam request accepted.");
    public static final String POSITIVE_BEAM_SENT = cc(POSITIVE + "Beam request sent.");
    public static final String POSITIVE_WARP_CREATED = cc(POSITIVE + "Warp successfully created.");
    public static final String POSITIVE_WARP_DELETED = cc(POSITIVE + "Warp successfully deleted.");
    public static final String POSITIVE_NICK_SET = cc(POSITIVE + "Nickname set to \"{name}\".");
    public static final String POSITIVE_SEEN_ONLINE = cc(POSITIVE + "{player} is currently online.");
    public static final String POSITIVE_HAT = cc(POSITIVE + "Enjoy your new hat!");
    public static final String POSITIVE_DISCORD = cc(POSITIVE + "Discord URL: https://discord.gg/PZcZ7D9");
    public static final String POSITIVE_SET_GUARD = cc(POSITIVE + "Successfully made {player} a guard.");
    public static final String POSITIVE_SET_DONOR = cc(POSITIVE + "Successfully made {player} a donor.");
    public static final String POSITIVE_DEL_GUARD = cc(POSITIVE + "Successfully removed {player} from the guards.");
    public static final String POSITIVE_DEL_DONOR = cc(POSITIVE + "Successfully removed {player} from the donors.");
    private static final String NEGATIVE = "&c";
    public static final String NEGATIVE_NO_BED = cc(NEGATIVE + "You don't have a bed.");
    public static final String NEGATIVE_PERMISSION = cc(NEGATIVE + "Not enough permissions.");
    public static final String NEGATIVE_ARGUMENTS = cc(NEGATIVE + "Invalid amount of arguments.");
    public static final String NEGATIVE_INVALID_COLOR = cc(NEGATIVE + "Color not found.");
    public static final String NEGATIVE_NO_BEAM = cc(NEGATIVE + "Nobody has requested to beam to you.");
    public static final String NEGATIVE_PLAYER_NOT_FOUND = cc(NEGATIVE + "Player not found.");
    public static final String NEGATIVE_BEAM = cc(NEGATIVE + "You need to wait {seconds} second(s) until next beam.");
    public static final String NEGATIVE_BEAMWORLDS = cc(NEGATIVE + "You need to be in the same world as your target.");
    public static final String NEGATIVE_WARP_NOT_FOUND = cc(NEGATIVE + "Warp doesn't exist.");
    public static final String NEGATIVE_WARP_EXISTS = cc(NEGATIVE + "Warp already exists.");
    public static final String NEGATIVE_CHARACTERS = cc(NEGATIVE + "Special characters aren't allowed.");
    public static final String NEGATIVE_NICK_WAIT = cc(NEGATIVE + "You need to wait 5 minutes before each nickname change.");
    public static final String NEGATIVE_NICK_USED = cc(NEGATIVE + "This name is already in use.");
    public static final String NEGATIVE_NICK_LENGTH = cc(NEGATIVE + "Your nickname must be between 3 and 16 characters.");
    public static final String NEGATIVE_WHOIS = cc(NEGATIVE + "There is no player with the nick {nick}.");
    public static final String NEGATIVE_RTP = cc(NEGATIVE + "You need to wait 6 hours between each random teleport.");
    public static final String NEGATIVE_PLAYERHEAD = cc(NEGATIVE + "It costs 1 diamond to get a playerhead.");
    public static final String NEGATIVE_SEEN_UNKNOWN = cc(NEGATIVE + "The last played date of this player is currently unknown.");
    public static final String NEGATIVE_EMPTY_HAND = cc(NEGATIVE + "You don't have anything in your hand.");
    public static final String NEGATIVE_ALREADY_GUARD = cc(NEGATIVE + "Already a guard.");
    public static final String NEGATIVE_ALREADY_DONOR = cc(NEGATIVE + "Already a donor.");
    public static final String NEGATIVE_NOT_GUARD = cc(NEGATIVE + "Not a guard.");
    public static final String NEGATIVE_NOT_DONOR = cc(NEGATIVE + "Not a donor.");
    public static final String NEGATIVE_UNKNOWN_ARG = cc(NEGATIVE + "Unknown argument {arg}.");

    private static String cc(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
