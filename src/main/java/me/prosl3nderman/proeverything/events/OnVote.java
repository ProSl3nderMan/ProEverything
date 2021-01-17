package me.prosl3nderman.proeverything.events;

import com.vexsoftware.votifier.model.VotifierEvent;
import me.prosl3nderman.proeverything.PlayerDatabase.LocalTable;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnVote implements Listener {

    @EventHandler
    public void onLeVote(VotifierEvent e) {
        if (Bukkit.getPlayer(e.getVote().getUsername()) == null) {
            bc(ChatColor.WHITE + e.getVote().getUsername() + ChatColor.AQUA + " just voted even though they don't play DragonsDoom!");
            return;
        }
        Player p = Bukkit.getPlayer(e.getVote().getUsername());
        bc(ChatColor.WHITE + p.getName() + ChatColor.AQUA + " just voted and received 1 exp!");
        p.setLevel(p.getLevel()+1);

        new LocalTable().doVote(p, p.getUniqueId().toString());
    }





    private void bc(String broadcast) {
        Bukkit.broadcastMessage(ChatColor.DARK_AQUA + "[" + ChatColor.AQUA + "VOTING" + ChatColor.DARK_AQUA + "] " + ChatColor.RESET + broadcast);
    }
}
