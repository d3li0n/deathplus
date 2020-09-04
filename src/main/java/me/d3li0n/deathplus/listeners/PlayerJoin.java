package me.d3li0n.deathplus.listeners;

import me.d3li0n.deathplus.utils.Character;
import me.d3li0n.deathplus.utils.FileManager;
import me.d3li0n.deathplus.utils.PlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {
    private final FileManager manager;
    private PlayerManager playerManager;

    public PlayerJoin(FileManager manager, PlayerManager playerManager) {
        this.manager = manager;
        this.playerManager = playerManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (player.isOp() || player.hasPermission("deathplus.*")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    this.manager.getLangConfig().getString("messages.admin_message")));
        } else {
            playerManager.add(new Character(player.getUniqueId(), 0));

            if (this.manager.getConfigFile().getBoolean("general.on-player-join-message")) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        this.manager.getLangConfig().getString("messages.player_message").replace("%deaths%",
                                "" + playerManager.getDeaths(player.getUniqueId()))));
            }
        }
    }
}
