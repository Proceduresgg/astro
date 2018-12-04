package me.procedures.astro.match;

import mkremins.fanciful.FancyMessage;
import org.bukkit.entity.Player;

/**
 * Made for flexibility. With this, you can do whatever you need
 * depending on the type of match (Ranked, UnrankedOption, Party Fight).
 */

public interface MatchOption {

    void startPreGame(Match match);

    FancyMessage getInventoryMessage(String winnerName);
}
