package me.prosl3nderman.proeverything.PlayerDatabase;

import me.prosl3nderman.proeverything.ActiveConfig;
import me.prosl3nderman.proeverything.ProEverything;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import javax.print.DocFlavor;
import java.sql.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.UUID;

public class LocalTable {

    private Connection connection;
    private Statement statement;
    private String host, database, username, password, table;
    private int port;
    private String THEJOINDATE, THELASTJOIN, THEHOURS,THEVOTES, THEVOTEPOINTS;

    public LocalTable() {
        host = "172.17.0.1";
        port = 3306;
        database = "players";
        username = "proeverything";
        password = "pr0Sk1lls15";
        table = ProEverything.plugin.getConfig().getString("localTableName");
    }

    public void saveToFile() {
        new BukkitRunnable() {
            public void run() {
                try {
                    openConnection();
                    statement = connection.createStatement();

                    ResultSet entry = statement.executeQuery("SELECT * FROM "+table+";");
                    ActiveConfig ac = new ActiveConfig();
                    while (entry.next()) {
                        ac.getConfig().set(entry.getString("PlayersUUID") + ".IGN", entry.getString("IGN"));
                        ac.getConfig().set(entry.getString("PlayersUUID") + ".joinDate", entry.getString("joinDate"));
                        ac.getConfig().set(entry.getString("PlayersUUID") + ".lastLogin", entry.getString("lastLogin"));
                        String hours = entry.getString("hours");
                        if (hours != null)
                            ac.getConfig().set(entry.getString("PlayersUUID") + ".hours", hours);
                        else
                            ac.getConfig().set(entry.getString("PlayersUUID") + ".hours", 0);
                        ac.getConfig().set(entry.getString("PlayersUUID") + ".votes", entry.getString("votes"));
                        ac.getConfig().set(entry.getString("PlayersUUID") + ".votePoints", entry.getString("votePoints"));
                        ac.getConfig().set(entry.getString("PlayersUUID") + ".nick", entry.getString("nick"));
                    }
                    ac.srConfig();
                } catch (SQLException e) { e.printStackTrace(); } catch (ClassNotFoundException e) { e.printStackTrace(); }
            }
        }.runTaskAsynchronously(ProEverything.plugin);
    }

    public void saveToDatabase() {
        new BukkitRunnable() {
            public void run() {
                try {
                    openConnection();
                    statement = connection.createStatement();

                    ActiveConfig ac = new ActiveConfig();
                    for (String uuid : ac.getConfig().getConfigurationSection("").getKeys(false)) {
                        statement = connection.createStatement();

                        ResultSet entry = statement.executeQuery("SELECT joinDate FROM "+table+" WHERE PlayersUUID='" + uuid + "';");

                        String name, joinDate, lastLogin, hours, votes, votePoints, nick;
                        name = ac.getConfig().getString(uuid + ".IGN");
                        joinDate = ac.getConfig().getString(uuid + ".joinDate");
                        lastLogin = ac.getConfig().getString(uuid + ".lastLogin");
                        hours = ac.getConfig().getString(uuid + ".hours");
                        votes = ac.getConfig().getString(uuid + ".votes");
                        votePoints = ac.getConfig().getString(uuid + ".votePoints");

                        statement.executeUpdate("INSERT INTO "+table+" (PlayersUUID, IGN, joinDate, lastLogin, hours, votes, votePoints, nick) VALUES ('" + uuid + "', '" + name + "', '" + joinDate + "', '" + lastLogin
                                + "', '" + hours + "', '" + votes + "', '" + votePoints + "', 'null');");
                    }
                } catch (SQLException e) { e.printStackTrace(); } catch (ClassNotFoundException e) { e.printStackTrace(); }
            }
        }.runTaskAsynchronously(ProEverything.plugin);
    }

