package me.koenn.bits.command.commands;

import me.koenn.bits.Ref;
import me.koenn.bits.command.ICommand;
import me.koenn.bits.player.CPlayer;
import org.bukkit.Bukkit;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class RandomTeleportCommand implements ICommand {

    private static final HashMap<UUID, Date> LAST_RTP = new HashMap<>();

    @Override
    public String getName() {
        return "randomteleport";
    }

    @Override
    public String execute(CPlayer cPlayer, String[] args) {
        Date now = new Date();
        Date rtp = LAST_RTP.get(cPlayer.getUUID());

        if (rtp != null && now.getTime() - rtp.getTime() < 21600000) {
            return Ref.NEGATIVE_RTP;
        }

        double size = (Bukkit.getWorld("bits").getWorldBorder().getSize() / 2) - 1000.0;

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), String.format("spreadplayers 0 0 1 %s false %s", size, cPlayer.getName()));
        LAST_RTP.put(cPlayer.getUUID(), now);
        return null;
    }

    @Override
    public List<String> getTabCompleteOptions(CPlayer cPlayer, String argument) {
        return null;
    }
}
