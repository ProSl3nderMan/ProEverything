package me.prosl3nderman.proeverything.commands;

import me.prosl3nderman.prochat.ChatConfig;
import me.prosl3nderman.prochat.TagsNStarManager;
import me.prosl3nderman.proeverything.PlayerDatabase.LocalTable;
import me.prosl3nderman.proeverything.ProEverything;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

public class DonorCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) { // donor <command> <player> <misc>
        if (sender instanceof Player)
            return true;
        if (args.length == 0) {
            ProEverything.plugin.getLogger().log(Level.INFO, "Not enough arguments in /donor");
            return true;
        }
        if (Bukkit.getPlayer(args[1]) == null) {
            String cmd = "donor";
            for (int i = 0; i < args.length; i++)
                cmd = cmd + " " + args[i];
            ProEverything.plugin.getConfig().set("offlineDonations." + args[0], cmd);
            ProEverything.plugin.srConfig();
            return true;
        }
        Player p = Bukkit.getPlayer(args[1]);
        if (args[0].equalsIgnoreCase("votepoints")) {
            new LocalTable().addVotePoint(p, p.getUniqueId().toString(), Integer.parseInt(args[2]));
            return true;
        }
        if (args[0].equalsIgnoreCase("bloodDragon")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " parent add blood_dragon");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " permission set plots.plot." + (getAllowedPlots(p) + 1));
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "adjustbonusclaimblocks " + p.getName() + " 5000");
            ChatConfig cc = new ChatConfig();
            cc.getConfig().set(p.getUniqueId().toString() + ".tag", ProEverything.plugin.getConfig().getString("tags.bloodDragon"));
            cc.srConfig();
            new TagsNStarManager().setTagNSuffix(cc, p);
            Bukkit.broadcastMessage(ChatColor.BOLD.YELLOW + "Thank you " + ChatColor.BOLD.GOLD + p.getName() + ChatColor.BOLD.YELLOW + " for donating for the rank Blood Dragon!");
            return true;
        }
        if (args[0].equalsIgnoreCase("frostDragon")) {
            int plots = 2;
            if (!p.hasPermission("ProEverything.dc"))
                plots = 3;
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " parent add frost_dragon");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " permission set plots.plot." + (getAllowedPlots(p) + 2));
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "adjustbonusclaimblocks " + p.getName() + " 10000");
            ChatConfig cc = new ChatConfig();
            cc.getConfig().set(p.getUniqueId().toString() + ".tag", ProEverything.plugin.getConfig().getString("tags.frostDragon"));
            cc.srConfig();
            new TagsNStarManager().setTagNSuffix(cc, p);
            Bukkit.broadcastMessage(ChatColor.BOLD.YELLOW + "Thank you " + ChatColor.BOLD.GOLD + p.getName() + ChatColor.BOLD.YELLOW + " for donating for the rank Frost Dragon!");
            return true;
        }
        if (args[0].equalsIgnoreCase("nick")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " permission set " + "ProEverything.nick" + " true");
            Bukkit.broadcastMessage(ChatColor.BOLD.YELLOW+ "Thank you " + ChatColor.BOLD.GOLD + p.getName() + ChatColor.BOLD.YELLOW + " for donating for /nick permissions!");
            return true;
        }
        if (args[0].equalsIgnoreCase("prefix")) {
            p.sendMessage(ChatColor.GOLD + "Send a DM to ProSl3nderMan on discord your chosen prefix. ProSl3nderMan's discord tag: " + ChatColor.WHITE + "ProSl3nderMan#1462");
            p.sendMessage(ChatColor.GOLD + "Underlines, magic, and bold text are not allowed. Color codes: " + ChatColor.WHITE + "https://goo.gl/BV84PX");
            Bukkit.broadcastMessage(ChatColor.BOLD.YELLOW + "Thank you " + ChatColor.BOLD.GOLD + p.getName() + ChatColor.BOLD.YELLOW + " for donating for a custom prefix!");
            return true;
        }
        if (args[0].equalsIgnoreCase("homes")) {
            String permission = "ProEverything.maxhomes10";
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " permission set " + permission + " true");
            Bukkit.broadcastMessage(ChatColor.BOLD.YELLOW + "Thank you " + ChatColor.BOLD.GOLD + p.getName() + ChatColor.BOLD.YELLOW + " for donating for 10 homes!");
            return true;
        }
        if (args[0].equalsIgnoreCase("claimblocks")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "adjustbonusclaimblocks " + p.getName() + " 10000");
            Bukkit.broadcastMessage(ChatColor.BOLD.YELLOW + "Thank you " + ChatColor.BOLD.GOLD + p.getName() + ChatColor.BOLD.YELLOW + " for donating for 10,000 claim blocks!");
            return true;
        }
        if (args[0].equalsIgnoreCase("add_plots_to_existing")) {
            List<String> bloodDragon = Arrays.asList("maddycakes26","bobygril","itssollyy","bobslegopie","joyrv","tiraliass","epangelosanto","ja1","sniperriflex","escad_","iworkatikea","themuffinbutt0n",
                    "vitusmis","the_dead_king","katicle","nymphetnyxie","musically","malikk","pushthebutton","chxcago","maxandcheese12","classyy","owodaddyuwu","apricityday","psboss","brinslegopie",
                    "legend_ii","totallytroye","yuvaln8888","zanderrich","alli2020","tannerfamily","proconman","greykitty13","cjh_99","kaiix");
            for (String name : bloodDragon)
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set plots.plot." + 2);
            List<String> frostDragon = Arrays.asList("bobygril","itssollyy","bobslegopie","tiraliass","brinslegopie","legend_ii","ja1","totallytroye","yuvaln8888","vitusmis","the_dead_king","katicle",
                    "musically","zanderrich","alli2020","tannerfamily","proconman","malikk","pushthebutton","greykitty13","classyy","owodaddyuwu","cjh_99","psboss","kaiix");
            for (String name : frostDragon)
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set plots.plot." + 4);
            return true;
        }
        return true;
    }

    private Integer getMaxHomes(Player p) {
        for (int i = 20; i <= 0; i--) {
            if (p.hasPermission("ProEverything.maxhomes" + i))
                return i;
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
}
