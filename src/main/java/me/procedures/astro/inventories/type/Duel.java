package me.procedures.astro.inventories.type;

import me.procedures.astro.AstroPlugin;
import me.procedures.astro.inventories.AbstractInventory;
import me.procedures.astro.ladder.Ladder;
import me.procedures.astro.match.MatchOption;
import me.procedures.astro.match.options.Unranked;
import me.procedures.astro.match.type.SoloMatch;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.HashSet;
import java.util.Set;

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
        if (event.getInventory().getTitle().contains("Choose ladder")) {
            for (Ladder ladder : AstroPlugin.getInstance().getLadderManager().getLadders().values()) {
                if (ladder.getDisplayItem().getType() == event.getCurrentItem().getType()) {
                    System.out.println("LOODLOLD");
                    Set<MatchOption> options = new HashSet<>();
                    options.add(new Unranked());
                    new SoloMatch(AstroPlugin.getInstance(), ladder, (Player) event.getWhoClicked(), (Player) event.getWhoClicked(), options);
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }
}
