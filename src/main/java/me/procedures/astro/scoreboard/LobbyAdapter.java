package me.procedures.astro.scoreboard;

import me.joeleoli.frame.FrameAdapter;
import me.procedures.astro.AstroPlugin;
import me.procedures.astro.utils.GameUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
        List<String> toReturn = new ArrayList<>();

        toReturn.add(ChatColor.RESET.toString() + "Online" + ChatColor.GRAY + ": " + ChatColor.DARK_RED +  Bukkit.getOnlinePlayers().size());
        toReturn.add(ChatColor.RESET.toString() + "In fights" + ChatColor.GRAY + ": " + ChatColor.DARK_RED + AstroPlugin.getInstance().getQueueManager().getTotalInFight());
        toReturn.add(ChatColor.RESET.toString() + "In queues" + ChatColor.GRAY + ": " + ChatColor.DARK_RED + AstroPlugin.getInstance().getQueueManager().getTotalInQueue());

        return toReturn;
    }
}
