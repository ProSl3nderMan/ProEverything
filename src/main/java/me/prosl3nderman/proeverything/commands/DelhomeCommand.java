package me.prosl3nderman.proeverything.commands;

import me.prosl3nderman.proeverything.HomeConfig;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class DelhomeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Must be sent by a player.");
            return true;
        }
        Player p = (Player) commandSender;
        String uuid = p.getUniqueId().toString();
        HomeConfig HC = new HomeConfig();
        if (!HC.getConfig().contains(uuid)) {
            p.sendMessage(ChatColor.RED + "You do not have a home! Do " + ChatColor.WHITE + "/sethome " + ChatColor.RED + "to set your home!");
            return true;
        }

        List<String> homes = new ArrayList<String>();
        for (String home : HC.getConfig().getConfigurationSection(uuid).getKeys(false))
            homes.add(home);
        if (args.length == 0) {
            if (!homes.contains("home")) {
                p.sendMessage(ChatColor.RED + "You do not have a default home! Do " + ChatColor.WHITE + "/sethome " + ChatColor.RED + "to set your default home!");
                return true;
            }
            p.sendMessage(ChatColor.RED + "Do " + ChatColor.WHITE + "/delhome home" + ChatColor.RED + " to delete your default home.");
            return true;
        }
        if (!homes.contains(args[0])) {
            p.sendMessage(ChatColor.RED + "Invalid home name " + ChatColor.WHITE + args[0] + ChatColor.RED + "! Homes: " + homesInStringForm(homes));
            return true;
        }
        HC.getConfig().set(uuid + "." + args[0], null);
        if (HC.getConfig().getConfigurationSection(uuid).getKeys(false).size() == 0)
            HC.getConfig().set(uuid, null);
        HC.srConfig();
        p.sendMessage(ChatColor.GOLD + "Your home " + ChatColor.WHITE + args[0] + ChatColor.GOLD + " has now been deleted!");
        return true;
    }

    private String homesInStringForm(List<String> homes) {
        String dummy = homes.get(0);
        for (int i = 1; i < homes.size(); i++)
            dummy = dummy + ", " + homes.get(i);
        return dummy;
    }
}
