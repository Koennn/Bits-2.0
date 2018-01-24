package me.koenn.bits.command.commands;

import me.koenn.bits.Ref;
import me.koenn.bits.command.ICommand;
import me.koenn.bits.player.CPlayer;
import me.koenn.bits.player.CPlayerRegistry;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class SeenCommand implements ICommand {

    private static final SimpleDateFormat FORMAT_TIME = new SimpleDateFormat("HH:mm:ss");
    private static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public String getName() {
        return "seen";
    }

    @Override
    public String execute(CPlayer cPlayer, String[] args) {
        if (args.length != 1) {
            return Ref.NEGATIVE_ARGUMENTS;
        }

        String name = args[0];

        OfflinePlayer player = Arrays.stream(Bukkit.getOfflinePlayers())
                .filter(offlinePlayer -> offlinePlayer.getName().equalsIgnoreCase(name))
                .findFirst().orElse(null);

        if (player == null) {
            return Ref.NEGATIVE_PLAYER_NOT_FOUND;
        }

        if (player.isOnline()) {
            return Ref.POSITIVE_SEEN_ONLINE
                    .replace("{player}", player.getName());
        }

        CPlayer temp = CPlayerRegistry.getCPlayer(player.getUniqueId());
        if (temp == null || !temp.contains("lastPlayed")) {
            return Ref.NEGATIVE_SEEN_UNKNOWN;
        }
        Date last = new Date(Long.parseLong(temp.get("lastPlayed")));

        return Ref.NEUTRAL_SEEN
                .replace("{player}", player.getName())
                .replace("{time}", FORMAT_TIME.format(last))
                .replace("{date}", FORMAT_DATE.format(last));
    }

    @Override
    public List<String> getTabCompleteOptions(CPlayer cPlayer, String argument) {
        return Arrays.stream(Bukkit.getOfflinePlayers()).map(OfflinePlayer::getName).collect(Collectors.toList());
    }
}
