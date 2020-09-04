package me.d3li0n.deathplus.listeners;

import me.d3li0n.deathplus.utils.FileManager;
import me.d3li0n.deathplus.utils.PlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener {
    private final FileManager manager;
    private PlayerManager playerManager;

    public PlayerDeath(FileManager manager, PlayerManager playerManager) {
        this.manager = manager;
        this.playerManager = playerManager;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity().getPlayer();
        event.setDeathMessage(null);

        if (!player.isOp() || !player.hasPermission("deathplus.bypass")) {

            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6------------------------------------------------------"));
            player.sendMessage("");

            this.playerManager.addDeath(event.getEntity().getUniqueId());

            int graceLives = this.manager.getConfigFile().getInt("general.player.graceLiveCount") - this.playerManager.getDeaths(event.getEntity().getUniqueId());

            if (this.manager.getConfigFile().getBoolean("general.player.graceEnabled") && graceLives >= 0) {
                if (graceLives > 0) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            this.manager.getLangConfig().getString("messages.grace_period")
                                    .replace("%lives%", "" + graceLives)));
                }
                else if (graceLives == 0) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            this.manager.getLangConfig().getString("messages.grace_final")));
                }
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        this.manager.getLangConfig().getString("messages.player_death_message")));
            }

            player.sendMessage("");
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6------------------------------------------------------"));
        }
    }
}
