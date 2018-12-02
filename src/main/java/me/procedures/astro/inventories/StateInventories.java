package me.procedures.astro.inventories;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.procedures.astro.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Each enum represents a state and holds an array of items which
 * represent the inventory for that state.
 */

@Getter
@AllArgsConstructor
public enum StateInventories {

    LOBBY(new ItemStack[]{
            new ItemBuilder(Material.IRON_SWORD, ChatColor.WHITE + ChatColor.BOLD.toString() + "Join" + ChatColor.YELLOW + ChatColor.BOLD.toString() + " Unranked " + ChatColor.WHITE + ChatColor.BOLD.toString() + "Queue", ChatColor.GRAY + "Right click to queue unranked.").getItem(),
            new ItemBuilder(Material.DIAMOND_SWORD, ChatColor.WHITE + ChatColor.BOLD.toString() + "Join" + ChatColor.GREEN + ChatColor.BOLD.toString() + " Ranked " + ChatColor.WHITE + ChatColor.BOLD.toString() + "Queue", ChatColor.GRAY + "Right click to queue ranked.").getItem(),
            null,
            null,
            new ItemBuilder(Material.NAME_TAG, ChatColor.RED + ChatColor.BOLD.toString() + "Create Party",  ChatColor.GRAY + "Right Click to create a party.").getItem(),
            null,
            null,
            null,
            new ItemBuilder(Material.BOOK, ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + "Edit Kits", ChatColor.GRAY + "Right click to edit your kits.").getItem(),
    }),

    SPECTATOR(new ItemStack[] {
            new ItemBuilder(Material.INK_SACK, ChatColor.RED + ChatColor.BOLD.toString() + "Stop Spectating", 1, (short) 1, ChatColor.GRAY + "Right Click to stop spectating.").getItem(),
            new ItemBuilder(Material.PAPER, ChatColor.YELLOW + ChatColor.BOLD.toString() + "Spectator Information", ChatColor.GRAY + "Right Click to get", ChatColor.GRAY + "spectator information.").getItem(),
    }),

    QUEUE(new ItemStack[]{
            new ItemBuilder(Material.INK_SACK, ChatColor.RED + "Leave Queue", 1, (short)1, ChatColor.GRAY + "Right-click to leave your queue.").getItem()
    });

    private ItemStack[] contents;
}
