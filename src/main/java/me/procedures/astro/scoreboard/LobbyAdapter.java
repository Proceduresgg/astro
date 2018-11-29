package me.procedures.astro.scoreboard;

import me.joeleoli.frame.FrameAdapter;
import me.procedures.astro.AstroPlugin;
import me.procedures.astro.utils.GameUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class LobbyAdapter implements FrameAdapter {

    @Override
    public String getTitle(Player player) {
        return GameUtil.SCOREBOARD_TITLE;
    }

    @Override
    public List<String> getLines(Player player) {
        List<String> lines = new ArrayList<>();

        AstroPlugin.getInstance().getConfiguration().getScoreboard().getConfig().getStringList("boards.lobby").forEach(line -> {
            String copy = line.replace("{total-player-count}", String.valueOf(Bukkit.getOnlinePlayers().size()))
                    .replace("{total-queue-count}", String.valueOf(AstroPlugin.getInstance().getQueueManager().getTotalInQueue()))
                    .replace("{total-match-count}", String.valueOf(AstroPlugin.getInstance().getQueueManager().getTotalInFight()));

            lines.add(copy);
        });

        return lines;
    }
}
