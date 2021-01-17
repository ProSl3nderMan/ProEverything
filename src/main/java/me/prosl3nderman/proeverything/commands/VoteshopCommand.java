package me.prosl3nderman.proeverything.commands;

import me.lucko.luckperms.api.LuckPermsApi;
import me.lucko.luckperms.api.Node;
import me.lucko.luckperms.api.User;
import me.prosl3nderman.proeverything.PlayerDatabase.LocalTable;
import me.prosl3nderman.proeverything.ProEverything;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.logging.Level;

public class VoteshopCommand implements CommandExecutor, Listener {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Must be sent by a player.");
            return true;
        }
        Player p = (Player) commandSender;
        openModGUI(p);
        return true;
    }

    private void openModGUI(Player player) {
        Inventory inv = Bukkit.createInventory(null, ProEverything.plugin.getConfig().getInt("voteShop.slotamount"), ChatColor.DARK_AQUA + "Vote Shop Menu");

        for (int i = 0 ; i < ProEverything.plugin.getConfig().getInt("voteShop.slotamount") ; i++) {
            ItemStack barrier = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
            ItemMeta barrierMeta = barrier.getItemMeta();

            barrierMeta.setDisplayName(ChatColor.DARK_AQUA + "");
            barrier.setItemMeta(barrierMeta);

            inv.setItem(i, barrier);
        }

        for (String slot : ProEverything.plugin.getConfig().getConfigurationSection("voteShop.slots").getKeys(false)) {
            String direct = "voteShop.slots." + slot;

            ItemStack item = new ItemStack(Material.getMaterial(ProEverything.plugin.getConfig().getString(direct + ".block")));
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', ProEverything.plugin.getConfig().getString(direct + ".name")));

            if (item.getType() == Material.RED_BED)
                direct = "voteShop.slots." + slot + ".maxHomes" + (getMaxHomes(player) + 1);
            if (item.getType() == Material.STONE_SLAB)
                direct = "voteShop.slots." + slot + "." + getAllowedPlots(player);

            int amountofdesc = ProEverything.plugin.getConfig().getInt(direct + ".description.lineamount");
            if (amountofdesc == 1)
                itemMeta.setLore(Arrays.asList(ChatColor.translateAlternateColorCodes('&', ProEverything.plugin.getConfig().getString(direct + ".description.lines.line" + amountofdesc))));
            else if (amountofdesc == 2) {
                itemMeta.setLore(Arrays.asList(ChatColor.translateAlternateColorCodes('&', ProEverything.plugin.getConfig().getString(direct + ".description.lines.line" + (amountofdesc - 1))),
                        ChatColor.translateAlternateColorCodes('&', ProEverything.plugin.getConfig().getString(direct + ".description.lines.line" + (amountofdesc)))));
            } else if (amountofdesc == 3)
                itemMeta.setLore(Arrays.asList(ChatColor.translateAlternateColorCodes('&', ProEverything.plugin.getConfig().getString(direct + ".description.lines.line" + (amountofdesc-2))),
                        ChatColor.translateAlternateColorCodes('&', ProEverything.plugin.getConfig().getString(direct + ".description.lines.line" + (amountofdesc -1))),
                        ChatColor.translateAlternateColorCodes('&', ProEverything.plugin.getConfig().getString(direct + ".description.lines.line" + (amountofdesc)))));
            else if (amountofdesc == 4)
                itemMeta.setLore(Arrays.asList(ChatColor.translateAlternateColorCodes('&', ProEverything.plugin.getConfig().getString(direct + ".description.lines.line" + (amountofdesc-3))),
                        ChatColor.translateAlternateColorCodes('&', ProEverything.plugin.getConfig().getString(direct + ".description.lines.line" + (amountofdesc -2))),
                        ChatColor.translateAlternateColorCodes('&', ProEverything.plugin.getConfig().getString(direct + ".description.lines.line" + (amountofdesc -1))),
                        ChatColor.translateAlternateColorCodes('&', ProEverything.plugin.getConfig().getString(direct + ".description.lines.line" + (amountofdesc)))));
            else if (amountofdesc == 5)
                itemMeta.setLore(Arrays.asList(ChatColor.translateAlternateColorCodes('&', ProEverything.plugin.getConfig().getString(direct + ".description.lines.line" + (amountofdesc-4))),
                        ChatColor.translateAlternateColorCodes('&', ProEverything.plugin.getConfig().getString(direct + ".description.lines.line" + (amountofdesc -3))),
                        ChatColor.translateAlternateColorCodes('&', ProEverything.plugin.getConfig().getString(direct + ".description.lines.line" + (amountofdesc -2))),
                        ChatColor.translateAlternateColorCodes('&', ProEverything.plugin.getConfig().getString(direct + ".description.lines.line" + (amountofdesc -1))),
                        ChatColor.translateAlternateColorCodes('&', ProEverything.plugin.getConfig().getString(direct + ".description.lines.line" + (amountofdesc)))));
            else if (amountofdesc == 6)
                itemMeta.setLore(Arrays.asList(ChatColor.translateAlternateColorCodes('&', ProEverything.plugin.getConfig().getString(direct + ".description.lines.line" + (amountofdesc-5))),
                        ChatColor.translateAlternateColorCodes('&', ProEverything.plugin.getConfig().getString(direct + ".description.lines.line" + (amountofdesc -4))),
                        ChatColor.translateAlternateColorCodes('&', ProEverything.plugin.getConfig().getString(direct + ".description.lines.line" + (amountofdesc -3))),
                        ChatColor.translateAlternateColorCodes('&', ProEverything.plugin.getConfig().getString(direct + ".description.lines.line" + (amountofdesc -2))),
                        ChatColor.translateAlternateColorCodes('&', ProEverything.plugin.getConfig().getString(direct + ".description.lines.line" + (amountofdesc -1))),
                        ChatColor.translateAlternateColorCodes('&', ProEverything.plugin.getConfig().getString(direct + ".description.lines.line" + (amountofdesc)))));
            else if (amountofdesc == 7)
                itemMeta.setLore(Arrays.asList(ChatColor.translateAlternateColorCodes('&', ProEverything.plugin.getConfig().getString(direct + ".description.lines.line" + (amountofdesc-6))),
                        ChatColor.translateAlternateColorCodes('&', ProEverything.plugin.getConfig().getString(direct + ".description.lines.line" + (amountofdesc -5))),
                        ChatColor.translateAlternateColorCodes('&', ProEverything.plugin.getConfig().getString(direct + ".description.lines.line" + (amountofdesc -4))),
                        ChatColor.translateAlternateColorCodes('&', ProEverything.plugin.getConfig().getString(direct + ".description.lines.line" + (amountofdesc -3))),
                        ChatColor.translateAlternateColorCodes('&', ProEverything.plugin.getConfig().getString(direct + ".description.lines.line" + (amountofdesc -2))),
                        ChatColor.translateAlternateColorCodes('&', ProEverything.plugin.getConfig().getString(direct + ".description.lines.line" + (amountofdesc -1)))));
            item.setItemMeta(itemMeta);

            inv.setItem(Integer.parseInt(slot), item);
        }

        player.openInventory(inv);
    }

    @EventHandler
    public void OpenGUIListener(InventoryClickEvent event) {
        if (!event.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_AQUA + "Vote Shop Menu"))
            return;
        Player p = (Player) event.getWhoClicked();
        event.setCancelled(true);
        if (event.getClick() == null)
            return;
        if (event.getCurrentItem().getType() == Material.GRAY_STAINED_GLASS_PANE)
            return;
        for (String slot : ProEverything.plugin.getConfig().getConfigurationSection("voteShop.slots").getKeys(false)) {
            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ProEverything.plugin.getConfig().getString("voteShop.slots." + slot + ".name"))) {
                if (event.getCurrentItem().getItemMeta().getLore().get(0).equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&5No more available homes for now!"))) {
                    p.sendMessage(ChatColor.RED + "You have reached the maximum amount of homes you can get from voteshop!");
                    return;
                }
                if (event.getCurrentItem().getItemMeta().getLore().get(0).equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&5No more available plots for now!"))) {
                    p.sendMessage(ChatColor.RED + "You have reached the maximum amount of homes you can get from voteshop!");
                    return;
                }
                p.closeInventory();
                ConsoleCommandSender console = Bukkit.getConsoleSender();
                String command = ProEverything.plugin.getConfig().getString("voteShop.slots." + slot + ".command");
                if (command.contains("voop console ")) {
                    command = command.replaceAll("voop console %cost% ", "");
                    int cost;
                    if (event.getCurrentItem().getType() == Material.RED_BED)
                        cost = ProEverything.plugin.getConfig().getInt("voteShop.slots." + slot + ".maxhomes" + (getMaxHomes(p) + 1) + ".cost");
                    else if (event.getCurrentItem().getType() == Material.STONE_SLAB)
                        cost = ProEverything.plugin.getConfig().getInt("voteShop.slots." + slot + "." + getAllowedPlots(p) + ".cost");
                    else
                        cost = ProEverything.plugin.getConfig().getInt("voteShop.slots." + slot + ".cost");

                    if (!ProEverything.plugin.votePoints.containsKey(p.getName())) {
                        p.sendMessage(ChatColor.RED + "You must have " + ChatColor.WHITE + cost + ChatColor.RED + " vote points to buy this! Do /stats to see how much vote points you have.");
                        return;
                    }
                    int votePoints = ProEverything.plugin.votePoints.get(p.getName());
                    if (cost > votePoints) {
                        p.sendMessage(ChatColor.RED + "This costs " + ChatColor.WHITE + cost + ChatColor.RED + " vote points, you have only " + ChatColor.WHITE + votePoints + ChatColor.RED + " vote points!");
                        return;
                    }
                    command = command.replaceAll("%player%", p.getName());
                    Bukkit.getLogger().log(Level.INFO, "[ProVotes] " + p.getName() + " did " + command + " for " + cost + " vote points.");
                    new LocalTable().removeVotePoint(p, p.getUniqueId().toString(), cost);
                    if (command.contains(";")) {
                        String[] commands = command.split(";");
                        for (String cmd : commands)
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
                    } else
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
                    p.sendMessage(ChatColor.GREEN + "You just purchased " + event.getCurrentItem().getItemMeta().getDisplayName() + ChatColor.GREEN + " for " + ChatColor.WHITE + cost + ChatColor.GREEN
                            + " vote points.");
                    return;
                }
                if (command.contains("%player%"))
                    command = command.replace("%player%", p.getName());
                if (command.contains("%cost%")) {
                    if (event.getCurrentItem().getType() == Material.RED_BED)
                        command = command.replace("%cost%", ProEverything.plugin.getConfig().getString("voteShop.slots." + slot + ".maxhomes" + (getMaxHomes(p) + 1) + ".cost"));
                    else if (event.getCurrentItem().getType() == Material.STONE_SLAB)
                        command = command.replace("%cost%", ProEverything.plugin.getConfig().getString("voteShop.slots." + slot + "." + getAllowedPlots(p) + ".cost"));
                    else
                        command = command.replace("%cost%", ProEverything.plugin.getConfig().getString("voteShop.slots." + slot + ".cost"));
                }
                Bukkit.dispatchCommand(console, command);
                p.closeInventory();
                return;
            }
        }
    }

    private Integer getMaxHomes(Player p) {
        for (int i = 20; i <= 0; i--) {
            if (p.hasPermission(getHomePermission(i))) {
                return i + 1;
            }
        }
        return 1;
    }

    private String getAllowedPlots(Player p) {
        int plots = 1;
        for (int i = 1; i < 40; i++) {
            if (p.hasPermission("plots.plot." + i)) {
                if (p.hasPermission("proeverything.tags.frostDragon"))
                    plots = (i - 3);
                else if (p.hasPermission("proeverything.tags.bloodDragon"))
                    plots = (i - 1);
                else
                    plots = i;
            }
        }
        return "maxplots" + plots;
    }

    private String getHomePermission(Integer homes) {
        String permission = "essentials.sethome.multiple.plus";
        if (homes == 1)
            permission += "one";
        else if (homes == 2)
            permission += "two";
        else if (homes == 3)
            permission += "three";
        else if (homes == 4)
            permission += "four";
        else if (homes == 5)
            permission += "five";
        else if (homes == 6)
            permission += "six";
        else if (homes == 7)
            permission += "seven";
        else if (homes == 8)
            permission += "eight";
        else if (homes == 9)
            permission += "nine";
        else if (homes == 10)
            permission += "ten";
        else if (homes == 11)
            permission += "eleven";
        else if (homes == 12)
            permission += "twelve";
        else if (homes == 13)
            permission += "thirteen";
        else if (homes == 14)
            permission += "fourteen";
        else if (homes == 15)
            permission += "fifteen";
        else if (homes == 16)
            permission += "sixteen";
        else if (homes == 17)
            permission += "seventeen";
        else if (homes == 18)
            permission += "eighteen";
        else if (homes == 19)
            permission += "nineteen";
        else if (homes == 20)
            permission += "twenty";
        else if (homes == 21)
            permission += "twentyone";
        else if (homes == 22)
            permission += "twentytwo";
        else if (homes == 23)
            permission += "twentythree";
        else if (homes == 24)
            permission += "twentyfour";
        return permission;
    }
}
