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

@Getter
public abstract class ConsumerMenu implements InventoryHolder {

    @Setter private Consumer<InventoryClickEvent> consumer;

    public abstract Inventory getInventory();
}
