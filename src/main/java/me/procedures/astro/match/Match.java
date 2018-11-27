package me.procedures.astro.match;

import lombok.Getter;
import lombok.Setter;
import me.procedures.astro.AstroPlugin;
import me.procedures.astro.arena.Arena;
import me.procedures.astro.ladder.Ladder;
import me.procedures.astro.match.team.MatchPlayer;
import me.procedures.astro.match.team.MatchTeam;
import me.procedures.astro.player.PlayerProfile;
import me.procedures.astro.player.PlayerState;
import me.procedures.astro.runnables.MatchStartRunnable;
import me.procedures.astro.utils.GameUtil;
import me.procedures.astro.utils.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Updated the match system so that it's more flexible with party matches.
 */

@Getter
@Setter
public class Match {

    private final AstroPlugin plugin;

    private final Map<Player, MatchPlayer> matchPlayers = new HashMap<>();

    private final List<Player> spectators = new ArrayList<>();
    private final List<MatchOption> matchOptions;

    private UUID uuid;
    private Ladder ladder;
    private Arena arena;

    private MatchStatus status = MatchStatus.STARTING;

    private long startTime;

    public Match(AstroPlugin plugin, Ladder ladder, List<Player> teamOne, List<Player> teamTwo, List<MatchOption> matchOptions) {
        this.plugin = plugin;
        this.ladder = ladder;
        this.matchOptions = matchOptions;
        this.uuid = UUID.randomUUID();

        teamOne.forEach(player -> {
            this.matchPlayers.put(player, new MatchPlayer(player, MatchTeam.RED, false));
            player.teleport(Bukkit.getWorld("world").getSpawnLocation()); // TODO: Make the player teleport to the arena spawn points
        });

        teamTwo.forEach(player -> {
            this.matchPlayers.put(player, new MatchPlayer(player, MatchTeam.BLUE, false));
            player.teleport(Bukkit.getWorld("world").getSpawnLocation());
        });

        this.spawnPlayers();
        this.matchOptions.forEach(matchOption -> matchOption.startPreGame(this));

        new MatchStartRunnable(this, 5).runTaskTimer(this.plugin, 20L, 0L);
    }

    public Match(AstroPlugin plugin, Ladder ladder, Player playerOne, Player playerTwo, MatchOption option) {
        this(plugin, ladder, Collections.singletonList(playerOne), Collections.singletonList(playerTwo), Collections.singletonList(option));
    }

    public void startMatch() {
        this.startTime = System.currentTimeMillis();
        this.status = MatchStatus.ONGOING;
    }

    public void endMatch(MatchTeam winner) { // TODO: Handle losers
        Set<Map.Entry<Player, MatchPlayer>> winners = this.getMatchPlayers().entrySet()
                .stream()
                .filter(entry -> entry.getValue().getTeam() == winner)
                .collect(Collectors.toSet());

        winners.forEach(entry -> GameUtil.resetPlayer(entry.getKey()));

        this.resetPlayers();

        new BukkitRunnable() {
            @Override
            public void run() {
                getMatchPlayers().entrySet()
                        .stream()
                        .filter(entry -> entry.getValue().getTeam() == winner)
                        .forEach(entry -> {
                            plugin.getProfileManager().getProfile(entry.getKey()).setState(PlayerState.LOBBY);
                            GameUtil.teleportToSpawn(entry.getKey());
                        });
            }
        }.runTaskLater(this.getPlugin(), 100L);
    }

    public void handleDeath(Player player, Location location, String deathMessage) {
        if (!deathMessage.contains("has left the match")) {
            this.playSound(Sound.AMBIENCE_THUNDER, location, 10.0F);

            this.plugin.getProfileManager().getProfile(player).setState(PlayerState.LOBBY);

            this.getMatchPlayers().keySet().stream()
                    .filter(alivePlayer -> alivePlayer != player)
                    .forEach(alivePlayer -> alivePlayer.hidePlayer(player));

            GameUtil.teleportToSpawn(player);
        }

        this.getMatchPlayers().get(player).setDead(true);

        this.sendMessage(deathMessage);

        /* Checks if all other players on their team are dead, if TRUE, end the match.*/
        List<Boolean> alive = this.matchPlayers.values().stream()
                .filter(matchPlayer -> matchPlayer.getTeam() == this.matchPlayers.get(player).getTeam())
                .map(MatchPlayer::isDead)
                .collect(Collectors.toList());

        if (!alive.contains(false)) {
            this.endMatch(MatchTeam.getOpposite(this.matchPlayers.get(player).getTeam()));
        }
    }

    public void spawnPlayers() {
        this.matchPlayers.keySet().forEach(player -> {
            PlayerProfile profile = this.getPlugin().getProfileManager().getProfile(player);

            profile.setState(PlayerState.FIGHTING);
            profile.setMatch(this);

            GameUtil.resetPlayer(player);

            player.getInventory().setContents(profile.getKits(this.ladder));
            player.spigot().setCollidesWithEntities(true);
            player.setCanPickupItems(true);
            player.setAllowFlight(false);
            player.setFlying(false);

            PlayerUtil.hideAllPlayers(player);

            this.matchPlayers.keySet()
                    .stream()
                    .filter(opponent -> opponent != player)
                    .forEach(player::showPlayer);
        });
    }

    public void resetPlayers() {
        this.matchPlayers.keySet().forEach(player -> {
            PlayerProfile profile = this.plugin.getProfileManager().getProfile(player);

            player.setMaximumNoDamageTicks(20);

            profile.setState(PlayerState.LOBBY);
        });
    }

    public void cleanSpectators() {

    }

    public void playSound(Sound sound, Location location, float idk2) {
        this.matchPlayers.keySet().forEach(player -> player.playSound(location, sound, 10.0F, idk2));

        this.getSpectators().forEach(spectator -> spectator.playSound(location, sound, 10.0F, idk2));
    }

    public void sendMessage(String message) {
        this.matchPlayers.keySet().forEach(player -> player.sendMessage(message));

        this.getSpectators().forEach(spectator -> spectator.sendMessage(message));
    }

    public List<Player> getPlayers(MatchTeam team) {
        List<Player> players = new ArrayList<>();

        this.matchPlayers.forEach((player, matchPlayer) -> {
            if (matchPlayer.getTeam() == team) {
                players.add(player);
            }
        });

        return players;
    }
}
