package me.procedures.astro.match.options;

import me.procedures.astro.match.Match;
import me.procedures.astro.match.MatchOption;
import me.procedures.astro.match.team.MatchTeam;
import me.procedures.astro.utils.CC;
import me.procedures.astro.utils.StringUtil;
import mkremins.fanciful.FancyMessage;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Unranked implements MatchOption {

    @Override
    public void startPreGame(Match match) {
        match.getMatchPlayers().forEach(matchPlayer -> {
            Player player = matchPlayer.getPlayer();

            player.sendMessage(CC.LIGHT + "Your Opponent: " + CC.BRIGHT + StringUtil.getPlayerNames(match.getPlayers(matchPlayer.getTeam() == MatchTeam.RED ? MatchTeam.BLUE : MatchTeam.RED)));
        });
    }

    @Override
    public FancyMessage getInventoryMessage(String winnerName) {
        return new FancyMessage("Winner: " + winnerName).color(CC.LIGHT); // TODO: Create inventory / match cache
    }
}
