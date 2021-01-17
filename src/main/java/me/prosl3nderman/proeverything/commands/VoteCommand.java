package me.prosl3nderman.proeverything.commands;

import me.prosl3nderman.proeverything.ProEverything;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VoteCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Must be sent by a player.");
            return true;
        }
        Player p = (Player) commandSender;

        p.sendMessage(ChatColor.DARK_AQUA + "------------ " + ChatColor.AQUA + "Vote Links " + ChatColor.DARK_AQUA + "------------");
        int i = 1;
        for (String link : ProEverything.plugin.getConfig().getConfigurationSection("voteLinks").getKeys(false)) {
            p.sendMessage(ChatColor.DARK_AQUA + "" + i + ") " + ChatColor.AQUA + ProEverything.plugin.getConfig().getString("voteLinks." + link));
            i++;
        }
        p.sendMessage(ChatColor.DARK_AQUA + "We really appreciate you voting for us! By voting, you are rewarded 1 exp per vote. After 5 votes you will get a vote point to use in /voteShop.");
        return true;
    }
}
