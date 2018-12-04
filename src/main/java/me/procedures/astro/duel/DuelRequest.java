package me.procedures.astro.duel;

import me.procedures.astro.ladder.Ladder;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.entity.Player;

public class DuelRequest {

    public DuelRequest(Player from, Player to, Ladder ladder) {

        to.spigot().sendMessage(new ComponentBuilder(from.getDisplayName()).color(ChatColor.GREEN)
                .append(" has sent you a ").color(ChatColor.YELLOW)
                .append(ladder.getName()).color(ChatColor.GREEN)
                .append(" duel request. ").color(ChatColor.YELLOW)
                .append("[").color(ChatColor.DARK_GRAY)
                .append("YES").color(ChatColor.GREEN).bold(true).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/accept " + from.getName()))
                .append("]").color(ChatColor.DARK_GRAY)
                .create());
    }
}
