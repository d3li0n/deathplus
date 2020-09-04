package me.d3li0n.deathplus.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class PlayerManager {
    private Map<UUID, Character> players;

    public PlayerManager () {
        this.players = new HashMap<UUID, Character>();
    }

    public void add(final Character player) {
        final UUID p = player.getPlayer();
        if (!this.players.containsKey(p)) this.players.put(p, player);
    }

    public void loadPlayers(FileManager manager) {
        Bukkit.getLogger().info("[DeathPlus] Loading player data");
        long time = System.currentTimeMillis();
        try {
            for (String key : manager.getPlayerFile().getConfigurationSection("players").getKeys(false))
                this.add(new Character(UUID.fromString(key), manager.getPlayerFile().getInt("players." + key + ".deaths")));
            Bukkit.getLogger().info("[DeathPlus] Player data has been loaded. Action took: " + (System.currentTimeMillis() - time) + " ms.");
        } catch (NullPointerException e) {
            Bukkit.getLogger().warning("[DeathPlus] Player data failed to load. Bypassing..");
            time = System.currentTimeMillis();
            for(Player player : Bukkit.getOnlinePlayers())
                this.add(new Character(player.getUniqueId(), 0));
            Bukkit.getLogger().info("[DeathPlus] Player data has been created. Action took: " + (System.currentTimeMillis() - time) + " ms.");
        }
    }

    public void savePlayers(FileManager manager, JavaPlugin plugin) {

        Iterator<Map.Entry<UUID, Character>> it = this.players.entrySet().iterator();

        Bukkit.getLogger().info("[DeathPlus] Saving player data");
        long time = System.currentTimeMillis();
        while (it.hasNext()) {
            Map.Entry<UUID, Character> pair = it.next();
            manager.getPlayerFile().set("players." + pair.getKey() + ".deaths", pair.getValue().getDeaths());
            it.remove();
        }

        try {
            manager.getPlayerFile().save(new File(plugin.getDataFolder(), "players.yml"));
            Bukkit.getLogger().info("[DeathPlus] Player data has been saved. Action took: " + (System.currentTimeMillis() - time) + " ms.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addDeath(final UUID player) {
        this.players.get(player).addDeaths();
    }

    public int getDeaths(final UUID player) {
        return this.players.get(player).getDeaths();
    }
}
