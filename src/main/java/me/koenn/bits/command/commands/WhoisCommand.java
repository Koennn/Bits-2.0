package me.koenn.bits.command.commands;

import me.koenn.bits.Ref;
import me.koenn.bits.command.ICommand;
import me.koenn.bits.player.CPlayer;
import me.koenn.bits.player.CPlayerRegistry;
import mkremins.fanciful.FancyMessage;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class WhoisCommand implements ICommand {

    @Override
    public String getName() {
        return "whois";
    }

    @Override
    public String execute(CPlayer cPlayer, String[] args) {
        if (args.length != 1) {
            return Ref.NEGATIVE_ARGUMENTS;
        }

        String nick = args[0];
        CPlayer player = CPlayerRegistry.getCPlayerByNick(nick);
        if (player == null) {
            return Ref.NEGATIVE_WHOIS.replace("{nick}", nick);
        }
        String name = player.getName();

        new FancyMessage(Ref.NEUTRAL_WHOIS.replace("{nickname}", nick).replace("{player}", name))
                .tooltip(String.format("Click to beam to %s", name))
                .command(String.format("/beam %s", name))
                .send(cPlayer.getPlayer());
        return null;
    }

    @Override
    public List<String> getTabCompleteOptions(CPlayer cPlayer, String argument) {
        return CPlayerRegistry.getCPlayers().stream()
                .map(CPlayer::getNickname)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
