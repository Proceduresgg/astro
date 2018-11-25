package me.procedures.astro.match.options;

import me.procedures.astro.match.Match;
import me.procedures.astro.match.MatchOption;
import me.procedures.astro.utils.CC;
import me.procedures.astro.utils.StringUtil;
import mkremins.fanciful.FancyMessage;

import java.util.ArrayList;

public class Unranked implements MatchOption {

    @Override
    public void startPreGame(Match match) {
        match.getRedTeam().keySet().forEach(player -> {
            player.sendMessage(CC.LIGHT + "Your Opponent: " + CC.BRIGHT + StringUtil.getPlayerNames(new ArrayList<>(match.getBlueTeam().keySet())));
        });

        match.getBlueTeam().keySet().forEach(player -> {
            player.sendMessage(CC.LIGHT + "Your Opponent: " + CC.BRIGHT + StringUtil.getPlayerNames(new ArrayList<>(match.getRedTeam().keySet())));
        });
    }

    @Override
    public FancyMessage getInventoryMessage(String winnerName) {
        return new FancyMessage("Winner: " + winnerName).color(CC.LIGHT); // TODO: Create inventory / match cache
    }
}
