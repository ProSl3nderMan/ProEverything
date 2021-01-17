package me.prosl3nderman.proeverything.commands;

import me.prosl3nderman.prochat.ChatConfig;
import me.prosl3nderman.proeverything.PlayerDatabase.LocalTable;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ProCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Must be sent by a player.");
            return true;
        }
        Player p = (Player) commandSender;
        if (!p.isOp())
            return true;
        if (args.length != 0) {
            if (args[0].equalsIgnoreCase("addvotes")) {
                new LocalTable().doVote(Bukkit.getPlayer(args[1]), Bukkit.getPlayer(args[1]).getUniqueId().toString());
                p.sendMessage(ChatColor.RED + "Added one vote to " + args[1]);
                return true;
            }
            if (args[0].equalsIgnoreCase("removevotePoints")) {
                new LocalTable().removeVotePoint(Bukkit.getPlayer(args[1]), Bukkit.getPlayer(args[1]).getUniqueId().toString(), 1);
                p.sendMessage(ChatColor.RED + "removed one vote point from " + args[1]);
                return true;
            }
            if (args[0].equalsIgnoreCase("addVotePoints")) {
                if (args.length == 2) {
                    new LocalTable().addVotePoint(Bukkit.getPlayer(args[1]), Bukkit.getPlayer(args[1]).getUniqueId().toString(), 1);
                    p.sendMessage(ChatColor.RED + "added one vote point to " + args[1]);
                } else {
                    new LocalTable().addVotePoint(Bukkit.getPlayer(args[1]), Bukkit.getPlayer(args[1]).getUniqueId().toString(), Integer.parseInt(args[2]));
                    p.sendMessage(ChatColor.RED + "added " + args[2] + " vote points to " + args[1]);
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("transferItems")) {
                ChatConfig cc = new ChatConfig();
                for (String uuid : cc.getConfig().getConfigurationSection("").getKeys(false)) {
                    if (cc.getConfig().contains(uuid + ".tags")) {
                        for (String tag : cc.getConfig().getStringList(uuid + ".tags"))
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + uuid + " permission set ProEverything.tags." + tag + " true");
                    }
                    if (cc.getConfig().contains(uuid + ".suffixs")) {
                        for (String suffix : cc.getConfig().getStringList(uuid + ".suffixs"))
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + uuid + " permission set ProEverything.suffixs." + suffix + " true");
                    }
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("oldItems")) {
                ChatConfig cc = new ChatConfig();
                for (String uuid : cc.getConfig().getConfigurationSection("").getKeys(false)) {
                    if (cc.getConfig().contains(uuid + ".tags"))
                        cc.getConfig().set(uuid + ".tags", null);
                    if (cc.getConfig().contains(uuid + ".suffixs"))
                        cc.getConfig().set(uuid + ".suffixs", null);
                }
                cc.srConfig();
                return true;
            }
            if (args[0].equalsIgnoreCase("checkHomes")) {
                Player test = Bukkit.getPlayer(args[1]);
                int homes = 1;
                for (int i = 10; i <= 0; i--) {
                    if (test.hasPermission("ProEverything.maxhomes" + i)) {
                        homes = i;
                        break;
                    }
                }
                p.sendMessage(test.getName() + "Has this amount of homes: " + homes);
                return true;
            }
            if (args[0].equalsIgnoreCase("transferPlayerData")) {
                new LocalTable().saveToFile();
                return true;
            }
            if (args[0].equalsIgnoreCase("transferPlayerData2")) {
                new LocalTable().saveToDatabase();
                return true;
            }
        }
        return true;
    }
}
