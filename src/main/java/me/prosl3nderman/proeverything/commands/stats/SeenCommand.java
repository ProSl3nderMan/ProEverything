package me.prosl3nderman.proeverything.commands.stats;

import me.prosl3nderman.proeverything.PlayerDatabase.LocalTable;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SeenCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Must be done by a player.");
            return true;
        }
        Player p = (Player) commandSender;

        if (args.length == 0) {
            p.sendMessage(ChatColor.RED + "Correct usage: " + ChatColor.WHITE + "/seen <player>" + ChatColor.RED + "!");
            return true;
        }
        LocalTable PD = new LocalTable();
        PD.sendLastOn(p, args[0]);
        return true;
    }
}
