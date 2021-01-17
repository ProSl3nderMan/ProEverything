package me.prosl3nderman.proeverything.commands;

import me.prosl3nderman.proeverything.ActiveConfig;
import me.prosl3nderman.proeverything.ProEverything;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.LocalTime;
import java.util.HashMap;

public class TpacceptCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Must be sent by a player.");
            return true;
        }
        Player p = (Player) commandSender;

        HashMap<String,String> tpaPending = ProEverything.plugin.tpaPending;
        if (!(tpaPending.containsKey(p.getName()))) {
            p.sendMessage(ChatColor.RED + "No one has sent a tpa request to you! Suggesting you use " + ChatColor.WHITE + "/tpahere <player> " + ChatColor.RED + "to ask someone to tpa to you.");
            return true;
        }
        if (ProEverything.plugin.tpaHere.contains(p.getName())) {
            Player destinationPlayer = Bukkit.getPlayer(tpaPending.get(p.getName()));
            p.teleport(destinationPlayer);
            ProEverything.plugin.tpaPending.remove(p.getName());
            ProEverything.plugin.tpaHere.remove(p.getName());
            destinationPlayer.sendMessage(ChatColor.GOLD + "The player " + ChatColor.WHITE + p.getName() + ChatColor.GOLD + " has accepted your tpa here!");
            p.sendMessage(ChatColor.GOLD + "You have accepted " + ChatColor.WHITE + p.getName() + ChatColor.GOLD + "'s tpa here request!");

            if (!(destinationPlayer.hasPermission("ProEverything.noTpaCooldown"))) {
                ActiveConfig ac = new ActiveConfig();
                ac.getConfig().set(destinationPlayer.getUniqueId().toString() + ".lastTPA", timeToString(LocalTime.now()));
                ac.srConfig();
            }
        } else {
            Player playerWhoSent = Bukkit.getPlayer(tpaPending.get(p.getName()));

            playerWhoSent.sendMessage(ChatColor.GOLD + "The player " + ChatColor.WHITE + p.getName() + ChatColor.GOLD + " has accepted your tpa request! Teleporting now...");
            playerWhoSent.teleport(p);
            ProEverything.plugin.tpaPending.remove(p.getName());
            p.sendMessage(ChatColor.GOLD + "You have accepted " + ChatColor.WHITE + p.getName() + ChatColor.GOLD + "'s tpa request!");
        }
        return true;
    }

    private String timeToString(LocalTime time) {
        return time.toString();
    }

    private LocalTime stringToTime(String time) { //minutes:seconds
        return LocalTime.parse(time);
    }
}
