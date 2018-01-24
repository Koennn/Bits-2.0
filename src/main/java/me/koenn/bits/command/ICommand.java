package me.koenn.bits.command;

import me.koenn.bits.player.CPlayer;

import java.util.List;

public interface ICommand {

    String getName();

    String
    execute(CPlayer cPlayer, String[] args);

    List<String> getTabCompleteOptions(CPlayer cPlayer, String argument);
}
