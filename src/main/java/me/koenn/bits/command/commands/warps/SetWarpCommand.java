package me.koenn.bits.command.commands.warps;

import me.koenn.bits.Ref;
import me.koenn.bits.command.ICommand;
import me.koenn.bits.player.CPlayer;
import me.koenn.bits.warps.WarpManager;

import java.util.List;

public class SetWarpCommand implements ICommand {

    @Override
    public String getName() {
        return "setwarp";
    }

    @Override
    public String execute(CPlayer cPlayer, String[] args) {
        if (!cPlayer.getPlayer().isOp()) {
            return Ref.NEGATIVE_PERMISSION;
        }

        if (args.length != 1) {
            return Ref.NEGATIVE_ARGUMENTS;
        }

        String name = args[0];
        if (WarpManager.INSTANCE.getWarp(name) != null) {
            return Ref.NEGATIVE_WARP_EXISTS;
        }

        WarpManager.INSTANCE.addWarp(name, cPlayer.getPlayer().getLocation());
        return Ref.POSITIVE_WARP_CREATED;
    }

    @Override
    public List<String> getTabCompleteOptions(CPlayer cPlayer, String argument) {
        return null;
    }
}
