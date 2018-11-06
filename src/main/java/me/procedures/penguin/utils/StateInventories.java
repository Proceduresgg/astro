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
            new ItemBuilder(Material.ANVIL, ChatColor.LIGHT_PURPLE  + ChatColor.BOLD.toString() + "Settings", ChatColor.GRAY + "Right Click to open", ChatColor.GRAY + "your settings.").getItem(),
            new ItemBuilder(Material.DIAMOND_SWORD, ChatColor.GRAY + ChatColor.BOLD.toString() + "Join" + ChatColor.AQUA + ChatColor.BOLD.toString() + " Ranked " + ChatColor.GRAY + ChatColor.BOLD.toString() + "Queue", ChatColor.GRAY + "Right click to queue ranked.").getItem(),});

    private ItemStack[] contents;
}
