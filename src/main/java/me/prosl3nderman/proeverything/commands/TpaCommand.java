package me.prosl3nderman.proeverything.commands;

import me.prosl3nderman.proeverything.ProEverything;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class TpaCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) { //tpa <player name>
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Must be sent by a player.");
            return true;
        }
        Player p = (Player) commandSender;

        if (args.length == 0) {
            p.sendMessage(ChatColor.RED + "Correct usage: /tpa <player name>.");
            return true;
        }
        HashMap<String,String> tpaPending = ProEverything.plugin.tpaPending;
        if (tpaPending.containsValue(p.getName())) {
            Player oldRequestedPlayer = null;
            for (Map.Entry tpaPendin : tpaPending.entrySet()) {
                if (tpaPendin.getValue().toString().equalsIgnoreCase(p.getName())) {
                    oldRequestedPlayer = Bukkit.getPlayer(tpaPendin.getKey().toString());
                    break;
                }
            }
            if (oldRequestedPlayer != null) {
                if (ProEverything.plugin.tpaHere.contains(oldRequestedPlayer.getName()))
                    ProEverything.plugin.tpaHere.remove(oldRequestedPlayer.getName());
                if (oldRequestedPlayer.isOnline())
                    oldRequestedPlayer.sendMessage(ChatColor.YELLOW + "The player " + ChatColor.WHITE + p.getName() + ChatColor.YELLOW + " has canceled their tpa request.");
                p.sendMessage(ChatColor.YELLOW + "Cancelling your tpa request with " + ChatColor.WHITE + oldRequestedPlayer.getName() + ChatColor.YELLOW + ".");
                ProEverything.plugin.tpaPending.remove(oldRequestedPlayer.getName());
            }
        }

        if (Bukkit.getPlayer(args[0]) == null || !(Bukkit.getPlayer(args[0]).isOnline())) {
            p.sendMessage(ChatColor.RED + "The player " + ChatColor.WHITE + args[0] + ChatColor.RED + " is either not online or is misspelled!");
            return true;
        }


        Player sentTo = Bukkit.getPlayer(args[0]);
        ProEverything.plugin.tpaPending.put(sentTo.getName(), p.getName());
        finishTpa(p, sentTo);
        return true;
    }

    public static void finishTpa(Player p, Player sentTo) {
        p.sendMessage(ChatColor.YELLOW + "You have sent a tpa request to player " + ChatColor.WHITE + sentTo.getName() + ChatColor.YELLOW + "!");
        sentTo.sendMessage(ChatColor.YELLOW + "The player " + ChatColor.WHITE + p.getName() + ChatColor.YELLOW + " has request to tpa to you!");
        sentTo.sendMessage(ChatColor.YELLOW + "Do "  + ChatColor.GREEN + "/tpaccept" + ChatColor.YELLOW + " or " + ChatColor.GREEN + "/tpyes" + ChatColor.YELLOW + " to accept.");
        sentTo.sendMessage(ChatColor.YELLOW + "Do "  + ChatColor.RED + "/tpareject" + ChatColor.YELLOW + " or " + ChatColor.RED + "/tpadeny" + ChatColor.YELLOW + " to reject.");
        new BukkitRunnable() {
            @Override
            public void run() {
                if (ProEverything.plugin.tpaPending.containsKey(p.getName())) {
                    if (!ProEverything.plugin.tpaPending.get(p.getName()).equalsIgnoreCase(sentTo.toString()))
                        return;
                    p.sendMessage(ChatColor.RED + "Tpa request to player " + ChatColor.WHITE + sentTo.getName() + ChatColor.RED + " has expired!");
                    if (sentTo.isOnline())
                        sentTo.sendMessage(ChatColor.RED + "The tpa request from player " + ChatColor.WHITE + p.getName() + ChatColor.RED + " has expired!");
                    ProEverything.plugin.tpaPending.remove(p.getName());
                }
            }
        }.runTaskLaterAsynchronously(ProEverything.plugin, 400L);
    }

    private String timeToString(LocalTime time) {
        return time.toString();
    }

    private LocalTime stringToTime(String time) { //minutes:seconds
        return LocalTime.parse(time);
    }


    /*
        if (ac.getConfig().contains(p.getUniqueId().toString() + ".lastTPA") && !(p.hasPermission("ProEverything.noTpaCooldown")) && p.getLevel() >= 5) {
            LocalTime now = LocalTime.now();
            LocalTime old = stringToTime(ac.getConfig().getString(p.getUniqueId().toString() + ".lastTPA"));
            Duration dur = Duration.between(old, now);

            if (dur.toMinutes() < 1 && dur.toMinutes() >= 0) {
                p.sendMessage(ChatColor.YELLOW + "This tpa will cost " + ChatColor.RED + "5 exp" + ChatColor.YELLOW + "! Do " + ChatColor.GREEN + "/tpconfirm" + ChatColor.YELLOW + " if you wish to continue, or just wait " +
                        ChatColor.RED + (60-(dur.toMinutes() / 60)) + ChatColor.YELLOW + " second(s) till you can tpa again!");
                ProEverything.plugin.tpaConfirm.add(p.getName());

                Player sentTo = Bukkit.getPlayer(args[0]);
                ProEverything.plugin.tpaPending.put(p.getName(), sentTo.getName());

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (ProEverything.plugin.tpaConfirm.contains(p.getName()))
                            ProEverything.plugin.tpaConfirm.remove(p.getName());
                    }
                }.runTaskLaterAsynchronously(ProEverything.plugin, 400L);
            }else {
                ac.getConfig().set(p.getUniqueId().toString() + ".lastTPA", null);
                ac.srConfig();
            }
            return true;
        }
        */
}
