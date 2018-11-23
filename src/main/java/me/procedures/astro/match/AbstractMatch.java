package me.procedures.astro.match;

import lombok.Getter;
import lombok.Setter;
import me.procedures.astro.AstroPlugin;
import me.procedures.astro.arena.Arena;
import me.procedures.astro.ladder.Ladder;
import me.procedures.astro.player.PlayerProfile;
import me.procedures.astro.player.PlayerState;
import me.procedures.astro.utils.GameUtil;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public abstract class AbstractMatch {

    private final AstroPlugin plugin;

    private final List<Player> spectators = new ArrayList<>();

    private UUID uuid;
    private Ladder ladder;
    private Arena arena;

    private MatchStatus status = MatchStatus.STARTING;

    private long startTime;

    public AbstractMatch(AstroPlugin plugin, Ladder ladder) {
        this.plugin = plugin;
        this.uuid = UUID.randomUUID();
    }

    public void startMatch() {
        this.startTime = System.currentTimeMillis();
        this.status = MatchStatus.ONGOING;
    }

    public void spawnPlayers(Player... players) {
        for (Player player : players) {
            PlayerProfile profile = this.getPlugin().getProfileManager().getProfile(player);

            player.spigot().setCollidesWithEntities(true);
            player.setCanPickupItems(true);
            player.setAllowFlight(false);
            player.setFlying(false);

            profile.setState(PlayerState.FIGHTING);
            profile.setMatch(this);

            Arrays.stream(players)
                    .filter(opponent -> opponent != player)
                    .forEach(player::showPlayer);

            this.getPlugin().getPlayerUtil().hideAllPlayers(player);

            GameUtil.resetPlayer(player);
            player.getInventory().setContents(profile.getKits(ladder));
        }
    }

    public void resetPlayers(Player... players) {
        for (Player player : players) {
            PlayerProfile profile = this.plugin.getProfileManager().getProfile(player);

            player.setMaximumNoDamageTicks(20);

            profile.setState(PlayerState.LOBBY);
        }
    }

    public void cleanSpectators() {

    }

    public void playSound(Sound sound, float idk2, Player... players) {
        for (Player player : players) {
            player.playSound(player.getLocation(), sound, 10.0f, idk2);
        }

        for (Player spectator : this.getSpectators()) {
            spectator.playSound(spectator.getLocation(), sound, 10.0F, idk2);
        }
    }

    public void sendMessage(String message, Player... players) {
        for (Player player : players) {
            player.sendMessage(message);
        }

        for (Player spectator : this.getSpectators()) {
            spectator.sendMessage(message);
        }
    }
}
