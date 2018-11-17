package me.procedures.penguin.match;

import lombok.Getter;
import lombok.Setter;
import me.procedures.penguin.PenguinPlugin;
import me.procedures.penguin.arena.Arena;
import me.procedures.penguin.ladder.impl.Ladder;
import me.procedures.penguin.player.PlayerProfile;
import me.procedures.penguin.player.PlayerState;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
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

            Arrays.stream(players)
                    .filter(opponent -> opponent != player)
                    .forEach(player::showPlayer);

            this.getPlugin().getPlayerUtil().hideAllPlayers(player);
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