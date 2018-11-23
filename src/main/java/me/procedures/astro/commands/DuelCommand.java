package me.procedures.astro.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import me.procedures.astro.AstroPlugin;
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
        if (player == target) player.sendMessage("cant duel yo self lil nigga LOOL");

        player.openInventory(this.plugin.getMenuManager().getDuelInventory().toBukkitInventory());
    }
}
