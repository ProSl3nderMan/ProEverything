package me.prosl3nderman.proeverything.commands;

import me.prosl3nderman.proeverything.PlayerDatabase.LocalTable;
import me.prosl3nderman.proeverything.ProEverything;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.logging.Level;

public class VOOPCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) { // command: /voop <function> <player> <cost>
        if (commandSender instanceof Player)
            return true;
        if (args.length != 3)
            return false;
        String function = args[0];
        Player p = Bukkit.getPlayer(args[1]);
        int cost = Integer.parseInt(args[2]);

        if (!ProEverything.plugin.votePoints.containsKey(p.getName())) {
            p.sendMessage(ChatColor.RED + "You must have " + ChatColor.WHITE + cost + ChatColor.RED + " vote points to buy this! Do /stats to see how much vote points you have.");
            return true;
        }
        int votePoints = ProEverything.plugin.votePoints.get(p.getName());
        if (cost > votePoints) {
            p.sendMessage(ChatColor.RED + "This costs " + ChatColor.WHITE + cost + ChatColor.RED + " vote points, you have only " + ChatColor.WHITE + votePoints + ChatColor.RED + " vote points!");
            return true;
        }
        Bukkit.getLogger().log(Level.INFO, "[ProVotes] " + p.getName() + " did " + function + " for " + cost + " vote points.");

