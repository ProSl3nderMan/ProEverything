package me.prosl3nderman.proeverything.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BroadcastCommand  implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Must be sent by a player.");
            return true;
        }
        Player p = (Player) commandSender;
        if (!p.isOp()) {
            return true;
        }
        String msg = "";
        for (int i = 0; i < args.length; i++)
            msg = msg + " " + args[i];
        Bukkit.broadcastMessage(ChatColor.RED + "[BROADCAST] " + ChatColor.GOLD + msg);
        return true;
    }
}
