package me.d3li0n.deathplus;

import me.d3li0n.deathplus.commands.PlayerCommands;
import me.d3li0n.deathplus.listeners.PlayerConsume;
import me.d3li0n.deathplus.listeners.PlayerDeath;
import me.d3li0n.deathplus.listeners.PlayerJoin;
import me.d3li0n.deathplus.listeners.PlayerRespawn;
import me.d3li0n.deathplus.utils.FileManager;
import me.d3li0n.deathplus.utils.PlayerManager;
import me.d3li0n.deathplus.utils.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private FileManager manager;
    private PlayerManager playerManager;

    @Override
    public void onEnable() {
        this.manager = new FileManager(this);

        if (!manager.isEnabled()) {
            Bukkit.getLogger().info("[Death Plus] Plugin is disabled. If you want to enable it, check your config.yml.");
            Bukkit.getPluginManager().disablePlugin(this);
        } else {

            if (!new UpdateChecker(this).isUpdated()) Bukkit.getLogger().warning("[DeathPlus] Current version seems to be out of date. Please update this plugin, as new versions may contain security fixes.");
            else Bukkit.getLogger().info("[DeathPlus] Current version is up to date.");

            this.manager.readLangFile(getPluginLang());

            this.playerManager = new PlayerManager();
            this.playerManager.loadPlayers(this.manager);
            registerEvents();

            getCommand("deaths").setExecutor(new PlayerCommands(this.playerManager, this.manager));
        }
    }

    @Override
    public void onDisable() {
        this.playerManager.savePlayers(this.manager, this);
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new PlayerJoin(this.manager, this.playerManager), this);
        getServer().getPluginManager().registerEvents(new PlayerDeath(this.manager, this.playerManager), this);
        getServer().getPluginManager().registerEvents(new PlayerRespawn(this.manager, this.playerManager, this), this);
        getServer().getPluginManager().registerEvents(new PlayerConsume(this.manager, this.playerManager), this);
    }

    public String getPluginLang() {
        return this.manager.getConfigFile().getString("general.language");
    }
}
