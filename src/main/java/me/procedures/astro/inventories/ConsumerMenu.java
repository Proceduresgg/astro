package me.procedures.astro.inventories;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.function.Consumer;

/**
 * Menu API
 */

public class ConsumerMenu implements InventoryHolder {

    @Getter @Setter
    private Consumer<InventoryClickEvent> consumer;

    private Inventory inventory;

    public ConsumerMenu(String title, int size) {
        this.inventory = Bukkit.createInventory(this, size, ChatColor.translateAlternateColorCodes('&', title));
    }

    public ConsumerMenu(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
