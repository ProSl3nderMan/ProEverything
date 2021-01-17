package me.prosl3nderman.proeverything.commands;

import me.prosl3nderman.proeverything.ProEverything;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class TparejectCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Must be sent by a player.");
            return true;
        }
        Player p = (Player) commandSender;
        HashMap<String,String> tpaPending = ProEverything.plugin.tpaPending;
        if (!(tpaPending.containsKey(p.getName()))) {
            p.sendMessage(ChatColor.RED + "No one has sent you a tpa or a tpa here request to you!");
            return true;
        }

        Player sentTo = null;
        for (Map.Entry tpaPendin : ProEverything.plugin.tpaPending.entrySet()) {
            if (tpaPendin.getValue().toString().equalsIgnoreCase(p.getName())) {
                sentTo = Bukkit.getPlayer(tpaPendin.getKey().toString());
                break;
            }
        }

        if (ProEverything.plugin.tpaHere.contains(p.getName())) {
            ProEverything.plugin.tpaHere.remove(p.getName());
            sentTo.sendMessage(ChatColor.YELLOW + "Your request to tpa here " + ChatColor.WHITE + p.getName() + ChatColor.YELLOW + " has been rejected!");
            p.sendMessage(ChatColor.YELLOW + "You have rejected " + ChatColor.WHITE + sentTo.getName() + ChatColor.YELLOW + "'s request to tpa to them.");
        } else {
            sentTo.sendMessage(ChatColor.YELLOW + "Your request to tpa to " + ChatColor.WHITE + p.getName() + ChatColor.YELLOW + " has been rejected!");
            p.sendMessage(ChatColor.YELLOW + "You have rejected " + ChatColor.WHITE + sentTo.getName() + ChatColor.YELLOW + "'s request to tpa to you.");
        }
        ProEverything.plugin.tpaPending.remove(sentTo.getName());
        return true;
    }
}
