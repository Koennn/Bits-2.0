package me.koenn.bits.command.commands;

import me.koenn.bits.Ref;
import me.koenn.bits.command.ICommand;
import me.koenn.bits.player.CPlayer;
import mkremins.fanciful.FancyMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ColorNameCommand implements ICommand {

    private static final List<String> COLORS = Arrays.stream(ChatColor.values())
            .filter(ChatColor::isColor)
            .map(Enum::name)
            .collect(Collectors.toList());

    private final Scoreboard scoreboard;

    public ColorNameCommand() {
        this.scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

        COLORS.stream()
                .map(String::toLowerCase)
                .filter(color -> this.scoreboard.getTeam(color) == null)
                .forEach(color -> this.scoreboard.registerNewTeam(color).setPrefix(ChatColor.valueOf(color.toUpperCase()).toString()));
    }

    @Override
    public String getName() {
        return "colorname";
    }

    @Override
    public String execute(CPlayer cPlayer, String[] args) {
        if (!cPlayer.isDonor() && !cPlayer.isGuard()) {
            return Ref.NEGATIVE_PERMISSION;
        }

        if (args.length != 1 || args[0].equalsIgnoreCase("list")) {
            cPlayer.sendMessage(Ref.HEADER_COLORS);
            cPlayer.sendMessage(Ref.CLICK_COLOR);
            COLORS.stream()
                    .map(String::toLowerCase)
                    .forEach(color -> new FancyMessage(color)
                            .tooltip(String.format("Click to change your color to %s", color.replace("_", " ")))
                            .formattedTooltip(new FancyMessage("Click to change your color to ")
                                    .then(color.replace("_", " "))
                                    .color(ChatColor.valueOf(color.toUpperCase()))
                            )
                            .color(ChatColor.valueOf(color.toUpperCase()))
                            .command(String.format("/cn %s", color))
                            .send(cPlayer.getPlayer()));
            return null;
        }

        String value = args[0].trim();
        String color = COLORS.stream().map(String::toLowerCase).filter(c -> c.equalsIgnoreCase(value)).findFirst().orElse(null);
        if (color == null) {
            return Ref.NEGATIVE_INVALID_COLOR;
        } else {
            cPlayer.setPrefix(ChatColor.valueOf(color.toUpperCase()).toString());
            this.scoreboard.getTeam(color).addEntry(cPlayer.getName());
            return Ref.POSITIVE_COLOR_CHANGE
                    .replace("{color}", color);
        }
    }

    @Override
    public List<String> getTabCompleteOptions(CPlayer cPlayer, String argument) {
        List<String> options = new ArrayList<>();
        options.add("list");
        if (argument != null) {
            options.addAll(COLORS.stream().map(String::toLowerCase).collect(Collectors.toList()));
        }
        return options;
    }
}
