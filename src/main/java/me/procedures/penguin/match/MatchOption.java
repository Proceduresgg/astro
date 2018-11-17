package me.procedures.penguin.match;

import mkremins.fanciful.FancyMessage;
import org.bukkit.entity.Player;

public interface MatchOption {

    void startPreGame(Player playerOne, Player playerTwo);

    FancyMessage getInventoryMessage(String winnerName);
}
