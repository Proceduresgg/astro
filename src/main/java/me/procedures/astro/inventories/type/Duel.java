package me.procedures.astro.inventories.type;

import me.procedures.astro.AstroPlugin;
import me.procedures.astro.inventories.AbstractInventory;
import me.procedures.astro.ladder.Ladder;
import me.procedures.astro.match.Match;
import me.procedures.astro.match.options.Unranked;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Collections;

public class Duel extends AbstractInventory {

    public Duel(AstroPlugin plugin, String title, int size) {
        super(plugin, title, size);

        for (Ladder ladder : plugin.getLadderManager().getLadders().values()) {
            this.getContents().add(ladder.getDisplayItem());
        }
    }

    @Override
    public void open(Player player) {

    }

    @Override
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        for (Ladder ladder : AstroPlugin.getInstance().getLadderManager().getLadders().values()) {
            if (ladder.getDisplayItem().getType() == event.getCurrentItem().getType()) {
                Player player = (Player) event.getWhoClicked();

                new Match(AstroPlugin.getInstance(), ladder, Collections.singletonList(player), Collections.singletonList(player), Collections.singletonList(new Unranked()));
                event.setCancelled(true);
                return;
            }
        }
    }
}
