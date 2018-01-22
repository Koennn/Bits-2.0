package me.koenn.bits.command;

import me.koenn.bits.Bits;
import me.koenn.bits.command.commands.BedCommand;
import me.koenn.bits.command.commands.DonateCommand;
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

        CMD_REGISTRY.getRegisteredObjects().stream()
                .filter(iCommand -> iCommand.getName().equalsIgnoreCase(command.getName()))
                .findFirst().ifPresent(iCommand -> cPlayer.sendMessage(iCommand.execute(cPlayer, args)));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        ICommand cmd = CMD_REGISTRY.getRegisteredObjects().stream()
                .filter(iCommand -> iCommand.getName().equalsIgnoreCase(command.getName()))
                .findFirst().orElse(null);

        if (cmd == null || cmd.getTabCompleteOptions() == null) {
            return new ArrayList<>();
        }
        return cmd.getTabCompleteOptions();
    }
}
