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
public abstract class AbstractMatch {

    private final PenguinPlugin plugin;

    private final List<Player> spectators = new ArrayList<>();

    private UUID uuid;
    private Ladder ladder;
    private Arena arena;

    private MatchStatus status = MatchStatus.STARTING;

    private long startTime;

    public AbstractMatch(PenguinPlugin plugin) {
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

            this.getPlugin().getPlayerUtil().hideAllPlayers(player);

            for (Player opponent : players) {
                if (opponent == player) {
                	continue;
                }

                player.showPlayer(opponent);
            }
        }
    }
}