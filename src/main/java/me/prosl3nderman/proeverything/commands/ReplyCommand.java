package me.prosl3nderman.proeverything.commands;

import me.prosl3nderman.proeverything.ProEverything;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReplyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Must be sent by a player.");
            return true;
        }
        Player p = (Player) commandSender;
        if (!ProEverything.plugin.reply.containsKey(p.getName())) {
            p.sendMessage(ChatColor.RED + "No one has /msg you yet!");
            return true;
        }
        if (args.length == 0) {
            p.sendMessage(ChatColor.RED + "Correct usage: " + ChatColor.WHITE + command + " <message>");
            return true;
        }
        String secondPlayerName = ProEverything.plugin.reply.get(p.getName());
        if (Bukkit.getPlayer(secondPlayerName) == null) {
            p.sendMessage(ChatColor.RED + "The player " + ChatColor.WHITE + secondPlayerName + ChatColor.RED + " has gone offline!");
            ProEverything.plugin.reply.remove(p.getName());
            return true;
        }
        if (!Bukkit.getPlayer(secondPlayerName).isOnline()) {
            p.sendMessage(ChatColor.RED + "The player " + ChatColor.WHITE + secondPlayerName + ChatColor.RED + " has gone offline!");
            ProEverything.plugin.reply.remove(p.getName());
            return true;
        }
        Player sendTo = Bukkit.getPlayer(secondPlayerName);

        String msg = "";
        for (int i = 0; i < args.length; i++)
            msg = msg + " " + args[i];

        p.sendMessage(ChatColor.GRAY + "[" + ChatColor.GOLD + "me" + ChatColor.GRAY + " -> " + ChatColor.LIGHT_PURPLE + sendTo.getName() + ChatColor.GRAY + "] " + ChatColor.RESET + msg);
        sendTo.sendMessage(ChatColor.GRAY + "[" + ChatColor.LIGHT_PURPLE + p.getName() + ChatColor.GRAY + " -> " + ChatColor.GOLD + "me" + ChatColor.GRAY + "] " + ChatColor.RESET + msg);
        if (ProEverything.plugin.reply.containsKey(sendTo.getName()))
            ProEverything.plugin.reply.remove(sendTo.getName());
        ProEverything.plugin.reply.put(sendTo.getName(), p.getName());
        return true;
    }
}
