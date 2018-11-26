package me.procedures.astro.inventories.type;

import me.procedures.astro.AstroPlugin;
import me.procedures.astro.inventories.AbstractMenu;
import me.procedures.astro.ladder.Ladder;
import me.procedures.astro.match.Match;
import me.procedures.astro.match.options.Unranked;
import me.procedures.astro.player.PlayerProfile;
import me.procedures.astro.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class DuelMenu extends AbstractMenu {

    public DuelMenu(AstroPlugin plugin, String title, int size) {
        super(plugin, title, size);
    }

    @Override
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        /* When the player clicks an item, check whether
        the inventory's title matches this title, if so
        add the player to the ladder which display item
        matches the item clicked. */
        Player player = (Player) event.getWhoClicked();

        if (!event.getInventory().getTitle().equals(this.getTitle())) {
            return;
        }

        ItemStack item = event.getCurrentItem();

        if (item == null || item.getType() == Material.AIR || item.getItemMeta().getDisplayName() == null) {
            return;
        }

        this.getPlugin().getLadderManager().getLadders().values().forEach(ladder -> {
            if (ladder.getDisplayItem().getType() == event.getCurrentItem().getType()) {
                player.closeInventory();
                event.setCancelled(true);

                new Match(AstroPlugin.getInstance(), ladder, player, player, new Unranked());
            }
        });
    }

    @Override
    public void open(Player player) {
        /* Create the inventory with all the ladder display items. */
        PlayerProfile profile = this.getPlugin().getProfileManager().getProfile(player);
        List<ItemStack> contents = new ArrayList<>();

        this.getPlugin().getQueueManager().getQueues().values().forEach(queue -> {
            Ladder ladder = queue.getLadder();

            contents.add(new ItemBuilder(ladder.getDisplayItem().getType(), ladder.getName(), 1).getItem());
        });

        Inventory inventory = Bukkit.createInventory(null, this.getSize(), this.getTitle());
        contents.forEach(inventory::addItem);

        player.openInventory(inventory);
    }
}