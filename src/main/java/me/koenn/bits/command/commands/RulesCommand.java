package me.koenn.bits.command.commands;

import me.koenn.bits.command.ICommand;
import me.koenn.bits.player.CPlayer;

import java.util.List;

public class RulesCommand implements ICommand {

    @Override
    public String getName() {
        return "rules";
    }

    @Override
    public String execute(CPlayer cPlayer, String[] args) {
        cPlayer.sendMessage("&e&l- Server Rules -");
        cPlayer.sendMessage("&b&l1) &rNo griefing");
        cPlayer.sendMessage("&b&l2) &rBe respectful towards other players");
        cPlayer.sendMessage("&b&l4) &rNo spamming");
        cPlayer.sendMessage("&b&l5) &rNo advertising");
        cPlayer.sendMessage("&b&l6) &rNo constant cursing");
        cPlayer.sendMessage("&7&oWe define griefing as purposefully destroying things that either " +
                "aren’t yours, or with the intent of upsetting another player, even in claims you " +
                "have trust too. If any of these rules are unclear to you, please ask a guard for " +
                "clarification. The breakage of any of these rules can result in a ban. Ban appeals " +
                "can be made in our discord. Banning is of the guard’s volition, and does not require warning.");
        return null;
    }

    @Override
    public List<String> getTabCompleteOptions(CPlayer cPlayer, String argument) {
        return null;
    }
}
