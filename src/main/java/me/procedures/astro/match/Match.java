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
import me.procedures.astro.queue.AbstractQueue;
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

@Getter @Setter
public class Match {

    private final AstroPlugin plugin;

    private final Map<Player, MatchPlayer> players = new HashMap<>();

    private final List<Player> spectators = new ArrayList<>();
    private final List<MatchOption> matchOptions;

    private UUID uuid;
    private AbstractQueue queue;
    private Ladder ladder;
    private Arena arena;

    private MatchStatus status = MatchStatus.STARTING;

    private long startTime;

    public Match(AstroPlugin plugin, AbstractQueue queue, Ladder ladder, List<Player> teamOne, List<Player> teamTwo, List<MatchOption> matchOptions) {
        this.plugin = plugin;
        this.uuid = UUID.randomUUID();
        this.queue = queue;
        this.ladder = ladder;
        this.matchOptions = matchOptions;

        teamOne.forEach(player -> {
            this.players.put(player, new MatchPlayer(player, MatchTeam.RED, false));
            player.teleport(Bukkit.getWorld("world").getSpawnLocation()); // TODO: Make the player teleport to the arena spawn points
        });

        teamTwo.forEach(player -> {
            this.players.put(player, new MatchPlayer(player, MatchTeam.BLUE, false));
            player.teleport(Bukkit.getWorld("world").getSpawnLocation());
        });

        this.spawnPlayers();
        this.matchOptions.forEach(matchOption -> matchOption.startPreGame(this));

        new MatchStartRunnable(this, 5).runTaskTimer(this.plugin, 0L, 20L);
    }

    public Match(AstroPlugin plugin, AbstractQueue queue, Ladder ladder, Player playerOne, Player playerTwo, MatchOption option) {
        this(plugin, queue, ladder, Collections.singletonList(playerOne), Collections.singletonList(playerTwo), Collections.singletonList(option));
    }

    public void startMatch() {
        this.startTime = System.currentTimeMillis();
        this.status = MatchStatus.ONGOING;
    }

    public void endMatch(MatchTeam winner) { // TODO: Handle losers
        Set<Map.Entry<Player, MatchPlayer>> winners = this.getPlayers().entrySet()
                .stream()
                .filter(entry -> entry.getValue().getTeam() == winner)
                .collect(Collectors.toSet());

        winners.forEach(entry -> GameUtil.resetPlayer(entry.getKey()));

        this.resetPlayers();
        this.queue.handleMatch(this);

        new BukkitRunnable() {
            @Override
            public void run() {
                getPlayers().entrySet()
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

            this.getPlayers().keySet().stream()
                    .filter(alivePlayer -> alivePlayer != player)
                    .forEach(alivePlayer -> alivePlayer.hidePlayer(player));

            GameUtil.teleportToSpawn(player);
        }

        this.getPlayers().get(player).setDead(true);

        this.sendMessage(deathMessage);

        /* Checks if all other players on their team are dead, if TRUE, end the match.*/
        List<Boolean> alive = this.players.values().stream()
                .filter(matchPlayer -> matchPlayer.getTeam() == this.players.get(player).getTeam())
                .map(MatchPlayer::isDead)
                .collect(Collectors.toList());

        MatchTeam winner = MatchTeam.getOpposite(this.players.get(player).getTeam());

        if (!alive.contains(false)) {
            this.endMatch(winner);

        } else {
            if (player.isOnline()) {
                this.plugin.getSpectatorManager().startSpectating(player, this.getTeam(winner).get(0));
            }
        }
    }

    public void spawnPlayers() {
        this.players.keySet().forEach(player -> {
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

            this.players.keySet()
                    .stream()
                    .filter(opponent -> opponent != player)
                    .forEach(player::showPlayer);
        });
    }

    public void resetPlayers() {
        this.players.keySet().forEach(player -> {
            PlayerProfile profile = this.plugin.getProfileManager().getProfile(player);

            player.setMaximumNoDamageTicks(20);

            profile.setState(PlayerState.LOBBY);
        });
    }

    public void cleanSpectators() {
        this.spectators.forEach(GameUtil::teleportToSpawn);
    }

    public void playSound(Sound sound, Location location, float idk2) {
        this.players.keySet().forEach(player -> player.playSound(location, sound, 10.0F, idk2));

        this.getSpectators().forEach(spectator -> spectator.playSound(location, sound, 10.0F, idk2));
    }

    public void sendMessage(String message) {
        this.players.keySet().forEach(player -> player.sendMessage(message));

        this.getSpectators().forEach(spectator -> spectator.sendMessage(message));
    }

    public List<Player> getTeam(MatchTeam team) {
        List<Player> players = new ArrayList<>();

        this.players.forEach((player, matchPlayer) -> {
            if (matchPlayer.getTeam() == team) {
                players.add(player);
            }
        });

        return players;
    }
}
