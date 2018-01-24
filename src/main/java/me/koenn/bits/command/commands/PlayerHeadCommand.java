package me.koenn.bits.command.commands;

import me.koenn.bits.Ref;
import me.koenn.bits.command.ICommand;
import me.koenn.bits.player.CPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.stream.Collectors;

public class PlayerHeadCommand implements ICommand {

    @Override
    public String getName() {
        return "playerhead";
    }

    @Override
    public String execute(CPlayer cPlayer, String[] args) {
        if (args.length != 1) {
            return Ref.NEGATIVE_ARGUMENTS;
        }

        Inventory inventory = cPlayer.getPlayer().getInventory();
        if (!inventory.contains(Material.DIAMOND)) {
            return Ref.NEGATIVE_PLAYERHEAD;
        }

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), String.format("give %s minecraft:skull 1 3 {SkullOwner: %s}", cPlayer.getName(), args[0]));
        inventory.removeItem(new ItemStack(Material.DIAMOND, 1));
        return null;
    }

    @Override
    public List<String> getTabCompleteOptions(CPlayer cPlayer, String argument) {
        return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
    }
}
