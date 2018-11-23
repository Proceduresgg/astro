package me.procedures.astro.inventories;

import lombok.Getter;
import me.procedures.astro.AstroPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class AbstractInventory implements Listener {

    private final AstroPlugin plugin;

    private List<ItemStack> contents = new ArrayList<>();

    private String title;

    private int size;

    public AbstractInventory(AstroPlugin plugin, String title, int size) {
        this.plugin = plugin;
        this.title = title;
        this.size = size;

        AstroPlugin.getInstance().getServer().getPluginManager().registerEvents(this, AstroPlugin.getInstance());
    }

    public abstract void open(Player player);

    public Inventory toBukkitInventory() {
        Inventory inventory = Bukkit.createInventory(null, this.size, this.title);
        this.contents.forEach(inventory::addItem);

        return inventory;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
    }


}
