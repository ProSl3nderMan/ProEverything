package me.prosl3nderman.proeverything.commands;

import me.prosl3nderman.proeverything.ProEverything;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpectatorCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Must be sent by a player.");
            return true;
        }
        Player p = (Player) commandSender;
        if (!p.hasPermission("ProEverything.spectator")) {
            p.sendMessage(ChatColor.RED + "You do not have permission to go into spectator mode!");
            return true;
        }
        if (ProEverything.plugin.spectators.containsKey(p.getName())) {
            p.teleport(ProEverything.plugin.spectators.get(p.getName()));
            ProEverything.plugin.spectators.remove(p.getName());
            p.setGameMode(GameMode.SURVIVAL);
            p.sendMessage(ChatColor.GOLD + "You have been taken out of spectator mode!");
            return true;
        }
        ProEverything.plugin.spectators.put(p.getName(), p.getLocation());
        p.setGameMode(GameMode.SPECTATOR);
        p.sendMessage(ChatColor.GOLD + "You have been put into spectator mode!");
        return true;
    }
}
