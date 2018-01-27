package me.koenn.bits;

import me.koenn.bits.command.CommandAPI;
import me.koenn.bits.listeners.*;
import me.koenn.bits.player.CPlayerRegistry;
import me.koenn.bits.util.PermissionHelper;
import me.koenn.bits.warps.WarpManager;
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

        WarpManager.load();

        commandAPI = new CommandAPI();
        CommandAPI.registerCommands();

        Bukkit.getPluginManager().registerEvents(new SleepListener(), this);
        Bukkit.getPluginManager().registerEvents(new ChunkUnloadListener(), this);
        Bukkit.getPluginManager().registerEvents(new DamageListener(), this);
        Bukkit.getPluginManager().registerEvents(new FishSlapListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        Bukkit.getPluginManager().registerEvents(new SignListener(), this);
        Bukkit.getPluginManager().registerEvents(new PermissionHelper(), this);
        Bukkit.getPluginManager().registerEvents(new KallumsKipper(), this);
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
    }
}
