package me.prosl3nderman.proeverything.commands;

import me.prosl3nderman.proeverything.ProEverything;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class TpacancelCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Must be sent by a player.");
            return true;
        }

        Player p = (Player) commandSender;
        if (!(ProEverything.plugin.tpaPending.containsValue(p.getName()))) {
            p.sendMessage(ChatColor.RED + "You have not sent a tpa to or tpa here request!");
            return true;
        }

        Player sentTo = null;
        for (Map.Entry tpaPendin : ProEverything.plugin.tpaPending.entrySet()) {
            if (tpaPendin.getValue().toString().equalsIgnoreCase(p.getName())) {
                sentTo = Bukkit.getPlayer(tpaPendin.getKey().toString());
                break;
            }
        }

        if (ProEverything.plugin.tpaHere.contains(sentTo.getName())) {
            ProEverything.plugin.tpaHere.remove(p.getName());
            sentTo.sendMessage(ChatColor.YELLOW + "The request to tpa to " + ChatColor.WHITE + p.getName() + ChatColor.YELLOW + " has been canceled.");
            p.sendMessage(ChatColor.YELLOW + "Your request for " + ChatColor.WHITE + sentTo.getName() + ChatColor.YELLOW + " to tpa here has been canceled.");
        } else {
            sentTo.sendMessage(ChatColor.YELLOW + "The request to tpa to you from " + ChatColor.WHITE + p.getName() + ChatColor.YELLOW + " has been canceled.");
            p.sendMessage(ChatColor.YELLOW + "Your request to tpa to " + ChatColor.WHITE + sentTo.getName() + ChatColor.YELLOW + " has been canceled.");
        }
        ProEverything.plugin.tpaPending.remove(p.getName());
        return true;
    }
}
