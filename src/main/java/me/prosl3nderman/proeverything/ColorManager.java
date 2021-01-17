package me.prosl3nderman.proeverything;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ColorManager {

    public ItemStack getWoolFromChatColor(ChatColor cc) {
        if (cc == ChatColor.GRAY)
            return new ItemStack(Material.LIGHT_GRAY_WOOL);
        if (cc == ChatColor.DARK_GRAY)
            return new ItemStack(Material.GRAY_WOOL);
        if (cc == ChatColor.RED)
            return new ItemStack(Material.RED_WOOL);
        if (cc == ChatColor.DARK_RED)
            return new ItemStack(Material.RED_NETHER_BRICKS);
        if (cc == ChatColor.LIGHT_PURPLE)
            return new ItemStack(Material.PINK_WOOL);
        if (cc == ChatColor.DARK_PURPLE)
            return new ItemStack(Material.PURPLE_WOOL);
        if (cc == ChatColor.AQUA)
            return new ItemStack(Material.LIGHT_BLUE_WOOL);
        if (cc == ChatColor.DARK_AQUA)
            return new ItemStack(Material.CYAN_WOOL);
        if (cc == ChatColor.YELLOW)
            return new ItemStack(Material.YELLOW_WOOL);
        if (cc == ChatColor.GOLD)
            return new ItemStack(Material.GOLD_BLOCK);
        if (cc == ChatColor.WHITE)
            return new ItemStack(Material.WHITE_WOOL);
        if (cc == ChatColor.GREEN)
            return new ItemStack(Material.LIME_WOOL);
        if (cc == ChatColor.DARK_GREEN)
            return new ItemStack(Material.GREEN_WOOL);
        if (cc == ChatColor.DARK_BLUE)
            return new ItemStack(Material.BLUE_WOOL);
        return new ItemStack(Material.LIGHT_GRAY_WOOL);
    }

    public ChatColor getChatColorFromWool(Material wool) {
        if (wool == Material.LIGHT_GRAY_WOOL)
            return ChatColor.GRAY;
        if (wool == Material.GRAY_WOOL)
            return ChatColor.DARK_GRAY;
        if (wool == Material.RED_WOOL)
            return ChatColor.RED;
        if (wool == Material.RED_NETHER_BRICKS)
            return ChatColor.DARK_RED;
        if (wool == Material.PINK_WOOL)
            return ChatColor.LIGHT_PURPLE;
        if (wool == Material.PURPLE_WOOL)
            return ChatColor.DARK_PURPLE;
        if (wool == Material.LIGHT_BLUE_WOOL)
            return ChatColor.AQUA;
        if (wool == Material.CYAN_WOOL)
            return ChatColor.DARK_AQUA;
        if (wool == Material.YELLOW_WOOL)
            return ChatColor.YELLOW;
        if (wool == Material.GOLD_BLOCK)
            return ChatColor.GOLD;
        if (wool == Material.WHITE_WOOL)
            return ChatColor.WHITE;
        if (wool == Material.LIME_WOOL)
            return ChatColor.GREEN;
        if (wool == Material.GREEN_WOOL)
            return ChatColor.DARK_GREEN;
        if (wool == Material.BLUE_WOOL)
            return ChatColor.DARK_BLUE;
        return ChatColor.GRAY;
    }

    public Inventory getAllWools(Inventory inv, boolean darkRed) {
        inv.setItem(0,getWoolFromChatColor(ChatColor.GRAY));
        inv.setItem(1,getWoolFromChatColor(ChatColor.DARK_GRAY));
        inv.setItem(2,getWoolFromChatColor(ChatColor.RED));
        inv.setItem(3,getWoolFromChatColor(ChatColor.LIGHT_PURPLE));
        inv.setItem(4,getWoolFromChatColor(ChatColor.DARK_PURPLE));
        inv.setItem(5,getWoolFromChatColor(ChatColor.AQUA));
        inv.setItem(6,getWoolFromChatColor(ChatColor.DARK_AQUA));
        inv.setItem(7,getWoolFromChatColor(ChatColor.YELLOW));
        inv.setItem(8,getWoolFromChatColor(ChatColor.GOLD));
        inv.setItem(9,getWoolFromChatColor(ChatColor.WHITE));
        inv.setItem(10,getWoolFromChatColor(ChatColor.GREEN));
        inv.setItem(11,getWoolFromChatColor(ChatColor.DARK_GREEN));
        inv.setItem(12,getWoolFromChatColor(ChatColor.DARK_BLUE));
        if (darkRed)
            inv.setItem(13,getWoolFromChatColor(ChatColor.DARK_RED));
        return inv;
    }
    
    public List<ChatColor> getAllChatColors(boolean darkRed) {
        List<ChatColor> colors = new ArrayList<ChatColor>();

        colors.add(ChatColor.GRAY);
        colors.add(ChatColor.DARK_GRAY);
        colors.add(ChatColor.RED);
        colors.add(ChatColor.LIGHT_PURPLE);
        colors.add(ChatColor.DARK_PURPLE);
        colors.add(ChatColor.AQUA);
        colors.add(ChatColor.DARK_AQUA);
        colors.add(ChatColor.YELLOW);
        colors.add(ChatColor.GOLD);
        colors.add(ChatColor.WHITE);
        colors.add(ChatColor.GREEN);
        colors.add(ChatColor.DARK_GREEN);
        colors.add(ChatColor.DARK_BLUE);
        if (darkRed)
            colors.add(ChatColor.DARK_RED);

        return colors;
    }
}
