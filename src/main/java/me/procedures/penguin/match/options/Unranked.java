package me.procedures.penguin.match.options;

import me.procedures.penguin.PenguinPlugin;
import me.procedures.penguin.match.MatchOption;
import mkremins.fanciful.FancyMessage;
import org.bukkit.entity.Player;

public class Unranked implements MatchOption {

    @Override
    public void startPreGame(Player playerOne, Player playerTwo) {
        playerOne.sendMessage(PenguinPlugin.SERVER_COLOR_LIGHT + "Your Opponent: " + PenguinPlugin.SERVER_COLOR_BRIGHT + playerTwo.getName());
        playerTwo.sendMessage(PenguinPlugin.SERVER_COLOR_LIGHT + "Your Opponent: " + PenguinPlugin.SERVER_COLOR_BRIGHT + playerOne.getName());
    }

    @Override
    public FancyMessage getInventoryMessage(String winnerName) {
        return new FancyMessage("Winner: " + winnerName).color(PenguinPlugin.SERVER_COLOR_LIGHT); // TODO: Create inventory / match cache
    }
}
