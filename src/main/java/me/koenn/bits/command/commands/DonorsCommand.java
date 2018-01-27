package me.koenn.bits.command.commands;

import me.koenn.bits.Ref;
import me.koenn.bits.command.ICommand;
import me.koenn.bits.player.CPlayer;
import me.koenn.bits.player.CPlayerRegistry;

import java.util.List;

public class DonorsCommand implements ICommand {

    @Override
    public String getName() {
        return "donors";
    }

    @Override
    public String execute(CPlayer cPlayer, String[] args) {
        cPlayer.sendMessage(Ref.HEADER_DONORS);
        CPlayerRegistry.getCPlayers().stream()
                .filter(CPlayer::isDonor)
                .map(CPlayer::getName)
                .forEach(cPlayer::sendMessage);
        return null;
    }

    @Override
    public List<String> getTabCompleteOptions(CPlayer cPlayer, String argument) {
        return null;
    }
}
