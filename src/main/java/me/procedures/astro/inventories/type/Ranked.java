package me.procedures.astro.inventories.type;

import me.procedures.astro.AstroPlugin;
import me.procedures.astro.inventories.AbstractInventory;
import me.procedures.astro.ladder.Ladder;
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

public class Ranked extends AbstractInventory {

    public Ranked(AstroPlugin plugin, String title, int size) {
        super(plugin, title, size);
    }

    @Override
    @EventHandler
    public void onClick(InventoryClickEvent event) {
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

                this.getPlugin().getQueueManager().getQueues().get(ladder).addToQueue(player);
            }
        });
    }

    @Override
    public void open(Player player) {
        PlayerProfile profile = this.getPlugin().getProfileManager().getProfile(player);
        List<ItemStack> contents = new ArrayList<>();

        this.getPlugin().getQueueManager().getQueues().values().forEach(queue -> {
            Ladder ladder = queue.getLadder();

            contents.add(new ItemBuilder(ladder.getDisplayItem().getType(), ladder.getName(), 1,
                    "In Queue: " + queue.getQueue().size(),
                    "In Fight: " + queue.getPlayingAmount()).getItem());
        });

        Inventory inventory = Bukkit.createInventory(null, this.getSize(), this.getTitle());
        contents.forEach(inventory::addItem);

        player.openInventory(inventory);
    }
}
