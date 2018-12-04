package me.procedures.astro.managers;

import lombok.Getter;
import me.procedures.astro.AstroPlugin;
import me.procedures.astro.inventories.StateInventories;
import me.procedures.astro.match.Match;
import me.procedures.astro.player.PlayerProfile;
import me.procedures.astro.player.PlayerState;
import me.procedures.astro.utils.GameUtil;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

@Getter
public class SpectatorManager {

    private final AstroPlugin plugin;

    private final Map<Player, Match> spectators = new HashMap<>();

    public SpectatorManager(AstroPlugin plugin) {
        this.plugin = plugin;
    }

    public void startSpectating(Player player, Player target) {
        PlayerProfile playerProfile = this.plugin.getProfileManager().getProfile(player);
        PlayerProfile targetProfile = this.plugin.getProfileManager().getProfile(target);

        playerProfile.setState(PlayerState.SPECTATING);

        GameUtil.resetPlayer(player);

        player.teleport(target);
        player.setFlying(true);
        player.setAllowFlight(true);
        player.getInventory().setContents(StateInventories.SPECTATOR.getContents());

        Match match = targetProfile.getMatch();

        match.getSpectators().add(player);
        match.getPlayers().keySet().forEach(player::showPlayer);
    }
}
