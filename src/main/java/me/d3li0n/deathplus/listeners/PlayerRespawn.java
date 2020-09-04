package me.d3li0n.deathplus.listeners;

import me.d3li0n.deathplus.utils.FileManager;
import me.d3li0n.deathplus.utils.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class PlayerRespawn implements Listener {
    private final FileManager manager;
    private PlayerManager playerManager;
    private JavaPlugin plugin;

    public PlayerRespawn(FileManager manager, PlayerManager playerManager, JavaPlugin plugin) {
        this.manager = manager;
        this.playerManager = playerManager;
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        if (!player.isOp() || !player.hasPermission("deathplus.bypass")) {
            int graceLives = this.manager.getConfigFile().getInt("general.player.graceLiveCount") - this.playerManager.getDeaths(event.getPlayer().getUniqueId());

            if (this.manager.getConfigFile().getBoolean("general.player.graceEnabled") && graceLives == 0)
                player.sendTitle(this.manager.getLangConfig().getString("title.final_death_after_grace"),
                        null, 20, 40, 20); // Tick equals = 20, divide by 20 to get seconds
            else if (this.manager.getConfigFile().getBoolean("general.player.graceEnabled") && graceLives < 0) runRandomizer(player);
            else if (!this.manager.getConfigFile().getBoolean("general.player.graceEnabled"))
                if (this.playerManager.getDeaths(event.getPlayer().getUniqueId()) == 1) {
                    player.sendTitle(this.manager.getLangConfig().getString("title.final_death_after_grace"),
                            null, 20, 40, 20); // Tick equals = 20, divide by 20 to get seconds
                    runRandomizer(player);
                } else if (this.playerManager.getDeaths(event.getPlayer().getUniqueId()) > 1) runRandomizer(player);
        }
    }

    public void runRandomizer(Player player) {
        int rand = (int)(Math.random() * 1000) + 1;


        if (rand > 60 && rand <= 200) {
            sendMessage(player, 1);
            String mes = "";
            List<String> list = manager.getLangConfig().getStringList("messages.kick_message");
            for (String message : list) mes += ChatColor.translateAlternateColorCodes('&', message) + "\n";
            player.kickPlayer(mes);
        } else {
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {
                public void run() {

                    if (rand <= 60) {
                        player.getInventory().addItem(new ItemStack(Material.WATER_BUCKET, 1));

                        double posX = (Math.random() * 10000) - 10010;
                        double posZ = (Math.random() * 10000) - 10010;

                        player.sendTitle(manager.getLangConfig().getString("title.mlg.name"),
                                manager.getLangConfig().getString("title.mlg.description"), 20, 40, 20);
                        sendMessage(player, 0);
                        player.teleport(new Location(player.getWorld(), posX, 200, posZ));

                    } else if (rand <= 800) {
                        int randLuck = (int) (Math.random() * 101);

                        if (randLuck > 20) {
                            sendMessage(player, 2);
                            PotionEffectType effect = getRandomEffect();
                            if (effect.equals(PotionEffectType.WITHER))
                                player.addPotionEffect(new PotionEffect(effect, (int) (Math.random() * 13) * 20, 10));
                            else player.addPotionEffect(new PotionEffect(effect, (int) (Math.random() * 400) * 20, 10));
                        } else {
                            sendMessage(player, 3);
                            Collection<PotionEffect> listOfEffects = Arrays.asList(
                                    new PotionEffect(PotionEffectType.BLINDNESS, (int) (Math.random() * 300) * 20, 10),
                                    new PotionEffect(PotionEffectType.HUNGER, (int) (Math.random() * 300) * 20, 10),
                                    new PotionEffect(PotionEffectType.SLOW, (int) (Math.random() * 300) * 20, 10),
                                    new PotionEffect(PotionEffectType.WEAKNESS, (int) (Math.random() * 300) * 20, 10),
                                    new PotionEffect(PotionEffectType.POISON, (int) (Math.random() * 300) * 20, 10),
                                    new PotionEffect(PotionEffectType.JUMP, (int) (Math.random() * 300) * 20, 250)
                            );
                            player.addPotionEffects(listOfEffects);
                        }
                    } else {
                        player.setHealth((Math.random() * 8));
                        player.setFoodLevel((int) ((Math.random() * 10) / 2));
                        sendMessage(player, 4);
                    }
                }
            }, (1));
        }
    }

    public PotionEffectType getRandomEffect() {
        PotionEffectType[] effects = { PotionEffectType.SLOW, PotionEffectType.BLINDNESS, PotionEffectType.HUNGER, PotionEffectType.POISON,
                                        PotionEffectType.HARM, PotionEffectType.SLOW_DIGGING, PotionEffectType.WITHER };
        int random = (int) (Math.random() * effects.length);
        return effects[random];
    }

    public void sendMessage(Player pl, int type) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            switch(type) {
                case 0: player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        manager.getLangConfig().getString("messages.players_death_message.challenge").replace("%player%", pl.getName()))); break;
                case 1: player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        manager.getLangConfig().getString("messages.players_death_message.kick").replace("%player%", pl.getName()))); break;
                case 2: player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        manager.getLangConfig().getString("messages.players_death_message.potion").replace("%player%", pl.getName()))); break;
                case 3: player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        manager.getLangConfig().getString("messages.players_death_message.potions").replace("%player%", pl.getName()))); break;
                case 4: player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        manager.getLangConfig().getString("messages.players_death_message.health").replace("%player%", pl.getName()))); break;
            }
        }
    }
}
