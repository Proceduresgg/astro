package me.procedures.penguin.match.api;

import lombok.Getter;
import me.procedures.penguin.PenguinPlugin;
import org.bukkit.entity.Player;

@Getter
public abstract class SoloMatch extends AbstractMatch {

    private Player playerOne;
    private Player playerTwo;

    public SoloMatch(PenguinPlugin plugin, Player playerOne, Player playerTwo) {
        super(plugin);

        this.playerOne = playerOne;
        this.playerTwo = playerTwo;

        this.playerOne.teleport(this.getArena().getSpawnOne());
        this.playerTwo.teleport(this.getArena().getSpawnTwo());

        this.spawnPlayers(playerOne, playerTwo);
    }
}
