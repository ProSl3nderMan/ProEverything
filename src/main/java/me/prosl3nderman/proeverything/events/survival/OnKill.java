package me.prosl3nderman.proeverything.events.survival;

import me.prosl3nderman.prochat.ChatConfig;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.ArrayList;
import java.util.List;

public class OnKill implements Listener {

    @EventHandler
    public void onKillEDragon(EntityDeathEvent e) {
        if (!(e.getEntity().getType() == EntityType.ENDER_DRAGON))
            return;
        if (!(e.getEntity().getKiller() instanceof Player))
            return;
        List<Player> players = e.getEntity().getWorld().getPlayers();
        for (Player p : players) {
            String uuid = p.getUniqueId().toString();
            String star = "end";

            ChatConfig cc = new ChatConfig();
            List<String> items = new ArrayList<String>();
            if (cc.getConfig().contains(uuid + ".suffixs"))
                items = cc.getConfig().getStringList(uuid + ".suffixs");
            if (items.contains(star))
                return;
            items.add(star);
            cc.getConfig().set(uuid + ".suffixs", items);
            cc.srConfig();

            p.sendMessage(ChatColor.GOLD + "You just received the star " + ChatColor.WHITE + "end" + ChatColor.GOLD + "! Do " + ChatColor.WHITE + "/star end" + ChatColor.GOLD + " to enable it.");
        }
    }
}
