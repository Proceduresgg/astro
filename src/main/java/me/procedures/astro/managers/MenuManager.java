package me.procedures.astro.managers;

import lombok.Getter;
import me.procedures.astro.AstroPlugin;
import me.procedures.astro.ladder.impl.Ladder;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.ClickType;
import org.inventivetalent.menubuilder.inventory.InventoryMenuBuilder;

@Getter
public class MenuManager {

    private final AstroPlugin plugin;

    private InventoryMenuBuilder rankedMenu;

    public MenuManager(AstroPlugin plugin) {
        this.plugin = plugin;
        this.initiateRankedMenu();
    }

    private void initiateRankedMenu() {
        this.rankedMenu = new InventoryMenuBuilder().withSize(27).withTitle(ChatColor.RED + "Ranked Queue");

        for (Ladder ladder : this.plugin.getLadderManager().getLadders().values()) {
            this.rankedMenu.withItem(ladder.getDisplayOrder(), ladder.getDisplayItem(), (player, clickType, itemStack) -> {

                plugin.getQueueManager().getQueues().get(ladder).addToQueue(player);

                player.sendMessage(AstroPlugin.SERVER_COLOR_LIGHT + "You have been added to the queue.");
            }, ClickType.LEFT);
        }
    }
}
