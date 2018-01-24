package me.koenn.bits.command.commands;

import me.koenn.bits.Ref;
import me.koenn.bits.command.ICommand;
import me.koenn.bits.player.CPlayer;
import me.koenn.bits.util.Teleporter;
import mkremins.fanciful.FancyMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class BeamCommand implements ICommand {

    private static final HashMap<UUID, UUID> BEAM_REQUESTS = new HashMap<>();
    private static final HashMap<UUID, Date> LAST_BEAM = new HashMap<>();

    @Override
    public String getName() {
        return "beam";
    }

    @Override
    public String execute(CPlayer cPlayer, String[] args) {
        if (args.length != 1) {
            return Ref.NEGATIVE_ARGUMENTS;
        }
        String sub = args[0];

        if (sub.equalsIgnoreCase("accept")) {
            return this.accept(cPlayer);
        }
        return this.request(cPlayer, sub);
    }

    private String accept(CPlayer cPlayer) {
        if (!BEAM_REQUESTS.containsKey(cPlayer.getUUID())) {
            return Ref.NEGATIVE_NO_BEAM;
        }

        Player from = Bukkit.getPlayer(BEAM_REQUESTS.get(cPlayer.getUUID()));
        if (from == null) {
            return Ref.NEGATIVE_PLAYER_NOT_FOUND;
        }

        from.sendMessage(Ref.POSITIVE_BEAM_ACCEPTED);
        cPlayer.sendMessage(Ref.POSITIVE_BEAM_ACCEPTED);

        Teleporter.teleport(from, cPlayer.getPlayer().getLocation());

        LAST_BEAM.put(from.getUniqueId(), new Date());
        BEAM_REQUESTS.remove(cPlayer.getUUID());
        return null;
    }

    private String request(CPlayer cPlayer, String to) {
        Date beamDate = LAST_BEAM.get(cPlayer.getUUID());

        if (beamDate != null) {
            int limit = (cPlayer.isDonor() || cPlayer.isGuard()) ? 60000 : 120000;
            long diff = new Date().getTime() - beamDate.getTime();

            if (diff < limit) {
                long seconds = (limit - diff) / 1000;
                return Ref.NEGATIVE_BEAM.replace("{seconds}", String.valueOf(seconds));
            }
        }

        Player player = Bukkit.getPlayer(to);
        if (player == null) {
            return Ref.NEGATIVE_PLAYER_NOT_FOUND;
        }

        if (!cPlayer.getPlayer().getWorld().equals(player.getWorld())) {
            return Ref.NEGATIVE_BEAMWORLDS;
        }

        BEAM_REQUESTS.put(player.getUniqueId(), cPlayer.getUUID());

        new FancyMessage(Ref.NEUTRAL_BEAMEDTO.replace("{player}", cPlayer.getName()))
                .tooltip("Click to accept!").command("/beam accept").send(player);

        return Ref.POSITIVE_BEAM_SENT;
    }

    @Override
    public List<String> getTabCompleteOptions(CPlayer cPlayer, String argument) {
        List<String> options = new ArrayList<>();
        if (BEAM_REQUESTS.containsKey(cPlayer.getUUID())) {
            options.add("accept");
        }
        if (argument != null) {
            options.addAll(Bukkit.getOnlinePlayers().stream()
                    .filter(player -> !player.getUniqueId().equals(cPlayer.getUUID()))
                    .map(HumanEntity::getName)
                    .collect(Collectors.toList()));
        }
        return options;
    }
}
