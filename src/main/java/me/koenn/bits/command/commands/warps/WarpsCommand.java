package me.koenn.bits.command.commands.warps;

import me.koenn.bits.command.ICommand;
import me.koenn.bits.player.CPlayer;
import me.koenn.bits.warps.WarpManager;

import java.util.List;

public class WarpsCommand implements ICommand {

    @Override
    public String getName() {
        return "warps";
    }

    @Override
    public String execute(CPlayer cPlayer, String[] args) {
        if (args.length == 1) {
            WarpManager.INSTANCE.printWarps(cPlayer.getPlayer(), args[0]);
        } else {
            WarpManager.INSTANCE.printWarps(cPlayer.getPlayer(), null);
        }
        return null;
    }

    @Override
    public List<String> getTabCompleteOptions(CPlayer cPlayer, String argument) {
        return null;
    }
}
