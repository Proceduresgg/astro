package me.procedures.penguin.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Getter
@AllArgsConstructor
public enum StateInventories {

    LOBBY(new ItemStack[]{
            new ItemBuilder(Material.BOOK, ChatColor.GOLD + ChatColor.BOLD.toString() + "Edit Kits", ChatColor.GRAY + "Right click to edit your kits.").getItem(),
            null,
            null,
            null,
            new ItemBuilder(Material.NAME_TAG, ChatColor.YELLOW + ChatColor.BOLD.toString() + ChatColor.BOLD.toString() + "Create Party",  ChatColor.GRAY + "Right Click to create a party.").getItem(),
            null,
            null,
            new ItemBuilder(Material.IRON_SWORD, ChatColor.GRAY + ChatColor.BOLD.toString() + "Join" + ChatColor.AQUA + ChatColor.BOLD.toString() + " Unranked " + ChatColor.GRAY + ChatColor.BOLD.toString() + "Queue", ChatColor.GRAY + "Right click to queue unranked.").getItem(),
            new ItemBuilder(Material.DIAMOND_SWORD, ChatColor.GRAY + ChatColor.BOLD.toString() + "Join" + ChatColor.AQUA + ChatColor.BOLD.toString() + " Ranked " + ChatColor.GRAY + ChatColor.BOLD.toString() + "Queue", ChatColor.GRAY + "Right click to queue ranked.").getItem(),}),

    SPECTATOR(new ItemStack[] {
            new ItemBuilder(Material.INK_SACK, ChatColor.RED + ChatColor.BOLD.toString() + "Stop Spectating", 1, (short)1, ChatColor.GRAY + "Right Click to stop spectating.").getItem(),
            new ItemBuilder(Material.PAPER, ChatColor.YELLOW + ChatColor.BOLD.toString() + "Spectator Information", ChatColor.GRAY + "Right Click to get", ChatColor.GRAY + "spectator information.").getItem(),
            null,
            null,
            null,
            null,
            null,
            null,
            null,


    }),;

    private ItemStack[] contents;
}
