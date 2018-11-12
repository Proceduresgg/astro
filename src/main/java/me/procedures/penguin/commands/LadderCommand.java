package me.procedures.penguin.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import me.procedures.penguin.PenguinPlugin;
import me.procedures.penguin.kit.KitInventory;
import me.procedures.penguin.ladder.impl.Ladder;
import me.procedures.penguin.utils.InventoryUtil;
import me.procedures.penguin.utils.StringUtil;
import org.bukkit.entity.Player;

@CommandAlias("ladder")
@CommandPermission("procedures.manager")
public class LadderCommand extends BaseCommand {

    @Dependency
    private PenguinPlugin plugin;

    @Default
    public void onDefault(Player player) {
        player.sendMessage(PenguinPlugin.serverColorBright + "Usage: /ladder [create, delete, setdefaultinventory, setorder, setname]");
    }

    @Subcommand("create")
    public void onCreate(Player player, String[] args) {
        if (args.length == 0) {
            player.sendMessage(PenguinPlugin.serverColorBright + "Usage: /ladder create [name]");
            return;

        } else if (this.plugin.getLadderManager().getLadders().containsKey(args[0].toLowerCase())) {
            player.sendMessage(PenguinPlugin.serverColorBright + "That ladder already exists.");
            return;
        }

        Ladder ladder = new Ladder(args[0]);

        this.plugin.getLadderManager().getLadders().put(args[0], ladder);

        ladder.save();

        player.sendMessage(PenguinPlugin.serverColorBright + "That ladder has been created.");
    }

    @Subcommand("delete")
    public void onDelete(Player player, String[] args) {
        if (args.length == 0) {
            player.sendMessage(PenguinPlugin.serverColorBright + "Usage: /ladder delete [name]");
            return;

        } else if (!this.plugin.getLadderManager().getLadders().containsKey(args[0].toLowerCase())) {
            player.sendMessage(PenguinPlugin.serverColorBright + "That ladder does not exist.");
            return;
        }

        this.plugin.getLadderManager().getLadders().remove(args[0]);

        player.sendMessage(PenguinPlugin.serverColorBright + "That ladder has been deleted.");
    }

    @Subcommand("setdefaultinventory")
    public void onSetDefaultInventory(Player player, String[] args) {
        if (args.length == 0) {
            player.sendMessage(PenguinPlugin.serverColorBright + "Usage: /ladder setdefaultinventory [name]");
            return;

        } else if (!this.plugin.getLadderManager().getLadders().containsKey(args[0].toLowerCase())) {
            player.sendMessage(PenguinPlugin.serverColorBright + "That ladder does not exist.");
            return;
        }

        KitInventory kitInventory = InventoryUtil.inventoryFromPlayer(player);

        Ladder ladder = this.plugin.getLadderManager().getLadders().get(args[0]);

        ladder.setDefaultInventory(kitInventory);
        ladder.save();

        player.sendMessage(PenguinPlugin.serverColorBright + "The default inventory for that ladder has been updated.");
    }

    @Subcommand("setorder")
    public void onSetOrder(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(PenguinPlugin.serverColorBright + "Usage: /ladder setorder [name] order");
            return;

        } else if (!this.plugin.getLadderManager().getLadders().containsKey(args[0].toLowerCase())) {
            player.sendMessage(PenguinPlugin.serverColorBright + "That ladder does not exist.");
            return;

        } else if (!StringUtil.isNumeric(args[1])) {
            player.sendMessage(PenguinPlugin.serverColorBright + "You need to specific what order the ladder must be.");
            return;

        } else if (Integer.parseInt(args[1]) > 54 || Integer.parseInt(args[1]) < 0) {
            player.sendMessage(PenguinPlugin.serverColorBright + "You need to specify an order within 0 - 54.");
            return;
        }

        int order = Integer.parseInt(args[1]);

        Ladder ladder = this.plugin.getLadderManager().getLadders().get(args[0]);

        ladder.setDisplayOrder(order);
        ladder.save();

        player.sendMessage(PenguinPlugin.serverColorBright + "The display order for that ladder has been set.");
    }
}
