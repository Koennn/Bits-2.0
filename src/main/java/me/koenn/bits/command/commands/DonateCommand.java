package me.koenn.bits.command.commands;

import me.koenn.bits.Ref;
import me.koenn.bits.command.ICommand;
import me.koenn.bits.player.CPlayer;

import java.util.List;

public class DonateCommand implements ICommand {

    @Override
    public String getName() {
        return "donate";
    }

    @Override
    public String execute(CPlayer cPlayer, String[] args) {
        return Ref.POSITIVE_DONATE;
    }

    @Override
    public List<String> getTabCompleteOptions(CPlayer cPlayer, String argument) {
        return null;
    }
}
