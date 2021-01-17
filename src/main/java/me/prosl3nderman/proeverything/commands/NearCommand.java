package me.prosl3nderman.proeverything.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NearCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Must be sent by a player.");
            return true;
        }
        Player p = (Player) commandSender;
        if (!p.hasPermission("ProEverything.near")) {
            p.sendMessage(ChatColor.RED + "You do not have permission to use /near, you must be a donor!");
            return true;
        }
        p.sendMessage(getLocal(p,200));
        return true;
    }

    private String getLocal(Player p, Integer radius) {
        Location pLoc = p.getLocation();
        String nearby = "";
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (p != player) {
                if (p.getWorld() == player.getWorld()) {
                    Location playerLoc = player.getLocation();
                    if (playerLoc.distanceSquared(pLoc) < (radius * radius))
                        nearby += ChatColor.DARK_RED + player.getName() + ChatColor.GOLD + "(" + ChatColor.DARK_RED + Math.round(playerLoc.distance(pLoc)) + ChatColor.GOLD + ")  ";
                }
            }
        }
        if (nearby.length() == 0) {
            nearby = ChatColor.DARK_RED + "ALL BY MYYYYSELFFF";
            return nearby;
        }
        return nearby;
    }
}
