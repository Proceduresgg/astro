package me.procedures.penguin.listeners;

import lombok.AllArgsConstructor;
import me.procedures.penguin.PenguinPlugin;
import me.procedures.penguin.player.PlayerProfile;
import me.procedures.penguin.utils.GameUtil;
import me.procedures.penguin.inventories.StateInventories;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@AllArgsConstructor
public class PlayerListener implements Listener {

    private final PenguinPlugin plugin;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerProfile profile = this.plugin.getProfileManager().getProfile(player);

        GameUtil.resetPlayer(player);

        player.getInventory().setContents(StateInventories.LOBBY.getContents());
        player.updateInventory();

        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> onlinePlayer.hidePlayer(player));

        Bukkit.getOnlinePlayers().stream()
                .filter(onlinePlayer -> !onlinePlayer.hasPermission("gg.donor"))
                .forEach(player::hidePlayer);

        player.sendMessage(PenguinPlugin.SERVER_COLOR_BRIGHT + "Practice remastered, " + PenguinPlugin.SERVER_COLOR_LIGHT + "Created by Procedures / Dewgs.");

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerProfile profile = this.plugin.getProfileManager().getProfiles().remove(player.getUniqueId());
    }
}
