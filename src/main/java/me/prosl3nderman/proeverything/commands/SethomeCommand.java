package me.prosl3nderman.proeverything.commands;

import me.prosl3nderman.proeverything.HomeConfig;
import me.prosl3nderman.proeverything.ProEverything;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SethomeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Must be sent by a player.");
            return true;
        }
        Player p = (Player) commandSender;

        List<String> homes = getHomes(p);
        if (args.length == 0)
            args = new String[]{"home"};

        if (homes.size() == 0 || homes.contains(args[0])) {
            String homeName = args[0];
            setHome(p, homeName);
            return true;
        }
        if (getMaxHomes(p) < (homes.size() + 1)) {
            p.sendMessage(ChatColor.RED + "You only have access to " + ChatColor.WHITE + getMaxHomes(p) + ChatColor.RED + " home(s).");
            p.sendMessage(ChatColor.RED + "Do " + ChatColor.WHITE + "/voteshop" + ChatColor.RED +" to purchase more homes. or donate with " + ChatColor.WHITE + "/buy" + ChatColor.RED + " for more homes.");
            return true;
        }
        setHome(p, args[0]);
        return true;
    }

    private List<String> getHomes(Player p) {
        HomeConfig HC = new HomeConfig();
        List<String> homes = new ArrayList<>();
        if (!HC.getConfig().contains(p.getUniqueId().toString()))
            return homes;
        for (String home : HC.getConfig().getConfigurationSection(p.getUniqueId().toString()).getKeys(false))
            homes.add(home);
        return homes;
    }

    private void setHome(Player p, String homeName) {
        HomeConfig HC = new HomeConfig();
        HC.getConfig().set(p.getUniqueId().toString() + "." + homeName, ProEverything.plugin.getStringFLocation(p.getLocation(), true));
        HC.srConfig();
        if (homeName.equalsIgnoreCase("home"))
            p.sendMessage(ChatColor.GOLD + "Your home has been set to your location!");
        else
            p.sendMessage(ChatColor.GOLD + "Your home " + ChatColor.WHITE + homeName + ChatColor.GOLD + " has been set to your current location!");
    }

    private Integer getMaxHomes(Player p) {
        int homes = 1;
        for (int i = 20; i > 0; i--) {
            if (p.hasPermission("ProEverything.maxhomes" + i)) {
                homes = i;
                break;
            }
        }
        if (p.hasPermission("ProEverything.donorhomes2"))
            homes = homes + 2;
        if (p.hasPermission("ProEverything.donorhomes4"))
            homes = homes + 4;
        return homes;
    }
}
