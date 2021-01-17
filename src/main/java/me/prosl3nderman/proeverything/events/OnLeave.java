package me.prosl3nderman.proeverything.events;

import me.prosl3nderman.proeverything.PlayerDatabase.LocalTable;
import me.prosl3nderman.proeverything.ProEverything;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnLeave implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        LocalTable PD = new LocalTable();
        PD.setLastOnline(p, p.getUniqueId().toString());

        /*new BukkitRunnable() {
            public void run() {
                new TabHandler().sendPlayerTabInfoToOtherServer(p, p.getName(), p.getUniqueId().toString(), "remove", p.getWorld().getName());
            }
        }.runTaskLater(ProEverything.plugin, 100L);*/

        if (ProEverything.plugin.homes.containsKey(p.getName()))
            ProEverything.plugin.homes.remove(p.getName());
        if (ProEverything.plugin.reply.containsKey(p.getName()))
            ProEverything.plugin.reply.remove(p.getName());
    }
}
