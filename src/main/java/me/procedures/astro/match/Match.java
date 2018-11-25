package me.procedures.astro.match;

import lombok.Getter;
import lombok.Setter;
import me.procedures.astro.AstroPlugin;
import me.procedures.astro.arena.Arena;
import me.procedures.astro.inventories.StateInventories;
import me.procedures.astro.ladder.Ladder;
import me.procedures.astro.player.PlayerProfile;
import me.procedures.astro.player.PlayerState;
import me.procedures.astro.utils.GameUtil;
import me.procedures.astro.utils.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

/**
 * Updated the match system so that it's more flexible with party matches.
 */

@Getter
@Setter
public class Match {

    private final AstroPlugin plugin;

    /* Boolean value represents whether the player is alive.
       TRUE if they're alive, FALSE otherwise. */
    private final Map<Player, Boolean> redTeam = new HashMap<>();
    private final Map<Player, Boolean> blueTeam = new HashMap<>();

    private final List<Player> spectators = new ArrayList<>();
    private final List<MatchOption> matchOptions;

    private UUID uuid;
    private Ladder ladder;
    private Arena arena;

    private MatchStatus status = MatchStatus.STARTING;

    private long startTime;

    public Match(AstroPlugin plugin, Ladder ladder, List<Player> redTeam, List<Player> blueTeam, List<MatchOption> matchOptions) {
        this.plugin = plugin;
        this.ladder = ladder;
        this.matchOptions = matchOptions;
        this.uuid = UUID.randomUUID();

        redTeam.forEach(player -> {
            this.redTeam.put(player, true);
            player.teleport(Bukkit.getWorld("world").getSpawnLocation()); // TODO: Make the player teleport to the arena spawn points
        });

        blueTeam.forEach(player -> {
            this.blueTeam.put(player, true);
            player.teleport(Bukkit.getWorld("world").getSpawnLocation());
        });

        this.spawnPlayers();
        this.matchOptions.forEach(matchOption -> matchOption.startPreGame(this));
    }

    public void startMatch() {
        this.startTime = System.currentTimeMillis();
        this.status = MatchStatus.ONGOING;
    }

    public void endMatch(Map<Player, Boolean> winners, Map<Player, Boolean> losers) {
        winners.keySet().forEach(GameUtil::resetPlayer);

        this.resetPlayers();

        new BukkitRunnable() {
            @Override
            public void run() {
                winners.keySet().forEach(winner -> {
                    if (winner.isOnline()) {
                        if (winners.get(winner)) {

                            winner.getInventory().setContents(StateInventories.LOBBY.getContents());
                            winner.updateInventory();

                            winner.teleport(winner.getWorld().getSpawnLocation());
                        }
                    }
                });
            }
        }.runTaskLater(this.getPlugin(), 100L);
    }

    public void handleDeath(Player player, Location location, String deathMessage) {
        if (!deathMessage.contains("has left the match")) {
            this.playSound(Sound.AMBIENCE_THUNDER, 10.0F);
        }

        player.getInventory().setContents(StateInventories.LOBBY.getContents());
        player.updateInventory();

        player.teleport(player.getWorld().getSpawnLocation());

        this.sendMessage(deathMessage);

        this.getAllPlayers().forEach(alivePlayer -> alivePlayer.hidePlayer(player));

        if (!this.redTeam.values().contains(true)) {
            this.endMatch(this.blueTeam, this.redTeam);

        } else if (!this.blueTeam.values().contains(true)) {
            this.endMatch(this.redTeam, this.blueTeam);
        }
    }

    public void spawnPlayers() {
        this.getAllPlayers().forEach(player -> {
            PlayerProfile profile = this.getPlugin().getProfileManager().getProfile(player);

            GameUtil.resetPlayer(player);

            player.getInventory().setContents(profile.getKits(this.ladder));
            player.spigot().setCollidesWithEntities(true);
            player.setCanPickupItems(true);
            player.setAllowFlight(false);
            player.setFlying(false);

            profile.setState(PlayerState.FIGHTING);
            profile.setMatch(this);

            PlayerUtil.hideAllPlayers(player);

            this.getAllPlayers().forEach(opponent -> {
                if (opponent != player) {
                    player.showPlayer(opponent);
                }
            });
        });
    }

    public void resetPlayers() {
        this.getAllPlayers().forEach(player -> {
            PlayerProfile profile = this.plugin.getProfileManager().getProfile(player);

            player.setMaximumNoDamageTicks(20);

            profile.setState(PlayerState.LOBBY);
        });
    }

    public void cleanSpectators() {

    }

    public void playSound(Sound sound, float idk2) {
        this.getAllPlayers().forEach(player -> player.playSound(player.getLocation(), sound, 10.0F, idk2));

        this.getSpectators().forEach(spectator -> spectator.playSound(spectator.getLocation(), sound, 10.0F, idk2));
    }

    public void sendMessage(String message) {
        this.getAllPlayers().forEach(player -> player.sendMessage(message));

        this.getSpectators().forEach(spectator -> spectator.sendMessage(message));
    }

    public List<Player> getAllPlayers() {
        List<Player> players = new ArrayList<>();

        this.blueTeam.keySet().forEach(player -> {
            if (this.blueTeam.get(player)) {
                players.add(player);
            }
        });

        this.redTeam.keySet().forEach(player -> {
            if (this.redTeam.get(player)) {
                players.add(player);
            }
        });

        return players;
    }
}
