package me.procedures.astro.match.options;

import me.procedures.astro.match.Match;
import me.procedures.astro.match.MatchOption;
import me.procedures.astro.match.team.MatchTeam;
import me.procedures.astro.utils.CC;
import me.procedures.astro.utils.StringUtil;
import mkremins.fanciful.FancyMessage;

public class Unranked implements MatchOption {

    @Override
    public void startPreGame(Match match) {
        match.getMatchPlayers().forEach((player, matchPlayer) -> {
            player.sendMessage(CC.LIGHT + "Your Opponent: " + CC.BRIGHT + StringUtil.getPlayerNames(match.getPlayers(MatchTeam.getOpposite(matchPlayer.getTeam()))));
        });
    }

    @Override
    public FancyMessage getInventoryMessage(String winnerName) {
        return new FancyMessage("Winner: " + winnerName).color(CC.LIGHT); // TODO: Create inventory / match cache
    }
}
