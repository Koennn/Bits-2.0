package me.koenn.bits.command.commands;

import me.koenn.bits.Ref;
import me.koenn.bits.command.ICommand;
import me.koenn.bits.player.CPlayer;
import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.List;

public class ChangelogCommand implements ICommand {

    public static final String VERSION = "2.0.1";

    private static final String[] CHANGELOG = new String[]{
            "&c&lWARNING: Due to this latest update, donor permissions, colors and nicknames are reset. If you are a donor, please notify @Koenn or @KallumJ when you are on the server!",
            "Organised warps into categories.",
            "Made a lot of command messages clickable.",
            "Added a /rules command."
    };

    @Override
    public String getName() {
        return "changelog";
    }

    @Override
    public String execute(CPlayer cPlayer, String[] args) {
        cPlayer.sendMessage(Ref.HEADER_CHANGELOG);
        Arrays.stream(CHANGELOG).forEach(line -> cPlayer.sendMessage(ChatColor.GRAY + "- " + line));
        return null;
    }

    @Override
    public List<String> getTabCompleteOptions(CPlayer cPlayer, String argument) {
        return null;
    }
}
