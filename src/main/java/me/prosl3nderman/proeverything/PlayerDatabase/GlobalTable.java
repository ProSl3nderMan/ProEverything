package me.prosl3nderman.proeverything.PlayerDatabase;

import me.prosl3nderman.proeverything.ProEverything;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.*;

public class GlobalTable {

    private Connection connection;
    private Statement statement;
    private String host, database, username, password, table;
    private int port;
    private String THEJOINDATE, THELASTJOIN, THEHOURS;

    public GlobalTable() {
        host = "172.17.0.1";
        port = 3306;
        database = "players";
        username = "proeverything";
        password = "pr0Sk1lls15";
        table = "global";
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

                        p.sendMessage(ChatColor.LIGHT_PURPLE + "------ " + ChatColor.DARK_PURPLE + IGN + "'s Global Stats" + ChatColor.LIGHT_PURPLE + " ------");
                        p.sendMessage(ChatColor.DARK_PURPLE + "Join Date: " + ChatColor.LIGHT_PURPLE + THEJOINDATE);
                        p.sendMessage(ChatColor.DARK_PURPLE + "Last Login: " + ChatColor.LIGHT_PURPLE + THELASTJOIN);
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
                    p.sendMessage(ChatColor.LIGHT_PURPLE + "------ " + ChatColor.DARK_PURPLE + "Top Hours GloballyIGN" + ChatColor.LIGHT_PURPLE + " ------");
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
                        p.sendMessage(ChatColor.DARK_PURPLE + IGN + ChatColor.LIGHT_PURPLE + " was last online, globally, " + ChatColor.WHITE + entry.getString("lastLogin") + ChatColor.LIGHT_PURPLE + "!");
                    else
                        p.sendMessage(ChatColor.RED + "The player " + ChatColor.WHITE + IGN + ChatColor.RED + " does not exist!");
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
            connection = DriverManager.getConnection("jdbc:mysql://" + this.host+ ":" + this.port + "/" + this.database + "?useSSL=false", this.username, this.password);
        }
    }
}
