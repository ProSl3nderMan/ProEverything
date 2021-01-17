package me.prosl3nderman.proeverything.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AnnounceCommand implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player)
            return false;
        String announcement = args[0];
        for (int i = 1; i < args.length; i++)
            announcement+= " " + args[i];
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',announcement));
        return true;
    }
}
