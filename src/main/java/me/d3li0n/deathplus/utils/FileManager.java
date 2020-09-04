package me.d3li0n.deathplus.utils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileManager {
    private final JavaPlugin plugin;
    private FileConfiguration configFile, playerConfig, langConfig;
    private final String[] language = {	"en" };

    public FileManager(JavaPlugin plugin) {
        this.plugin = plugin;
        createDefaultConfigFile();
        createLanguageConfigFile();
        readConfigFile();
    }

    public boolean isEnabled() {
        if (!configFile.getBoolean("general.on-enable")) return false;
        return true;
    }

    public FileConfiguration getLangConfig() { return this.langConfig; }

    public FileConfiguration getConfigFile() {
        return this.configFile;
    }

    public FileConfiguration getPlayerFile() {
        return this.playerConfig;
    }

    public void readConfigFile() {
        this.configFile = new YamlConfiguration();
        this.playerConfig = new YamlConfiguration();
        try {
            this.configFile.load(new File(plugin.getDataFolder(), "config.yml"));
            this.playerConfig.load(new File(plugin.getDataFolder(), "players.yml"));
        } catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }

    public void readLangFile(String file) {
        File langFile = new File(plugin.getDataFolder(), "/languages/" + file + ".yml");
        this.langConfig = new YamlConfiguration();
        try {
            this.langConfig.load(langFile);
        } catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }

    public void createLanguageConfigFile() {
        File folder = new File(plugin.getDataFolder() + "/languages/");
        if(!folder.exists()) folder.mkdir();

        for(int i = 0; i < language.length; i++) {
            File file = new File(plugin.getDataFolder() + "/languages/" + language[i] + ".yml");
            if(!file.exists()) {
                try {
                    plugin.saveResource(language[i] + ".yml", false);
                    Files.move(Paths.get(plugin.getDataFolder() + "/" + language[i] + ".yml"),
                            Paths.get(plugin.getDataFolder() + "/languages/" + language[i] + ".yml"));
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void createDefaultConfigFile() {
        File folder = plugin.getDataFolder();

        if (!folder.exists()) folder.mkdir();

        File config = new File(folder, "config.yml");
        File players = new File(folder, "players.yml");

        if (!config.exists() || !players.exists()) {
            Bukkit.getLogger().info("[DeathPlus] Configuration files were not found. Creating...");
            long time = System.currentTimeMillis();
            plugin.saveResource("config.yml", false);
            plugin.saveResource("players.yml", false);
            Bukkit.getLogger().info("[DeathPlus] Finished creating config files. Action took: " + (System.currentTimeMillis() - time) + " ms.");
        }
    }
}
