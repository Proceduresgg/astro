package me.procedures.astro.scoreboard;

import me.joeleoli.frame.FrameAdapter;
import me.procedures.astro.AstroPlugin;
import me.procedures.astro.match.Match;
import me.procedures.astro.match.MatchStatus;
import me.procedures.astro.match.team.MatchTeam;
import me.procedures.astro.player.PlayerProfile;
import me.procedures.astro.utils.CC;
import me.procedures.astro.utils.GameUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MatchAdapter implements FrameAdapter {

    @Override
    public String getTitle(Player player) {
        return GameUtil.SCOREBOARD_TITLE;
    }

    @Override
    public List<String> getLines(Player player) {
        PlayerProfile profile = AstroPlugin.getInstance().getProfileManager().getProfile(player);
        Match match = profile.getMatch();

        List<String> lines = new ArrayList<>();

        if (match.getStatus() == MatchStatus.STARTING) {
            AstroPlugin.getInstance().getConfiguration().getScoreboard().getConfig().getStringList("boards.match").forEach(line -> {
                String copy = line.replace("{opponent-1}", player.getName())
                        .replace("{opponent-2}", match.getTeam(MatchTeam.getOpposite(match.getPlayers().get(player).getTeam())).get(0).getName());
                lines.add(copy);
            });

            return lines;
        } else {
            return Collections.emptyList();
        }
    }
}
