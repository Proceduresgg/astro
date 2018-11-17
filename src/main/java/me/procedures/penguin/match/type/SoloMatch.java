package me.procedures.penguin.match.type;

import lombok.Getter;
import me.procedures.penguin.PenguinPlugin;
import me.procedures.penguin.inventories.StateInventories;
import me.procedures.penguin.match.AbstractMatch;
import me.procedures.penguin.match.MatchOption;
import me.procedures.penguin.player.PlayerProfile;
import me.procedures.penguin.utils.GameUtil;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;
import java.util.Set;

@Getter
public class SoloMatch extends AbstractMatch {

    private Player playerOne;
    private Player playerTwo;

    private Set<MatchOption> matchOptions;

    public SoloMatch(PenguinPlugin plugin, Player playerOne, Player playerTwo, Set<MatchOption> matchOptions) {
        super(plugin);

        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.matchOptions = matchOptions;

        this.playerOne.teleport(this.getArena().getSpawnOne());
        this.playerTwo.teleport(this.getArena().getSpawnTwo());

        this.spawnPlayers(playerOne, playerTwo);

        this.matchOptions.forEach(matchOption -> matchOption.startPreGame(playerOne, playerTwo));
    }

    public void endMatch(Player winner, Player loser) {
        PlayerProfile winnerProfile = this.getPlugin().getProfileManager().getProfile(winner);
        PlayerProfile loserProfile = this.getPlugin().getProfileManager().getProfile(loser);

        this.resetPlayers(winner, loser);

        GameUtil.resetPlayer(winner);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (winner.isOnline()) {

                    winner.getInventory().setContents(StateInventories.LOBBY.getContents());
                    winner.updateInventory();

                    winner.teleport(winner.getWorld().getSpawnLocation());
                }
            }
        }.runTaskLater(this.getPlugin(), 100L);
    }

    public void handleDeath(Player player, Location location, String deathMessage) {
        if (!deathMessage.contains("has left the match")) {
            this.playSound(Sound.AMBIENCE_THUNDER, 10.0F, this.playerOne, this.playerTwo);
        }

        player.getInventory().setContents(StateInventories.LOBBY.getContents());
        player.updateInventory();

        player.teleport(player.getWorld().getSpawnLocation());

        this.sendMessage(deathMessage);

        this.playerOne.hidePlayer(this.playerTwo);
        this.playerTwo.hidePlayer(this.playerOne);

        this.endMatch(player == this.playerOne ? this.playerTwo : this.playerOne, player == this.playerOne ? this.playerOne : this.playerTwo);
    }
}