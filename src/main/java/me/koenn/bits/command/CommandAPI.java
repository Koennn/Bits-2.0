package me.koenn.bits.command;

import me.koenn.bits.Bits;
import me.koenn.bits.command.commands.*;
import me.koenn.bits.command.commands.warps.DelWarpCommand;
import me.koenn.bits.command.commands.warps.SetWarpCommand;
import me.koenn.bits.command.commands.warps.WarpCommand;
import me.koenn.bits.command.commands.warps.WarpsCommand;
import me.koenn.bits.player.CPlayer;
import me.koenn.bits.player.CPlayerRegistry;
import me.koenn.bits.util.registry.Registry;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CommandAPI implements CommandExecutor, TabCompleter {

    private static final Registry<ICommand> CMD_REGISTRY = new Registry<>();

    private static void registerCommand(ICommand command) {
        Bits.getInstance().getCommand(command.getName()).setExecutor(Bits.getCommandAPI());
        Bits.getInstance().getCommand(command.getName()).setTabCompleter(Bits.getCommandAPI());
        CMD_REGISTRY.register(command);

        if (command instanceof Listener) {
            Bukkit.getPluginManager().registerEvents((Listener) command, Bits.getInstance());
        }
    }

    public static void registerCommands() {
        registerCommand(new DonateCommand());
        registerCommand(new BedCommand());
        registerCommand(new ColorNameCommand());
        registerCommand(new RulesCommand());
        registerCommand(new BeamCommand());
        registerCommand(new WarpCommand());
        registerCommand(new WarpsCommand());
        registerCommand(new SetWarpCommand());
        registerCommand(new DelWarpCommand());
        registerCommand(new NickCommand());
        registerCommand(new WhoisCommand());
        registerCommand(new RandomTeleportCommand());
        registerCommand(new PlayerHeadCommand());
        registerCommand(new SeenCommand());
        registerCommand(new HatCommand());
        registerCommand(new DiscordCommand());
        registerCommand(new GuardCommand());
        registerCommand(new DonorCommand());
        registerCommand(new GuardsCommand());
        registerCommand(new DonorsCommand());
        registerCommand(new ChangelogCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Command is player-only");
            return true;
        }

        CPlayer cPlayer = CPlayerRegistry.getCPlayer(((Player) sender).getUniqueId());
        if (cPlayer == null) {
            return false;
        }

        CMD_REGISTRY.getAll().stream()
                .filter(iCommand -> iCommand.getName().equalsIgnoreCase(command.getName()))
                .findFirst().ifPresent(iCommand -> cPlayer.sendMessage(iCommand.execute(cPlayer, args)));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        CPlayer cPlayer = CPlayerRegistry.getCPlayer(((Player) sender).getUniqueId());
        if (cPlayer == null) {
            return new ArrayList<>();
        }

        if (args.length > 1) {
            return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
        }

        ICommand cmd = CMD_REGISTRY.getAll().stream()
                .filter(iCommand -> iCommand.getName().equalsIgnoreCase(command.getName()))
                .findFirst().orElse(null);

        String argument = (args.length > 0 && !args[0].trim().isEmpty()) ? args[0] : null;

        if (cmd == null || cmd.getTabCompleteOptions(cPlayer, argument) == null) {
            return new ArrayList<>();
        }

        List<String> options = cmd.getTabCompleteOptions(cPlayer, argument);
        if (argument == null) {
            return options;
        } else {
            return options.stream()
                    .map(String::toLowerCase)
                    .filter(option -> option.startsWith(argument.toLowerCase()))
                    .collect(Collectors.toList());
        }
    }
}
