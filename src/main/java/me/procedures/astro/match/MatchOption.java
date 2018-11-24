package me.procedures.astro.match;

import mkremins.fanciful.FancyMessage;
import org.bukkit.entity.Player;

public interface MatchOption {

    void startPreGame(Match match);

    FancyMessage getInventoryMessage(String winnerName);
}
