package me.procedures.penguin.match.api;

import lombok.Getter;
import lombok.Setter;
import me.procedures.penguin.PenguinPlugin;
import me.procedures.penguin.arena.Arena;
import me.procedures.penguin.ladder.impl.Ladder;
import me.procedures.penguin.player.PlayerProfile;
import me.procedures.penguin.player.PlayerState;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public abstract class Match {

    private final PenguinPlugin plugin;

    private UUID identifier;
    private Ladder ladder;
    private Long startMillis;
    private Arena arena;

    private MatchStatus matchStatus = MatchStatus.STARTING;

    private List<Player> spectators = new ArrayList<>();

    public Match(PenguinPlugin plugin) {
        this.plugin = plugin;
        this.identifier = UUID.randomUUID();
    }

    public void startMatch() {
        this.startMillis = System.currentTimeMillis();
    }

    public void spawnPlayers(Player... players) {
        for (Player player : players) {
            PlayerProfile profile = this.getPlugin().getProfileManager().getProfile(player);

            player.spigot().setCollidesWithEntities(true);
            player.setCanPickupItems(true);
            player.setAllowFlight(false);
            player.setFlying(false);

            this.getPlugin().getPlayerHider().hideAllPlayers(player);

            profile.setPlayerState(PlayerState.FIGHTING);

            for (Player opponent : players) {
                if (opponent == player) continue;

                player.showPlayer(opponent);
            }
        }
    }
}
