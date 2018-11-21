package me.procedures.astro.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import me.procedures.astro.AstroPlugin;
import me.procedures.astro.kit.KitInventory;
import me.procedures.astro.ladder.impl.Ladder;
import me.procedures.astro.utils.InventoryUtil;
import org.bukkit.entity.Player;

@CommandAlias("ladder")
@CommandPermission("procedures.manager")
public class LadderCommand extends BaseCommand {

    @Dependency
    private AstroPlugin plugin;

    @Default
    public void onDefault(Player player) {
        player.sendMessage(AstroPlugin.SERVER_COLOR_BRIGHT + "Usage: /ladder [create, delete, setdefaultinventory, setorder, setname]");
    }

    @Subcommand("create")
    public void onCreate(Player player, String name) {
        if (this.plugin.getLadderManager().getLadders().containsKey(name.toLowerCase())) {
            player.sendMessage(AstroPlugin.SERVER_COLOR_BRIGHT + "That ladder already exists.");
            return;
        }

        Ladder ladder = new Ladder(name);
        ladder.save();

        player.sendMessage(AstroPlugin.SERVER_COLOR_BRIGHT + "That ladder has been created.");

        this.plugin.getLadderManager().getLadders().put(name, ladder);
    }

    @Subcommand("delete")
    public void onDelete(Player player, Ladder ladder) {
        this.plugin.getLadderManager().getLadders().remove(ladder.getName());
        player.sendMessage(AstroPlugin.SERVER_COLOR_BRIGHT + "That ladder has been deleted.");
    }

    @Subcommand("setdefaultinventory")
    public void onSetDefaultInventory(Player player, Ladder ladder) {
        KitInventory kitInventory = InventoryUtil.inventoryFromPlayer(player);

        ladder.setDefaultInventory(kitInventory);
        ladder.save();

        player.sendMessage(AstroPlugin.SERVER_COLOR_BRIGHT + "The default inventory for that ladder has been updated.");
    }

    @Subcommand("setorder")
    public void onSetOrder(Player player, Ladder ladder, int order) {
        if (order > 54 || order < 0) {
            player.sendMessage(AstroPlugin.SERVER_COLOR_BRIGHT + "You need to specify an order within 0 - 54.");
            return;
        }

        ladder.setDisplayOrder(order);
        ladder.save();

        player.sendMessage(AstroPlugin.SERVER_COLOR_BRIGHT + "The display order for that ladder has been set.");
    }
}
