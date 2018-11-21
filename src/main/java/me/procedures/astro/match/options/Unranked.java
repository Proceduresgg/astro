package me.procedures.astro.match.options;

import me.procedures.astro.AstroPlugin;
import me.procedures.astro.match.MatchOption;
import mkremins.fanciful.FancyMessage;
import org.bukkit.entity.Player;

public class Unranked implements MatchOption {

    @Override
    public void startPreGame(Player playerOne, Player playerTwo) {
        playerOne.sendMessage(AstroPlugin.SERVER_COLOR_LIGHT + "Your Opponent: " + AstroPlugin.SERVER_COLOR_BRIGHT + playerTwo.getName());
        playerTwo.sendMessage(AstroPlugin.SERVER_COLOR_LIGHT + "Your Opponent: " + AstroPlugin.SERVER_COLOR_BRIGHT + playerOne.getName());
    }

    @Override
    public FancyMessage getInventoryMessage(String winnerName) {
        return new FancyMessage("Winner: " + winnerName).color(AstroPlugin.SERVER_COLOR_LIGHT); // TODO: Create inventory / match cache
    }
}
