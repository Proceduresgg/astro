package me.procedures.astro.inventories;

import me.procedures.astro.AstroPlugin;
import me.procedures.astro.ladder.Ladder;
import me.procedures.astro.queue.QueueContainer;
import me.procedures.astro.utils.CC;
import me.procedures.astro.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class UnrankedMenu extends ConsumerMenu {

    public UnrankedMenu() {
        this.setConsumer(event -> {
            Player player = (Player) event.getWhoClicked();
            ItemStack item = event.getCurrentItem();

            if (item == null || item.getType() == Material.AIR || item.getItemMeta().getDisplayName() == null) {
                return;
            }

            AstroPlugin.getInstance().getLadderManager().getLadders().values()
                    .stream()
                    .filter(ladder -> ladder.getDisplayItem().getType() == event.getCurrentItem().getType())
                    .forEach(ladder -> {
                        player.closeInventory();
                        event.setCancelled(true);

                        AstroPlugin.getInstance().getQueueManager().getUnrankedQueue(ladder).addToQueue(player);
                    });
        });
    }

    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 27, ChatColor.YELLOW.toString() + ChatColor.BOLD + "Select a Ladder");

        AstroPlugin.getInstance().getQueueManager().getQueues().values()
                .stream()
                .map(QueueContainer::getUnrankedQueue)
                .forEach(queue -> {
                    Ladder ladder = queue.getLadder();

                    inventory.addItem(new ItemBuilder(ladder.getDisplayItem().getType(), ChatColor.AQUA + ladder.getName(), 1,
                            CC.RESET + "In Queue: " + ChatColor.AQUA + queue.getQueue().size(),
                            CC.RESET + "In Fight: " + ChatColor.AQUA + queue.getPlayingAmount()).getItem());
                });

        return inventory;
    }
}
