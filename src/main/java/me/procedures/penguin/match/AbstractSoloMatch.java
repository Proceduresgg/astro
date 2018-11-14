package me.procedures.penguin.match;

import lombok.Getter;
import me.procedures.penguin.PenguinPlugin;
import me.procedures.penguin.inventories.StateInventories;
import me.procedures.penguin.player.PlayerProfile;
import me.procedures.penguin.utils.GameUtil;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@Getter
public abstract class AbstractSoloMatch extends AbstractMatch {

    private Player playerOne;
    private Player playerTwo;

    private boolean ranked;

    public AbstractSoloMatch(PenguinPlugin plugin, Player playerOne, Player playerTwo) {
        super(plugin);

        this.playerOne = playerOne;
        this.playerTwo = playerTwo;

        this.playerOne.teleport(this.getArena().getSpawnOne());
        this.playerTwo.teleport(this.getArena().getSpawnTwo());

        this.spawnPlayers(playerOne, playerTwo);

        if (ranked) {
            playerOne.sendMessage(PenguinPlugin.SERVER_COLOR_LIGHT + "Your Opponent: " + PenguinPlugin.SERVER_COLOR_BRIGHT + playerTwo.getName());
            playerTwo.sendMessage(PenguinPlugin.SERVER_COLOR_LIGHT + "Your Opponent: " + PenguinPlugin.SERVER_COLOR_BRIGHT + playerOne.getName());
        } else {
            playerOne.sendMessage(PenguinPlugin.SERVER_COLOR_LIGHT + "Your Opponent: " + PenguinPlugin.SERVER_COLOR_BRIGHT + playerTwo.getName());
            playerTwo.sendMessage(PenguinPlugin.SERVER_COLOR_LIGHT + "Your Opponent: " + PenguinPlugin.SERVER_COLOR_BRIGHT + playerOne.getName());
        }
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