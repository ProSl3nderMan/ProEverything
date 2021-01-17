package me.prosl3nderman.proeverything.commands;

import me.prosl3nderman.prochat.ChatConfig;
import me.prosl3nderman.prochat.ProChat;
import me.prosl3nderman.proeverything.*;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ReloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Must be sent by a player.");
            return true;
        }
        Player p = (Player) commandSender;
        if (!p.isOp()) {
            return true;
        }

        ProEverything plugin = ProEverything.plugin;

        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        new ActiveConfig().reloadConfig();
        //new ChatConfig().reloadConfig();
        new HomeConfig().reloadConfig();
        new SkinConfig().reloadConfig();

        p.sendMessage(ChatColor.GREEN + "Configs have been reloaded!");
        return true;
    }
}
