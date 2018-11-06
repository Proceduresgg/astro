package me.procedures.penguin.listeners;

import lombok.AllArgsConstructor;
import me.procedures.penguin.managers.ManagerHandler;
import me.procedures.penguin.player.impl.PlayerProfile;
import me.procedures.penguin.utils.GameUtils;
import me.procedures.penguin.utils.StateInventories;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@AllArgsConstructor
public class PlayerListener implements Listener {

    private ManagerHandler managerHandler;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerProfile profile = this.managerHandler.getProfileManager().getProfile(player);

        GameUtils.resetPlayer(player);
        player.getInventory().setContents(StateInventories.LOBBY.getContents());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerProfile profile = this.managerHandler.getProfileManager().getProfiles().remove(player.getUniqueId());

        profile.save();
    }
}