    public void setJoinDate(String uuid, String name) {
        new BukkitRunnable() {
            public void run() {
                try {
                    openConnection();
                    statement = connection.createStatement();

                    //CODE STARTS HERE
                    ResultSet entry = statement.executeQuery("SELECT joinDate FROM "+table+" WHERE PlayersUUID='" + uuid + "';");

                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();
                    String joinDate = dtf.format(now);
                    if (entry.isBeforeFirst() == false) {
                        statement.executeUpdate("INSERT INTO " + table + " (PlayersUUID, IGN, joinDate, lastLogin, hours, votes, votePoints) VALUES ('" + uuid + "', '" + name + "', '" + joinDate + "', '" + joinDate + "', '0', '0', '0');");

                        statement = connection.createStatement();
                        entry = statement.executeQuery("SELECT joinDate FROM global WHERE PlayersUUID='" + uuid + "';");
                        if (entry.isBeforeFirst() == false)
                            statement.executeUpdate("INSERT INTO global (PlayersUUID, IGN, joinDate, lastLogin, hours) VALUES ('" + uuid + "', '" + name + "', '" + joinDate + "', '" + joinDate + "', '0');");
                    }
                    else {
                        statement.executeUpdate("UPDATE " + table + " SET joinDate = '" + joinDate + "' WHERE PlayersUUID = '" + uuid + "';UPDATE global SET joinDate = '" + joinDate + "' WHERE PlayersUUID = '" + uuid + "';");
                    }
                    //CODE ENDS HERE

                } catch (SQLException e) { e.printStackTrace(); } catch (ClassNotFoundException e) { e.printStackTrace(); }
            }
        }.runTaskAsynchronously(ProEverything.plugin);
    }

    public void setLastOnline(Player p, String uuid) {
        new BukkitRunnable() {
            public void run() {
                try {
                    openConnection();
                    statement = connection.createStatement();

                    //CODE STARTS HERE

                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();
                    String lastLogin = dtf.format(now);
                    statement.executeUpdate("UPDATE "+table+" SET lastLogin = '" + lastLogin + "' WHERE PlayersUUID = '" + uuid + "';UPDATE global SET lastLogin = '" + lastLogin + "' WHERE PlayersUUID = '" + uuid + "';");

                    Double hours;
                    ActiveConfig ac = new ActiveConfig();
                    if (ac.getConfig().contains(uuid + ".joinTime")) {
                        LocalDateTime joinTime = LocalDateTime.parse(ac.getConfig().getString(uuid + ".joinTime"), dtf);
                        Duration dur = Duration.between(joinTime, now);
                        hours = Double.parseDouble(Long.toString(ChronoUnit.SECONDS.between(joinTime, now))) / 60 / 60;
                        ac.getConfig().set(uuid + ".joinTime", null);
                        ac.srConfig();
                    } else
                        hours = Double.parseDouble("0");

                    statement = connection.createStatement();
                    ResultSet entry = statement.executeQuery("SELECT hours FROM "+table+" WHERE PlayersUUID='" + uuid + "';");
                    double hoursCombined = 0;
                    if (entry.next()) {
                        hoursCombined = Double.parseDouble(entry.getString("hours")) + hours;
                        statement.executeUpdate("UPDATE " + table + " SET hours = '" + hoursCombined + "' WHERE PlayersUUID = '" + uuid + "';");

                        if (ProEverything.plugin.votePoints.containsKey(p.getName()))
                            ProEverything.plugin.votePoints.remove(p.getName());

                        statement = connection.createStatement();
                        entry = statement.executeQuery("SELECT hours FROM global WHERE PlayersUUID='" + uuid + "';");
                        if (entry.next())
                            statement.executeUpdate("UPDATE global SET hours = '" + (hours + Double.parseDouble(entry.getString("hours"))) + "' WHERE PlayersUUID = '" + uuid + "';");
                    }
                    ProEverything.plugin.checkHours(hoursCombined,uuid, p);
                    //CODE ENDS HERE

                } catch (SQLException e) { e.printStackTrace(); } catch (ClassNotFoundException e) { e.printStackTrace(); }
            }
        }.runTaskAsynchronously(ProEverything.plugin);
    }

