package me.d3li0n.deathplus.commands;

import me.d3li0n.deathplus.utils.FileManager;
import me.d3li0n.deathplus.utils.PlayerManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerCommands implements CommandExecutor {
    private PlayerManager playerManager;
    private final FileManager fileManager;

    public PlayerCommands(PlayerManager playerManager, FileManager fileManager) {
        this.playerManager = playerManager;
        this.fileManager = fileManager;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player) {
            if (s.equalsIgnoreCase("deaths")) {
                if (commandSender.hasPermission("deathplus.deaths"))
                    if (args.length == 0)
                        commandSender.sendMessage(ChatColor.
                            translateAlternateColorCodes('&',
                                fileManager.getLangConfig().getString("commands.deaths.message")
                                        .replace("%deaths%",
                                                "" + this.playerManager.getDeaths(((Player) commandSender).getUniqueId()))));
                    else {
                        Player target = Bukkit.getServer().getPlayer(args[0]);
                        if (target == null || !target.isOnline()) {
                            commandSender.sendMessage(ChatColor.
                                    translateAlternateColorCodes('&', fileManager.getLangConfig().getString("errors.commands.not-found")
                                    .replace("%player%", target.getName())));
                            return true;
                        }
                        commandSender.sendMessage(ChatColor.
                                translateAlternateColorCodes('&',
                                        fileManager.getLangConfig().getString("commands.deaths.other_player")
                                                .replace("%deaths%",
                                                        "" + this.playerManager.getDeaths((target.getUniqueId())))
                                                .replace("%player%",
                                                        "" + target.getName())));
                    }
                else
                    commandSender.sendMessage(ChatColor.
                            translateAlternateColorCodes('&',
                                    fileManager.getLangConfig().getString("errors.commands.no-permissions")));
            }
            return true;
        }
        Bukkit.getLogger().info(fileManager.getLangConfig().getString("errors.commands.no-permissions"));
        return false;
    }
}
