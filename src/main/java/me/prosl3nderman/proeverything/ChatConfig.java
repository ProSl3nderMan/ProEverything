package me.prosl3nderman.proeverything;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.logging.Level;

public class ChatConfig {
    private FileConfiguration customConfig = null;
    private File customConfigFile = null;
    private String dir = ProEverything.plugin.getDataFolder() + "";

    public void reloadConfig() {
        if (customConfigFile == null) {
            customConfigFile = new File(dir, "chat.yml");
        }
        if (!customConfigFile.exists()) {
            ProEverything.plugin.saveResource("chat.yml", false);
        }
        customConfig = YamlConfiguration.loadConfiguration(customConfigFile);

        Reader defConfigStream = null;
        try {
            defConfigStream = new InputStreamReader(ProEverything.plugin.getResource("chat.yml"), "UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            customConfig.setDefaults(defConfig);
        }
    }

    public FileConfiguration getConfig() {
        if (customConfig == null) {
            reloadConfig();
        }
        return customConfig;
    }

    public void saveConfig() {
        if (customConfig == null || customConfigFile == null) {
            return;
        }
        try {
            getConfig().save(customConfigFile);
        } catch (IOException ex) {
            ProEverything.plugin.getLogger().log(Level.SEVERE, "Could not save config to " + customConfigFile, ex);
        }
    }

    public void srConfig() {
        saveConfig();
        reloadConfig();
    }
}