    public void onDisable() {
        try {
            openConnection();
            statement = connection.createStatement();

            //CODE STARTS HERE

            for (Player p : Bukkit.getOnlinePlayers()) {
                String uuid = p.getUniqueId().toString();

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                String lastLogin = dtf.format(now);
                statement.executeUpdate("UPDATE " + table + " SET lastLogin = '" + lastLogin + "' WHERE PlayersUUID = '" + uuid + "';UPDATE global SET lastLogin = '" + lastLogin + "' WHERE PlayersUUID = '" + uuid + "';");

                Double hours;
                ActiveConfig ac = new ActiveConfig();
                if (ac.getConfig().contains(uuid + ".joinTime")) {
                    LocalDateTime joinTime = LocalDateTime.parse(ac.getConfig().getString(uuid + ".joinTime"), dtf);
                    Duration dur = Duration.between(joinTime, now);
                    hours = Double.parseDouble(Long.toString(ChronoUnit.SECONDS.between(joinTime, now))) / 60 / 60;
                    ac.getConfig().set(uuid + ".joinTime", null);
                    ac.srConfig();
                } else
                    hours = Double.parseDouble("0");

                statement = connection.createStatement();
                ResultSet entry = statement.executeQuery("SELECT hours FROM " + table + " WHERE PlayersUUID='" + uuid + "';");
                if (entry.next()) {
                    double combinedHours = Double.parseDouble(entry.getString("hours")) + hours;
                    statement.executeUpdate("UPDATE " + table + " SET hours = '" + combinedHours + "' WHERE PlayersUUID = '" + uuid + "';");

                    statement = connection.createStatement();
                    entry = statement.executeQuery("SELECT hours FROM global WHERE PlayersUUID='" + uuid + "';");
                    if (entry.next())
                        statement.executeUpdate("UPDATE global SET hours = '" + (hours + Double.parseDouble(entry.getString("hours"))) + "' WHERE PlayersUUID = '" + uuid + "';");
                }
                if (ProEverything.plugin.votePoints.containsKey(p.getName())) {
                    statement = connection.createStatement();
                    entry = statement.executeQuery("SELECT votePoints FROM "+table+" WHERE PlayersUUID='" + uuid + "';");
                    if (entry.next()) {
                        if (ProEverything.plugin.votePoints.get(p.getName()) != Integer.parseInt(entry.getString("votePoints"))) {
                            statement = connection.createStatement();
                            entry = statement.executeQuery("SELECT votePoints FROM " + table + " WHERE PlayersUUID='" + uuid + "';");
                            entry.next();
                            statement.executeUpdate("UPDATE " + table + " SET votePoints = '" + ProEverything.plugin.votePoints.get(p.getName()) + "' WHERE PlayersUUID = '" + uuid + "';");
                        }
                        ProEverything.plugin.votePoints.remove(p.getName());
                    }
                }
            }
            //CODE ENDS HERE

        } catch (SQLException e) { e.printStackTrace(); } catch (ClassNotFoundException e) { e.printStackTrace(); }
    }

    public void doStats(Player p, String IGN) {
        new BukkitRunnable() {
            public void run() {
                try {
                    openConnection();
                    statement = connection.createStatement();

                    //CODE STARTS HERE
                    ResultSet entry = statement.executeQuery("SELECT * FROM "+table+" WHERE IGN='" + IGN + "';");
                    if (entry.next()) {
                        THEJOINDATE = entry.getString("joinDate");
                        THELASTJOIN = entry.getString("lastLogin");
                        THEHOURS = entry.getString("hours");
                        THEVOTES = entry.getString("votes");
                        THEVOTEPOINTS = entry.getString("votePoints");

                        p.sendMessage(ChatColor.LIGHT_PURPLE + "------ " + ChatColor.DARK_PURPLE + IGN + "'s Local Stats" + ChatColor.LIGHT_PURPLE + " ------");
                        p.sendMessage(ChatColor.DARK_PURPLE + "Join Date: " + ChatColor.LIGHT_PURPLE + THEJOINDATE);
                        p.sendMessage(ChatColor.DARK_PURPLE + "Last Login: " + ChatColor.LIGHT_PURPLE + THELASTJOIN);
                        p.sendMessage(ChatColor.DARK_PURPLE + "Votes: " + ChatColor.LIGHT_PURPLE + THEVOTES);
                        p.sendMessage(ChatColor.DARK_PURPLE + "Vote Points: " + ChatColor.LIGHT_PURPLE + THEVOTEPOINTS);
                        p.sendMessage(ChatColor.DARK_PURPLE + "Hours: " + ChatColor.LIGHT_PURPLE + THEHOURS);
                    } else
                        p.sendMessage(ChatColor.RED + "The player " + ChatColor.WHITE + IGN + ChatColor.RED + " does not exist!");
                    //CODE ENDS HERE

                } catch (SQLException e) { e.printStackTrace(); } catch (ClassNotFoundException e) { e.printStackTrace(); }
            }
        }.runTaskAsynchronously(ProEverything.plugin);
    }

    public void getTopHours(Player p, Integer pageNumber) {
        new BukkitRunnable() {
            public void run() {
                try {
                    openConnection();
                    statement = connection.createStatement();

                    ResultSet entry = statement.executeQuery("SELECT * FROM "+table+" ORDER BY hours DESC limit " + (((pageNumber*10)-9) -1) + ",10;");
                    p.sendMessage(ChatColor.LIGHT_PURPLE + "------ " + ChatColor.DARK_PURPLE + "Top Hours Locally" + ChatColor.LIGHT_PURPLE + " ------");
                    for (int i = ((pageNumber*10) -9); i < ((pageNumber*10) +1); i++) {
                        if (entry.next())
                            p.sendMessage(ChatColor.LIGHT_PURPLE + "(" + i + "). " + ChatColor.DARK_PURPLE + entry.getString("IGN") + ChatColor.LIGHT_PURPLE + " : " + ChatColor.DARK_PURPLE
                                    + entry.getString("hours") + " hours");
                    }
                    p.sendMessage(ChatColor.LIGHT_PURPLE + "------ " + ChatColor.DARK_PURPLE + "Page " + pageNumber + ", do /stats top <number> for next page" + ChatColor.LIGHT_PURPLE + " ------");
                } catch (SQLException e) { e.printStackTrace(); } catch (ClassNotFoundException e) { e.printStackTrace(); }
            }
        }.runTaskAsynchronously(ProEverything.plugin);
    }

