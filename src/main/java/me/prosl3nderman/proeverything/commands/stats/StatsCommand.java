package me.prosl3nderman.proeverything.commands.stats;

import me.prosl3nderman.proeverything.PlayerDatabase.LocalTable;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Must be done by a player.");
            return true;
        }
        Player p = (Player) commandSender;

        LocalTable PD = new LocalTable(); // "joinDate","lastLogin","hours"
        if (args.length == 0) {
            PD.doStats(p, p.getName());
            return true;
        }
        if (args[0].equalsIgnoreCase("top")) {
            int pageNumber = 1;
            if (args.length != 1) {
                if (!NumberUtils.isNumber(args[1])) {
                    p.sendMessage(ChatColor.RED + "The page number '" + ChatColor.WHITE + args[1] + ChatColor.RED + "' is not a number " + ChatColor.WHITE + "4head" + ChatColor.RED + "!!!");
                    return true;
                }
                pageNumber = Integer.parseInt(args[1]);
            }
            PD.getTopHours(p,pageNumber);
            return true;
        }
        PD.doStats(p, args[0]);
        return true;
    }
}
