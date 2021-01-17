package me.prosl3nderman.proeverything.commands;

import me.prosl3nderman.proeverything.HomeConfig;
import me.prosl3nderman.proeverything.ProEverything;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class HomeCommand implements CommandExecutor {
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

        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("list")) {
                p.sendMessage(ChatColor.RED + "Homes: " + homesInStringForm(homes));
                return true;
            }
            if (!homes.contains(args[0])) {
                p.sendMessage(ChatColor.RED + "Invalid home name " + ChatColor.WHITE + args[0] + ChatColor.RED + "! Homes: " + homesInStringForm(homes));
                return true;
            }
        }

        if (homes.size() == 1)
            args = new String[]{homes.get(0)};
        else if (homes.size() > 1 && args.length == 0) {
            p.sendMessage(ChatColor.RED + "Homes: " + homesInStringForm(homes));
            return true;
        }
        String homeName = args[0];

        p.teleport(ProEverything.plugin.getLocationFString(HC.getConfig().getString(uuid + "." + homeName), true));
        if (homeName.equalsIgnoreCase("home"))
            p.sendMessage(ChatColor.GOLD + "Teleporting you to your home!");
        else
            p.sendMessage(ChatColor.GOLD + "Teleporting you to your home " + ChatColor.WHITE + homeName + ChatColor.GOLD + "!");
        return true;
    }

    private String homesInStringForm(List<String> homes) {
        String dummy = homes.get(0);
        for (int i = 1; i < homes.size(); i++)
            dummy = dummy + ", " + homes.get(i);
        return dummy;
    }

    private void testing(Player p) {

    }

    private String timeToString(LocalTime time) {
        return time.toString();
    }

    private LocalTime stringToTime(String time) { //minutes:seconds
        return LocalTime.parse(time);
    }
}
