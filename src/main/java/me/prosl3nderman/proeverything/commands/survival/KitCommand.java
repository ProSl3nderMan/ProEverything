package me.prosl3nderman.proeverything.commands.survival;

import me.prosl3nderman.proeverything.ProEverything;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class KitCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Must be sent by a player.");
            return true;
        }

        Player p = (Player) commandSender;

        if (!p.getWorld().getName().equalsIgnoreCase("world") && !p.getWorld().getName().equalsIgnoreCase("world_nether") && !p.getWorld().getName().equalsIgnoreCase("world_the_end")) {
            p.sendMessage(ChatColor.RED + "You can only do /kit in the survival world! /survival.");
            return true;
        }

        p.getInventory().addItem(new ItemStack(Material.GOLDEN_SHOVEL, 1));
        p.getInventory().addItem(new ItemStack(Material.STICK, 1));
        p.sendMessage(ChatColor.GOLD + "Giving you kit " + ChatColor.WHITE + "claim" + ChatColor.GOLD + "!");
        return true;
    }
}
