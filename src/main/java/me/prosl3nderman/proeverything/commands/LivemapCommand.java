package me.prosl3nderman.proeverything.commands;

import me.prosl3nderman.proeverything.ProEverything;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LivemapCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Must be done by a player.");
            return true;
        }
        Player p = (Player) commandSender;
        p.sendMessage(ChatColor.GOLD + "Here's a link to our livemap (dynmap): " + ChatColor.WHITE + ProEverything.plugin.getConfig().getString("dynmap"));
        p.sendMessage(ChatColor.GOLD + "Do /dynmap hide to hide yourself on the dynmap. Do /dynmap show to show yourself on the dynmap.");
        return true;
    }
}
