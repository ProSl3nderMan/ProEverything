package me.prosl3nderman.proeverything.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DCCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Must be sent by a player.");
            return true;
        }
        Player p = (Player) commandSender;
        if (!p.hasPermission("ProEverything.dc")) {
            p.sendMessage(ChatColor.RED + "You do not have access to donor chat!");
            return true;
        }
        if (args.length == 0) {
            p.sendMessage(ChatColor.RED + "Correct usage: " + ChatColor.WHITE + "/dc <message here>");
            return true;
        }
        String message = " ";
        for (int i = 0; i < args.length; i++)
            message = message + args[i] + " ";
        Bukkit.broadcast(ChatColor.WHITE + "[" + ChatColor.GOLD + "DonorChat" + ChatColor.WHITE + "] " + ChatColor.GRAY + p.getName() + "" + ChatColor.WHITE + ":" + message, "ProEverything.dc");
        return true;
    }
}
