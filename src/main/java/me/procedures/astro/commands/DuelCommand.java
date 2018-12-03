package me.procedures.astro.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import me.procedures.astro.AstroPlugin;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.entity.Player;
import net.md_5.bungee.api.ChatColor;


public class DuelCommand extends BaseCommand {

    @Dependency
    private AstroPlugin plugin;

    @Default
    public void onDefault(Player player) {
        player.sendMessage("LOL");
    }

    @Syntax("<target>")
    @CommandAlias("duel")
    @CommandCompletion("@players")
    public void onDefault(Player player, Player target) {
        player.spigot().sendMessage(new ComponentBuilder(player.getDisplayName())
                .color(ChatColor.GREEN)
                .append(" has sent you a duel request. ")
                .color(ChatColor.YELLOW)
                .append("[")
                .color(ChatColor.DARK_GRAY)
                .append("YES")
                .color(ChatColor.GREEN)
                .bold(true)
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/accept " + target.getName()))
                .append("]")
                .color(ChatColor.DARK_GRAY)
                .create());
    }
}
