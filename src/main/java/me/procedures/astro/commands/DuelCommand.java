package me.procedures.astro.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import me.procedures.astro.AstroPlugin;
import me.procedures.astro.utils.CC;
import org.bukkit.entity.Player;

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
        if (player == target) {
            player.sendMessage(CC.PRIMARY + "You cannot duel yourself.");
            return;
        }

        this.plugin.getMenuManager().getDuelMenuInventory().open(player);
    }
}
