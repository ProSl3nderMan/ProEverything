package me.prosl3nderman.proeverything.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Must be sent by a player.");
            return true;
        }
        Player p = (Player) commandSender;
        if (p.isOp() && args.length != 0) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                p.sendMessage(ChatColor.RED + "The player " + ChatColor.WHITE + args[0] + ChatColor.RED + " does not exist!");
                return true;
            }
            if (!target.isOnline()) {
                p.sendMessage(ChatColor.RED + "The player " + ChatColor.WHITE + args[0] + ChatColor.RED + " does not exist!");
                return true;
            }
            if (target.isFlying()) {
                target.setFlying(false);
                target.setAllowFlight(false);
                target.sendMessage(ChatColor.GOLD + "Fly mode has been deactivated!");
                p.sendMessage(ChatColor.GOLD + "Fly mode has been deactivated for " + ChatColor.WHITE + target.getName() + ChatColor.GOLD + "!");
            } else {
                p.setAllowFlight(true);
                p.setFlying(true);
                target.sendMessage(ChatColor.GOLD + "Fly mode has been activated!");
                p.sendMessage(ChatColor.GOLD + "Fly mode has been activated for " + ChatColor.WHITE + target.getName() + ChatColor.GOLD + "!");
            }
            return true;
        }
        if (!p.hasPermission("ProEverything.fly")) {
            p.sendMessage(ChatColor.RED + "You must be a Frost Dragon (donor rank) to use this command! Alternatively, you can buy it for 200 vote points in /vshop.");
            return true;
        }
        if (p.isFlying()) {
            p.setFlying(false);
            p.setAllowFlight(false);
            p.sendMessage(ChatColor.GOLD + "Fly mode has been deactivated!");
        } else {
            p.setAllowFlight(true);
            p.setFlying(true);
            p.sendMessage(ChatColor.GOLD + "Fly mode has been activated!");
        }
        return true;
    }
}
