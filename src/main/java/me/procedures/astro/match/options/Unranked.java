package me.procedures.astro.match.options;

import me.procedures.astro.AstroPlugin;
import me.procedures.astro.match.Match;
import me.procedures.astro.match.MatchOption;
import me.procedures.astro.match.team.MatchTeam;
import me.procedures.astro.utils.CC;
import me.procedures.astro.utils.MessageUtil;
import me.procedures.astro.utils.StringUtil;
import mkremins.fanciful.FancyMessage;
import org.bukkit.ChatColor;

public class Unranked implements MatchOption {

    @Override
    public void startPreGame(Match match) {
        match.getPlayers().forEach((player, matchPlayer) -> {
            player.sendMessage(MessageUtil.color(AstroPlugin.getInstance().getConfiguration().getMessages().getConfig().getString("match.opponents")
                    .replace("{opponent}", StringUtil.getPlayerNames(match.getTeam(MatchTeam.getOpposite(matchPlayer.getTeam()))))));
        });
    }

    @Override
    public FancyMessage getInventoryMessage(String winnerName) {
        return new FancyMessage("Winner: " + winnerName).color(CC.SECONDARY); // TODO: Create inventory / match cache
    }
}
