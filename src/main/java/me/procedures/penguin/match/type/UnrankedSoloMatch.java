package me.procedures.penguin.match.type;

import me.procedures.penguin.PenguinPlugin;
import me.procedures.penguin.match.AbstractSoloMatch;
import mkremins.fanciful.FancyMessage;
import org.bukkit.entity.Player;

public class UnrankedSoloMatch extends AbstractSoloMatch {

    public UnrankedSoloMatch(PenguinPlugin plugin, Player playerOne, Player playerTwo) {
        super(plugin, playerOne, playerTwo);

        playerOne.sendMessage(PenguinPlugin.SERVER_COLOR_LIGHT + "Your Opponent: " + PenguinPlugin.SERVER_COLOR_BRIGHT + playerTwo.getName());
        playerTwo.sendMessage(PenguinPlugin.SERVER_COLOR_LIGHT + "Your Opponent: " + PenguinPlugin.SERVER_COLOR_BRIGHT + playerOne.getName());
    }

    @Override
    public void endMatch(Player winner, Player loser) {
        super.endMatch(winner, loser);

        FancyMessage postMatchInventories = new FancyMessage("Winner: " + winner.getName()).color(PenguinPlugin.SERVER_COLOR_LIGHT); // TODO: Create inventory / match cache
    }
}
