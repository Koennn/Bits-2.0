package me.koenn.bits.command.commands;

import me.koenn.bits.Ref;
import me.koenn.bits.command.ICommand;
import me.koenn.bits.player.CPlayer;
import me.koenn.bits.player.CPlayerRegistry;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class NickCommand implements ICommand {

    private static final HashMap<UUID, Date> LAST_CHANGE = new HashMap<>();

    @Override
    public String getName() {
        return "nick";
    }

    @Override
    public String execute(CPlayer cPlayer, String[] args) {
        if (args.length != 1) {
            return Ref.NEGATIVE_ARGUMENTS;
        }

        String nick = args[0];
        if (!nick.matches("[a-zA-Z0-9]*")) {
            return Ref.NEGATIVE_CHARACTERS;
        }

        if (nick.equalsIgnoreCase("clear")) {
            cPlayer.setNickname(null);
            return Ref.NEUTRAL_NICK_CLEAR;
        }

        Date date = LAST_CHANGE.get(cPlayer.getUUID());

        if (date != null && new Date().getTime() - date.getTime() < 300000) {
            return Ref.NEGATIVE_NICK_WAIT;
        }

        if (CPlayerRegistry.getCPlayerByNick(nick) != null) {
            return Ref.NEGATIVE_NICK_USED;
        }

        for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
            if (nick.equalsIgnoreCase(offlinePlayer.getName())) {
                return Ref.NEGATIVE_NICK_USED;
            }
        }

        if (nick.length() < 3 || nick.length() > 16) {
            return Ref.NEGATIVE_NICK_LENGTH;
        }

        cPlayer.setNickname(nick);
        LAST_CHANGE.put(cPlayer.getUUID(), new Date());
        return Ref.POSITIVE_NICK_SET.replace("{name}", nick);
    }

    @Override
    public List<String> getTabCompleteOptions(CPlayer cPlayer, String argument) {
        return null;
    }
}
