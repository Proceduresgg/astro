package me.procedures.astro.inventories;

import lombok.Getter;
import me.procedures.astro.AstroPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Mini menu api I created which simply has listener for InventoryClickEvent,
 * and a method for opening and adding all the contents to the inventory.
 */

@Getter
public abstract class AbstractMenu implements Listener {

    private final AstroPlugin plugin;

    private String title;

    private int size;

    public AbstractMenu(AstroPlugin plugin, String title, int size) {
        this.plugin = plugin;
        this.title = title;
        this.size = size;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public abstract void open(Player player);

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!event.getInventory().getTitle().equals(this.title)) {
            event.setCancelled(false);
        }
    }
}