        if (function.equalsIgnoreCase("buyhome")) {
            if (p.hasPermission("ProEverything.frost") && getMaxHomes(p) > 15) {
                p.sendMessage(ChatColor.RED + "You have reached the home limit of 15!");
                return true;
            } else if (p.hasPermission("ProEverything.blood") && getMaxHomes(p) > 12 && !p.hasPermission("ProEverything.frost")) {
                p.sendMessage(ChatColor.RED + "You have reached the home limit of 12!");
                return true;
            } else if (getMaxHomes(p) > 10 && !p.hasPermission("ProEverything.blood") && !p.hasPermission("ProEverything.frost")) {
                p.sendMessage(ChatColor.RED + "You have reached the home limit of 10!");
                return true;
            }
            new LocalTable().removeVotePoint(p, p.getUniqueId().toString(), cost);
            String permission = getHomePermission((getMaxHomes(p)));
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " permission set " + permission + " true");
            p.sendMessage(ChatColor.GOLD + "You just bought another home for " + ChatColor.WHITE + cost + ChatColor.GOLD + " vote points!");
            return true;
        }

        if (function.equalsIgnoreCase("call")) {
            new LocalTable().removeVotePoint(p, p.getUniqueId().toString(), cost);
            String permission = "Ridables.call";
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " permission set " + permission + " true");
            p.sendMessage(ChatColor.GOLD + "You just bought /call for " + ChatColor.WHITE + cost + ChatColor.GOLD + " vote points!");
            return true;
        }

        if (function.equalsIgnoreCase("buyclaimblocks")) {
            new LocalTable().removeVotePoint(p, p.getUniqueId().toString(), cost);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "adjustbonusclaimblocks " + p.getName() + " 1000");
            p.sendMessage(ChatColor.GOLD + "You just bought 1000 claims blocks for " + ChatColor.WHITE + cost + ChatColor.GOLD + " vote points!");
            return true;
        }

        if (function.equalsIgnoreCase("buybeef")) {
            if (p.getInventory().firstEmpty() == -1) {
                p.sendMessage(ChatColor.RED + "Full inventory, canceling...");
                return true;
            }
            new LocalTable().removeVotePoint(p, p.getUniqueId().toString(), cost);
            p.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 64));
            p.sendMessage(ChatColor.GOLD + "You just bought 64 steak for " + ChatColor.WHITE + cost + ChatColor.GOLD + " vote points!");
            return true;
        }

        if (function.equalsIgnoreCase("buyexp")) {
            if (p.getInventory().firstEmpty() == -1) {
                p.sendMessage(ChatColor.RED + "Full inventory, canceling...");
                return true;
            }
            new LocalTable().removeVotePoint(p, p.getUniqueId().toString(), cost);
            p.getInventory().addItem(new ItemStack(Material.EXPERIENCE_BOTTLE, 32));
            p.sendMessage(ChatColor.GOLD + "You just bought 32 exp bottles for " + ChatColor.WHITE + cost + ChatColor.GOLD + " vote points!");
            return true;
        }

        if (function.equalsIgnoreCase("buytag")) {
            if (p.getInventory().firstEmpty() == -1) {
                p.sendMessage(ChatColor.RED + "Full inventory, canceling...");
                return true;
            }
            new LocalTable().removeVotePoint(p, p.getUniqueId().toString(), cost);
            p.getInventory().addItem(new ItemStack(Material.NAME_TAG, 1));
            p.sendMessage(ChatColor.GOLD + "You just bought 1 custom name tag for " + ChatColor.WHITE + cost + ChatColor.GOLD + " vote points!");
            p.sendMessage(ChatColor.GOLD.UNDERLINE + "Hold on to the name tag and come up with a custom tag. When you see ProSl3nderMan, /pm ask him for your tag and he'll know what to do.");
            return true;
        }

        if (function.equalsIgnoreCase("buynick")) {
            if (p.hasPermission("ProEverything.nick")) {
                p.sendMessage(ChatColor.RED + "You already have " + ChatColor.WHITE + "/nick <nick>" + ChatColor.RED + "!");
                return true;
            }
            new LocalTable().removeVotePoint(p, p.getUniqueId().toString(), cost);
            String permission = "ProEverything.nick";
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " permission set " + permission + " true");
            p.sendMessage(ChatColor.GOLD + "You just bought nick perms for " + ChatColor.WHITE + cost + ChatColor.GOLD + " vote points!");
            p.sendMessage(ChatColor.GOLD.UNDERLINE + "Do " + ChatColor.WHITE + "/nick <nick>" + ChatColor.GOLD + " to set your nick. Color codes here: " + ChatColor.WHITE + "https://goo.gl/BV84PX");
            return true;
        }

        if (function.equalsIgnoreCase("buychatcolor")) {
            new LocalTable().removeVotePoint(p, p.getUniqueId().toString(), cost);
            String permission = "ProEverything.ChatColors";
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " permission set " + permission + " true");
            p.sendMessage(ChatColor.GOLD + "You just bought more chat colors for " + ChatColor.WHITE + cost + ChatColor.GOLD + " vote points! Do " + ChatColor.WHITE + "/chatcolor" + ChatColor.GOLD + " to " +
                    "select your chat color!");
            return true;
        }

        if (function.equalsIgnoreCase("buymoney")) {
            new LocalTable().removeVotePoint(p, p.getUniqueId().toString(), cost);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "eco give " + p.getName() + " 5000");
            p.sendMessage(ChatColor.GOLD + "You just bought $5000!");
            return true;
        }

        if (function.equalsIgnoreCase("buybeacon")) {
            new LocalTable().removeVotePoint(p, p.getUniqueId().toString(), cost);
            p.getInventory().addItem(new ItemStack(Material.BEACON, 1));
            p.sendMessage(ChatColor.GOLD + "You just bought a beacon!");
            return true;
        }
        if (function.equalsIgnoreCase("buyplot")) {
            new LocalTable().removeVotePoint(p, p.getUniqueId().toString(), cost);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " permission set plots.plot." + (getAllowedPlots(p) + 1));
            p.sendMessage(ChatColor.GOLD + "You just bought an additional plot!");
            return true;
        }
        return true;
    }

    private Integer getMaxHomes(Player p) {
        for (int i = 20; i > 0; i--) {
            if (p.hasPermission(getHomePermission(i))) {
                return i + 1;
            }
        }
        return 1;
    }

    private Integer getAllowedPlots(Player p) {
        int plots = 1;
        for (int i = 1; i < 40; i++) {
            if (p.hasPermission("plots.plot." + i))
                plots = i;
        }
        return plots;
    }

    private String getHomePermission(Integer homes) {
        String permission = "essentials.sethome.multiple.plus";
        if (homes == 1)
            permission += "one";
        else if (homes == 2)
            permission += "two";
        else if (homes == 3)
            permission += "three";
        else if (homes == 4)
            permission += "four";
        else if (homes == 5)
            permission += "five";
        else if (homes == 6)
            permission += "six";
        else if (homes == 7)
            permission += "seven";
        else if (homes == 8)
            permission += "eight";
        else if (homes == 9)
            permission += "nine";
        else if (homes == 10)
            permission += "ten";
        else if (homes == 11)
            permission += "eleven";
        else if (homes == 12)
            permission += "twelve";
        else if (homes == 13)
            permission += "thirteen";
        else if (homes == 14)
            permission += "fourteen";
        else if (homes == 15)
            permission += "fifteen";
        else if (homes == 16)
            permission += "sixteen";
        else if (homes == 17)
            permission += "seventeen";
        else if (homes == 18)
            permission += "eighteen";
        else if (homes == 19)
            permission += "nineteen";
        else if (homes == 20)
            permission += "twenty";
        else if (homes == 21)
            permission += "twentyone";
        else if (homes == 22)
            permission += "twentytwo";
        else if (homes == 23)
            permission += "twentythree";
        else if (homes == 24)
            permission += "twentyfour";
        return permission;
    }
}
