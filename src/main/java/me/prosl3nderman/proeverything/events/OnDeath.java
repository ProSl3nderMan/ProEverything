package me.prosl3nderman.proeverything.events;

import me.prosl3nderman.proeverything.ProEverything;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class OnDeath implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        p.sendMessage(ChatColor.GOLD + "Coords format: world;x;y;z    -     " + ChatColor.WHITE + ProEverything.plugin.getStringFLocation(p.getLocation(), false));
        if (p.hasPermission("ProEverything.back")) {
            p.sendMessage(ChatColor.GOLD + "Since you are a donor, you can use /back to return to the area you died at.");
            if (ProEverything.plugin.back.containsKey(p.getName()))
                ProEverything.plugin.back.remove(p.getName());
            ProEverything.plugin.back.put(p.getName(), ProEverything.plugin.getStringFLocation(p.getLocation(), false));
        } else
            p.sendMessage(ChatColor.GOLD + "If you were a donor, you could use /back to return to the area you died at.");
    }
}
