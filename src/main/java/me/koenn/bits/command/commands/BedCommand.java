package me.koenn.bits.command.commands;

import me.koenn.bits.Ref;
import me.koenn.bits.command.ICommand;
import me.koenn.bits.player.CPlayer;
import me.koenn.bits.util.Teleporter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public class BedCommand implements ICommand {

    @Override
    public String getName() {
        return "bed";
    }

    @Override
    public String execute(CPlayer cPlayer, String[] args) {
        Player player = cPlayer.getPlayer();
        Location bed = player.getBedSpawnLocation();

        if (bed == null) {
            return Ref.NEGATIVE_NO_BED;
        }

        Teleporter.teleport(player, bed);
        return null;
    }

    @Override
    public List<String> getTabCompleteOptions(CPlayer cPlayer, String argument) {
        return null;
    }
}
