package me.prosl3nderman.proeverything.commands.skyblock;

import me.prosl3nderman.proeverything.ProEverything;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FrostshopCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Must be done by a player.");
            return true;
        }
        Player p = (Player) commandSender;
        if (p.isOp() && args.length != 0) {
            ProEverything.plugin.getConfig().set("frostshop", ProEverything.plugin.getStringFLocation(p.getLocation(), true));
            ProEverything.plugin.srConfig();
        }
        if (!p.hasPermission("proeverything.frostshop")) {
            p.sendMessage(ChatColor.RED + "You must be a Frost Dragon to do this! Do /buy to find out what all Frost Dragons get.");
            return true;
        }
        p.teleport(ProEverything.plugin.getLocationFString(ProEverything.plugin.getConfig().getString("frostshop"), true));
        p.sendMessage(ChatColor.GOLD + "You have been teleported to the frostshop!");
        return true;
    }
}
