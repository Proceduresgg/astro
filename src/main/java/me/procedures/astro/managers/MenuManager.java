package me.procedures.astro.managers;

import lombok.Getter;
import me.procedures.astro.AstroPlugin;
import me.procedures.astro.inventories.ConsumerMenu;
import me.procedures.astro.ladder.Ladder;
import me.procedures.astro.utils.CC;
import me.procedures.astro.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MenuManager {

    private final AstroPlugin plugin;

    private ConsumerMenu rankedMenu;

    public MenuManager(AstroPlugin plugin) {
        this.plugin = plugin;
        this.rankedMenu = new ConsumerMenu(this.getDuelInventory());
        this.rankedMenu.setConsumer(event -> {
            Player player = (Player) event.getWhoClicked();
            ItemStack item = event.getCurrentItem();

            if (item == null || item.getType() == Material.AIR || item.getItemMeta().getDisplayName() == null) {
                return;
            }

            this.getPlugin().getLadderManager().getLadders().values()
                    .stream()
                    .filter(ladder -> ladder.getDisplayItem().getType() == event.getCurrentItem().getType())
                    .forEach(ladder -> {
                        player.closeInventory();
                        event.setCancelled(true);

                        this.getPlugin().getQueueManager().getQueues().get(ladder).addToQueue(player);
                    });
        });
    }

    public Inventory getDuelInventory() {
        Inventory inventory = Bukkit.createInventory(null, 27, ChatColor.GOLD.toString() + ChatColor.BOLD + "Select a ladder..");
        List<ItemStack> contents = new ArrayList<>();

        this.getPlugin().getQueueManager().getQueues().values().forEach(queue -> {
            Ladder ladder = queue.getLadder();

            contents.add(new ItemBuilder(ladder.getDisplayItem().getType(), ChatColor.AQUA + ladder.getName(), 1,
                    CC.RESET + "In Queue: " + ChatColor.AQUA + queue.getQueue().size(),
                    CC.RESET + "In Fight: " + ChatColor.AQUA + queue.getPlayingAmount()).getItem());
        });

        contents.forEach(inventory::addItem);
        return inventory;
    }
}
