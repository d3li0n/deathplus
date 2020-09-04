package me.d3li0n.deathplus.listeners;

import me.d3li0n.deathplus.utils.FileManager;
import me.d3li0n.deathplus.utils.PlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;

import java.util.Collection;
import java.util.Iterator;

public class PlayerConsume implements Listener {
    private FileManager fileManager;
    private PlayerManager playerManager;

    public PlayerConsume(FileManager fileManager, PlayerManager playerManager) {
        this.fileManager = fileManager;
        this.playerManager = playerManager;
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onConsumeItem(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();

        if (this.fileManager.getConfigFile().getBoolean("general.on-player-consume-milk")) {
            if (!player.isOp() || !player.hasPermission("deathplus.bypass")) {
                if (event.getItem().getType().equals(Material.MILK_BUCKET)) {
                    int graceLives = this.fileManager.getConfigFile().getInt("general.player.graceLiveCount") - this.playerManager.getDeaths(event.getPlayer().getUniqueId());

                    Collection<PotionEffect> listOfEffects = player.getActivePotionEffects();

                    for (Iterator iterator = listOfEffects.iterator(); iterator.hasNext(); ) {
                        PotionEffect effect = (PotionEffect) iterator.next();
                        if (effect.getAmplifier() > 6) {
                            if (this.fileManager.getConfigFile().getBoolean("general.player.graceEnabled") && graceLives < 0) {
                                event.setCancelled(true);
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                        this.fileManager.getLangConfig().getString("errors.use.milk")));
                            } else if (!this.fileManager.getConfigFile().getBoolean("general.player.graceEnabled")) {
                                event.setCancelled(true);
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                        this.fileManager.getLangConfig().getString("errors.use.milk")));
                            }
                            break;
                        }
                    }
                }
            }
        }
    }
}
