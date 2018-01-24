package me.koenn.bits.command.commands.warps;

import me.koenn.bits.Ref;
import me.koenn.bits.command.ICommand;
import me.koenn.bits.player.CPlayer;
import me.koenn.bits.warps.Warp;
import me.koenn.bits.warps.WarpManager;

import java.util.List;
import java.util.stream.Collectors;

public class DelWarpCommand implements ICommand {

    @Override
    public String getName() {
        return "delwarp";
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
        Warp warp = WarpManager.INSTANCE.getWarp(name);
        if (warp == null) {
            return Ref.NEGATIVE_WARP_NOT_FOUND;
        }

        WarpManager.INSTANCE.deleteWarp(warp);
        return Ref.POSITIVE_WARP_DELETED;
    }

    @Override
    public List<String> getTabCompleteOptions(CPlayer cPlayer, String argument) {
        return WarpManager.INSTANCE.getWarps().stream().map(Warp::getName).collect(Collectors.toList());
    }
}
