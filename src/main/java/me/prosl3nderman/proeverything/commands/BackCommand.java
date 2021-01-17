package me.prosl3nderman.proeverything.commands;

import me.prosl3nderman.proeverything.ProEverything;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BackCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Must be sent by a player.");
            return true;
        }
        Player p = (Player) commandSender;
        if (!p.hasPermission("ProEverything.back")) {
            p.sendMessage(ChatColor.RED + "You do not have permission to use /back on death, you must be a donor!");
            return true;
        }
        ProEverything plugin = ProEverything.plugin;
        if (!plugin.back.containsKey(p.getName())) {
            p.sendMessage(ChatColor.RED + "You can only use /back when you die!");
            return true;
        }
        p.teleport(plugin.getLocationFString(plugin.back.get(p.getName()), false));
        plugin.back.remove(p.getName());
        p.sendMessage(ChatColor.GOLD + "You have been teleported to where you died.");
        return true;
    }
}
