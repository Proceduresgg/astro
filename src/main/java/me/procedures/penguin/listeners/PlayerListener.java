package me.procedures.penguin.listeners;

import lombok.AllArgsConstructor;
import me.procedures.penguin.PenguinPlugin;
import me.procedures.penguin.player.PlayerProfile;
import me.procedures.penguin.utils.GameUtil;
import me.procedures.penguin.utils.StateInventories;
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
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerProfile profile = this.plugin.getProfileManager().getProfiles().remove(player.getUniqueId());

        profile.save();
    }
}
