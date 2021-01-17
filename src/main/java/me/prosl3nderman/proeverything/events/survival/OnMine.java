package me.prosl3nderman.proeverything.events.survival;

import me.prosl3nderman.prochat.ChatConfig;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class OnMine implements Listener {

    @EventHandler
    public void onDragonEggMine(BlockBreakEvent e) {
        if (e.getBlock().getType() != Material.DRAGON_EGG)
            return;
        Player p = e.getPlayer();
        p.getInventory().addItem(new ItemStack(Material.DRAGON_EGG, 1));

        String uuid = p.getUniqueId().toString();
        String tag = "dragonKing";

        ChatConfig cc = new ChatConfig();
        List<String> items = new ArrayList<String>();
        if (cc.getConfig().contains(uuid + ".tags"))
            items = cc.getConfig().getStringList(uuid + ".tags");
        if (items.contains(tag))
            return;
        items.add(tag);
        cc.getConfig().set(uuid + ".tags", items);
        cc.srConfig();

        p.sendMessage(ChatColor.GOLD + "You just received the tag " + ChatColor.WHITE + "dragonKing" + ChatColor.GOLD + "! Do " + ChatColor.WHITE + "/tag dragonKing" + ChatColor.GOLD + " to enable it.");
    }

    @EventHandler
    public void dragonEggTpEvent(BlockFromToEvent e) {
        if (e.getBlock().getType() == Material.DRAGON_EGG)
            e.setCancelled(true);
    }
}
