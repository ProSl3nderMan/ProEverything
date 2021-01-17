package me.prosl3nderman.proeverything.commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HatCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Must be sent by a player.");
            return true;
        }
        Player p = (Player) commandSender;
        if (!p.hasPermission("ProEverything.hat")) {
            p.sendMessage(ChatColor.RED + "You do not have permission to use this command! Buy this permission in the donor shop /buy or the voter shop /vshop.");
            return true;
        }
        if (p.getInventory().getItemInMainHand() == null) {
            p.sendMessage(ChatColor.RED + "You cannot set your hat as your hand silly!");
            return true;
        }
        ItemStack oldHelm = null;
        if (p.getInventory().getHelmet() != null)
            oldHelm = p.getInventory().getHelmet();
        p.getInventory().setHelmet(p.getInventory().getItemInMainHand());
        p.getInventory().remove(p.getInventory().getItemInMainHand());
        if (oldHelm != null)
            p.getInventory().setItemInMainHand(oldHelm);
        p.sendMessage(ChatColor.GOLD + "Changed your hat!");
        return true;
    }
}
