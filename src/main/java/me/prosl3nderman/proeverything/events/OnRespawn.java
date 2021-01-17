package me.prosl3nderman.proeverything.events;

import me.prosl3nderman.proeverything.HomeConfig;
import me.prosl3nderman.proeverything.ProEverything;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class OnRespawn implements Listener {

    @EventHandler
    public void onLeDeath(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        if (p.getWorld().getName().contains("BSkyBlock"))
            return;
        if (p.getWorld().getName().equalsIgnoreCase("creative")) {
            e.setRespawnLocation(ProEverything.plugin.getLocationFString(ProEverything.plugin.getConfig().getString(p.getWorld().getName() + "spawn"), true));
            return;
        }
        HomeConfig HC = new HomeConfig();
        if (!HC.getConfig().contains(p.getUniqueId().toString() + ".home")) {
            e.setRespawnLocation(ProEverything.plugin.getLocationFString(ProEverything.plugin.getConfig().getString(p.getWorld().getName() + "spawn"),true));
            return;
        }
        e.setRespawnLocation(ProEverything.plugin.getLocationFString(HC.getConfig().getString(p.getUniqueId().toString() + ".home"), true));
    }
}
