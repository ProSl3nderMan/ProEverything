package me.prosl3nderman.proeverything.events;

import me.prosl3nderman.proeverything.ProEverything;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.plugin.Plugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class OnChangeGamemode implements Listener {

    @EventHandler
    public void onChangeGamemode(PlayerChangedWorldEvent e) {
        Player p = e.getPlayer();
        if (p.getLocation().getWorld().getName().equalsIgnoreCase("world") || p.getLocation().getWorld().getName().equalsIgnoreCase("world_nether") || p.getWorld().getName().equalsIgnoreCase("world_the_end")) {
            p.teleport(ProEverything.plugin.getLocationFString(ProEverything.plugin.getConfig().getString("creativespawn"), true));
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);
            try {
                p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Survival was moved to its own server and it's world has been reset! The old survival world files will be posted in our discord, either in announcements or important links.");
                out.writeUTF("Connect");
                out.writeUTF("survival");
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
            p.sendPluginMessage(ProEverything.plugin, "BungeeCord", b.toByteArray());
        }
        if (p.getLocation().getWorld().getName().equalsIgnoreCase("BSkyBlock_world") || p.getLocation().getWorld().getName().equalsIgnoreCase("BSkyBlock_world_nether") || p.getLocation().getWorld().getName().equalsIgnoreCase("BSkyBlock_world_the_end")) {
            p.teleport(ProEverything.plugin.getLocationFString(ProEverything.plugin.getConfig().getString("creativespawn"), true));
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);
            try {
                p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Sykblock was moved to its own server and it's world has been reset! The old skyblock world files will be posted in our discord, either in announcements or important links.");
                out.writeUTF("Connect");
                out.writeUTF("skyblock");
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
            p.sendPluginMessage(ProEverything.plugin, "BungeeCord", b.toByteArray());
        }
        if (ProEverything.plugin.getConfig().getBoolean("events.ChangeGamemodeOnSameServer"))
            ProEverything.plugin.setTabColor(p);
    }
}