    public void sendLastOn(Player p, String IGN) { //if LASTJOIN=false then the player does not exist.
        String test = "false";
        new BukkitRunnable() {
            public void run() {
                try {
                    openConnection();
                    statement = connection.createStatement();

                    //CODE STARTS HERE
                    ResultSet entry = statement.executeQuery("SELECT * FROM "+table+" WHERE IGN='" + IGN + "';");
                    String test2 = test;
                    if (entry.next())
                        p.sendMessage(ChatColor.DARK_PURPLE + IGN + ChatColor.LIGHT_PURPLE + " was last online, this gamemode, " + ChatColor.WHITE + entry.getString("lastLogin") + ChatColor.LIGHT_PURPLE + "!");
                    else
                        p.sendMessage(ChatColor.RED + "The player " + ChatColor.WHITE + IGN + ChatColor.RED + " does not exist!");
                    //CODE ENDS HERE
                } catch (SQLException e) { e.printStackTrace(); } catch (ClassNotFoundException e) { e.printStackTrace(); }
            }
        }.runTaskAsynchronously(ProEverything.plugin);
    }

    public void onEnable() {
        try {
            openConnection();
            statement = connection.createStatement();

            //CODE STARTS HERE

            for (Player p : Bukkit.getOnlinePlayers()) {
                String uuid = p.getUniqueId().toString();

                openConnection();
                statement = connection.createStatement();

                //CODE STARTS HERE
                statement = connection.createStatement();
                ResultSet entry = statement.executeQuery("SELECT IGN FROM "+table+" WHERE PlayersUUID='" + uuid + "';");
                if (entry.next())
                    statement.executeUpdate("UPDATE "+table+" SET IGN = '" + p.getName() + "' WHERE PlayersUUID = '" + uuid + "';UPDATE global SET IGN = '" + p.getName() + "' WHERE PlayersUUID = '" + uuid + "';");
                else {
                    setJoinDate(uuid, p.getName());
                    return;
                }

                statement = connection.createStatement();
                entry = statement.executeQuery("SELECT * FROM "+table+" WHERE PlayersUUID='" + uuid + "';");
                if (entry.next()) {
                    int votePointss = Integer.parseInt(entry.getString("votePoints"));
                    if (votePointss != 0)
                        ProEverything.plugin.votePoints.put(p.getName(), votePointss);
                }
            }
            //CODE ENDS HERE

        } catch (SQLException e) { e.printStackTrace(); } catch (ClassNotFoundException e) { e.printStackTrace(); }
    }

    public void onJoin(Player p, String uuid) {
        new BukkitRunnable() {
            public void run() {
                try {
                    openConnection();
                    statement = connection.createStatement();

                    //CODE STARTS HERE
                    statement = connection.createStatement();
                    ResultSet entry = statement.executeQuery("SELECT IGN FROM "+table+" WHERE PlayersUUID='" + uuid + "';");
                    if (entry.next())
                        statement.executeUpdate("UPDATE "+table+" SET IGN = '" + p.getName() + "' WHERE PlayersUUID = '" + uuid + "';UPDATE global SET IGN = '" + p.getName() + "' WHERE PlayersUUID = '" + uuid + "';");
                    else {
                        setJoinDate(uuid, p.getName());
                        return;
                    }

                    statement = connection.createStatement();
                    entry = statement.executeQuery("SELECT * FROM "+table+" WHERE PlayersUUID='" + uuid + "';");
                    if (entry.next()) {
                        int votePointss = Integer.parseInt(entry.getString("votePoints"));
                        if (votePointss != 0)
                            ProEverything.plugin.votePoints.put(p.getName(), votePointss);
                    }

                    //statement = connection.createStatement();
                    //entry = statement.executeQuery("SELECT * FROM "+table+" WHERE PlayersUUID='" + uuid + "';");

                    //CODE ENDS HERE

                } catch (SQLException e) { e.printStackTrace(); } catch (ClassNotFoundException e) { e.printStackTrace(); }
            }
        }.runTaskAsynchronously(ProEverything.plugin);
    }

