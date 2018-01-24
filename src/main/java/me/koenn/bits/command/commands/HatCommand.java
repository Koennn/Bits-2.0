package me.koenn.bits.command.commands;

import me.koenn.bits.Ref;
import me.koenn.bits.command.ICommand;
import me.koenn.bits.player.CPlayer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.List;

public class HatCommand implements ICommand {

    @Override
    public String getName() {
        return "hat";
    }

    @Override
    public String execute(CPlayer cPlayer, String[] args) {
        PlayerInventory inventory = cPlayer.getPlayer().getInventory();
        if (inventory.getItemInMainHand() == null || inventory.getItemInMainHand().getType().equals(Material.AIR)) {
            return Ref.NEGATIVE_EMPTY_HAND;
        }

        ItemStack hat = inventory.getItemInMainHand();
        if (inventory.getHelmet() != null) {
            ItemStack helmet = inventory.getHelmet();

            inventory.setHelmet(hat);
            inventory.setItemInMainHand(helmet);
        } else {
            inventory.setHelmet(hat);
            inventory.setItemInMainHand(null);
        }
        return Ref.POSITIVE_HAT;
    }

    @Override
    public List<String> getTabCompleteOptions(CPlayer cPlayer, String argument) {
        return null;
    }
}
