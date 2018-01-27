package me.koenn.bits.command.commands;

import me.koenn.bits.Ref;
import me.koenn.bits.command.ICommand;
import me.koenn.bits.player.CPlayer;
import me.koenn.bits.player.CPlayerRegistry;
import me.koenn.bits.util.PermissionHelper;

import java.util.Arrays;
import java.util.List;

public class GuardCommand implements ICommand {

    @Override
    public String getName() {
        return "guard";
    }

    @Override
    public String execute(CPlayer cPlayer, String[] args) {
        if (!cPlayer.getPlayer().isOp()) {
            return Ref.NEGATIVE_PERMISSION;
        }
        if (args.length != 2) {
            return Ref.NEGATIVE_ARGUMENTS;
        }

        CPlayer player = CPlayerRegistry.getCPlayer(args[1]);
        if (player == null) {
            return Ref.NEGATIVE_PLAYER_NOT_FOUND;
        }

        String arg = args[0];
        if (arg.equalsIgnoreCase("set")) {
            return this.setGuard(player);
        } else if (arg.equalsIgnoreCase("del")) {
            return this.delGuard(player);
        } else {
            return Ref.NEGATIVE_UNKNOWN_ARG.replace("{arg}", arg);
        }
    }

    private String setGuard(CPlayer cPlayer) {
        if (cPlayer.isGuard()) {
            return Ref.NEGATIVE_ALREADY_GUARD;
        }

        cPlayer.set("guard", true);
        PermissionHelper.reloadPermissions(cPlayer);

        return Ref.POSITIVE_SET_GUARD.replace("{player}", cPlayer.getName());
    }

    private String delGuard(CPlayer cPlayer) {
        if (!cPlayer.isGuard()) {
            return Ref.NEGATIVE_NOT_GUARD;
        }

        cPlayer.set("guard", false);
        PermissionHelper.reloadPermissions(cPlayer);

        return Ref.POSITIVE_DEL_GUARD.replace("{player}", cPlayer.getName());
    }

    @Override
    public List<String> getTabCompleteOptions(CPlayer cPlayer, String argument) {
        return Arrays.asList("set", "del");
    }
}
