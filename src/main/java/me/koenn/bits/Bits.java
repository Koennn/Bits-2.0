package me.koenn.bits;

import me.koenn.bits.command.CommandAPI;
import me.koenn.bits.listeners.SleepListener;
import me.koenn.bits.player.CPlayerRegistry;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Bits extends JavaPlugin {

    private static Bits INSTANCE;
    private static CPlayerRegistry cPlayerRegistry;
    private static CommandAPI commandAPI;

    public static void log(String message) {
        INSTANCE.getLogger().info(message);
    }

    public static Bits getInstance() {
        return INSTANCE;
    }

    public static CPlayerRegistry getCPlayerRegistry() {
        return cPlayerRegistry;
    }

    public static CommandAPI getCommandAPI() {
        return commandAPI;
    }

    @Override
    public void onEnable() {
        INSTANCE = this;

        cPlayerRegistry = new CPlayerRegistry();
        CPlayerRegistry.loadCPlayers();

        commandAPI = new CommandAPI();
        CommandAPI.registerCommands();

        Bukkit.getPluginManager().registerEvents(new SleepListener(), this);
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
    }
}