    public void doVote(Player p, String uuid) {
        new BukkitRunnable() {
            public void run() {
                try {
                    openConnection();
                    statement = connection.createStatement();

                    //CODE STARTS HERE
                    statement = connection.createStatement();
                    ResultSet entry = statement.executeQuery("SELECT votes FROM "+table+" WHERE PlayersUUID='" + uuid + "';");
                    entry.next();
                    int votes = entry.getInt("votes");
                    statement.executeUpdate("UPDATE " + table + " SET votes = '" + (votes + 1) + "' WHERE PlayersUUID = '" + uuid + "';");

                    if ((votes + 1) % 5 == 0) {
                        p.sendMessage(ChatColor.GOLD + "You just received 1 vote point! Do /voteshop or /vshop to spend your vote point.");
                        int points = 0;
                        if (ProEverything.plugin.votePoints.containsKey(p.getName()))
                            points = ProEverything.plugin.votePoints.get(p.getName());
                        ProEverything.plugin.votePoints.put(p.getName(), points + 1);
                    }
                    if (ProEverything.plugin.votePoints.containsKey(p.getName())) {
                        statement = connection.createStatement();
                        entry = statement.executeQuery("SELECT votePoints FROM "+table+" WHERE PlayersUUID='" + uuid + "';");
                        entry.next();
                        if (ProEverything.plugin.votePoints.get(p.getName()) != Integer.parseInt(entry.getString("votePoints"))) {
                            statement.executeUpdate("UPDATE " + table + " SET votePoints = '" + ProEverything.plugin.votePoints.get(p.getName()) + "' WHERE PlayersUUID = '" + uuid + "';");
                        }
                    }
                    ProEverything.plugin.checkVotes(votes +1,uuid, p);
                    //CODE ENDS HERE

                } catch (SQLException e) { e.printStackTrace(); } catch (ClassNotFoundException e) { e.printStackTrace(); }
            }
        }.runTaskAsynchronously(ProEverything.plugin);
    }

    public void removeVotePoint(Player p, String uuid, int points) {
        new BukkitRunnable() {
            public void run() {
                try {
                    openConnection();
                    statement = connection.createStatement();

                    //CODE STARTS HERE
                    statement = connection.createStatement();
                    ResultSet entry = statement.executeQuery("SELECT votePoints FROM "+table+" WHERE PlayersUUID='" + uuid + "';");
                    entry.next();
                    int votes = entry.getInt("votePoints");
                    statement.executeUpdate("UPDATE " + table + " SET votePoints = '" + (votes - points) + "' WHERE PlayersUUID = '" + uuid + "';");
                    if (ProEverything.plugin.votePoints.containsKey(p.getName()))
                        ProEverything.plugin.votePoints.put(p.getName(), ProEverything.plugin.votePoints.get(p.getName())-points);
                    //CODE ENDS HERE

                } catch (SQLException e) { e.printStackTrace(); } catch (ClassNotFoundException e) { e.printStackTrace(); }
            }
        }.runTaskAsynchronously(ProEverything.plugin);
    }

    public void addVotePoint(Player p, String uuid, int points) {
        new BukkitRunnable() {
            public void run() {
                try {
                    openConnection();
                    statement = connection.createStatement();

                    //CODE STARTS HERE
                    statement = connection.createStatement();
                    ResultSet entry = statement.executeQuery("SELECT votePoints FROM "+table+" WHERE PlayersUUID='" + uuid + "';");
                    entry.next();
                    int votes = entry.getInt("votePoints");
                    statement.executeUpdate("UPDATE " + table + " SET votePoints = '" + (votes + points) + "' WHERE PlayersUUID = '" + uuid + "';");
                    if (!ProEverything.plugin.votePoints.containsKey(p.getName()))
                        ProEverything.plugin.votePoints.put(p.getName(), 0);
                    ProEverything.plugin.votePoints.put(p.getName(), ProEverything.plugin.votePoints.get(p.getName())+points);
                    //CODE ENDS HERE

                } catch (SQLException e) { e.printStackTrace(); } catch (ClassNotFoundException e) { e.printStackTrace(); }
            }
        }.runTaskAsynchronously(ProEverything.plugin);
    }


    private void openConnection() throws SQLException, ClassNotFoundException {
        if (connection != null && !connection.isClosed()) {
            return;
        }

        synchronized (this) {
            if (connection != null && !connection.isClosed()) {
                return;
            }
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + this.host+ ":" + this.port + "/" + this.database + "?useSSL=false&allowMultiQueries=true", this.username, this.password);
        }
    }
}
