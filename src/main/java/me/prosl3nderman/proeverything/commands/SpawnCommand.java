package me.prosl3nderman.proeverything.commands;

import me.prosl3nderman.proeverything.ProEverything;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Must be done by a player.");
            return true;
        }
        Player p = (Player) commandSender;
        if (p.isOp() && args.length != 0) {
            ProEverything.plugin.getConfig().set(p.getWorld().getName() + "spawn", ProEverything.plugin.getStringFLocation(p.getLocation(), true));
            ProEverything.plugin.srConfig();
        }

        p.teleport(ProEverything.plugin.getLocationFString(ProEverything.plugin.getConfig().getString(p.getWorld().getName() + "spawn"), true));
        p.sendMessage(ChatColor.GOLD + "You have been teleported to spawn!");
        return true;
    }
}
