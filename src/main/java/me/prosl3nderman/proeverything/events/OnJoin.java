package me.prosl3nderman.proeverything.events;

import me.prosl3nderman.proeverything.*;
import me.prosl3nderman.proeverything.PlayerDatabase.LocalTable;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OnJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
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
        ChatConfig cc = new ChatConfig();
        if (cc.getConfig().contains(p.getUniqueId().toString() + ".chatColor"))
            ProEverything.plugin.chatColor.put(p.getName(), ChatColor.valueOf(cc.getConfig().getString(p.getUniqueId().toString() + ".chatColor")));

        if (p.getGameMode() == GameMode.SPECTATOR) {
            p.teleport(ProEverything.plugin.getLocationFString(ProEverything.plugin.getConfig().getString("worldspawn"), true));
            p.setGameMode(GameMode.SURVIVAL);
            if (ProEverything.plugin.spectators.containsKey(p.getName()))
                ProEverything.plugin.spectators.remove(p.getName());
        }

        if (!p.hasPlayedBefore()) {
            LocalTable PD = new LocalTable();
            PD.setJoinDate(p.getUniqueId().toString(), p.getName());

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " permission set ProEverything.tags.newbie true");
            p.getInventory().addItem(new ItemStack(Material.GOLDEN_SHOVEL, 1));
            p.getInventory().addItem(new ItemStack(Material.STICK, 1));
            Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "Welcome " + ChatColor.DARK_PURPLE + p.getName() + ChatColor.LIGHT_PURPLE + " to DragonsDoom!");
            p.sendMessage(ChatColor.GOLD + "Here's a link to our discord: " + ChatColor.WHITE + ProEverything.plugin.getConfig().getString("discord"));
            p.sendMessage(ChatColor.GOLD + "Here's a link to our livemap (dynmap): " + ChatColor.WHITE + ProEverything.plugin.getConfig().getString("dynmap"));
            p.teleport(ProEverything.plugin.getLocationFString(ProEverything.plugin.getConfig().getString("worldspawn"),true));
        }
        new LocalTable().onJoin(p, p.getUniqueId().toString());

        /*new BukkitRunnable() {
            public void run() {
                new TabHandler().sendPlayerTabInfoToOtherServer(p, p.getName(), p.getUniqueId().toString(), "add", p.getWorld().getName());
            }
        }.runTaskLater(ProEverything.plugin, 100L);*/

        ProEverything.plugin.setTabColor(p);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String joinTime = dtf.format(now);
        ActiveConfig ac = new ActiveConfig();
        ac.getConfig().set(p.getUniqueId().toString() + ".joinTime", joinTime.toString());
        ac.srConfig();

        if (p.getLocation().clone().subtract(0,1,0).getBlock().getType() == Material.AIR && p.hasPermission("proeverything.fly")) {
            p.setAllowFlight(true);
            p.setFlying(true);
            p.sendMessage(ChatColor.GOLD + "Fly mode has been activated!");
        }

        if (ProEverything.plugin.getConfig().contains("offlineDonations." + p.getName())) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), ProEverything.plugin.getConfig().getString("offlineDonations." + p.getName()));
            ProEverything.plugin.getConfig().set("offlineDonations." + p.getName(), null);
            ProEverything.plugin.srConfig();
        }
    }

    @EventHandler
    public void onSpawn(PlayerSpawnLocationEvent e) {
        Player p = e.getPlayer();
        if (p.hasPlayedBefore())
            return;
        e.setSpawnLocation(ProEverything.plugin.getLocationFString(ProEverything.plugin.getConfig().getString("worldspawn"),true));
    }
}
