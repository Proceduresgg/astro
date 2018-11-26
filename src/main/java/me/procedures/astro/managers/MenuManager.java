package me.procedures.astro.managers;

import lombok.Getter;
import me.procedures.astro.AstroPlugin;
import me.procedures.astro.inventories.type.Duel;
import me.procedures.astro.inventories.type.Ranked;
import org.bukkit.ChatColor;

@Getter
public class MenuManager {

    private final AstroPlugin plugin;

    private Ranked rankedInventory;
    private Duel duelInventory; // i made a mini menu api

    public MenuManager(AstroPlugin plugin) {
        this.plugin = plugin;
        this.rankedInventory = new Ranked(this.plugin, ChatColor.RED + "Ranked Queue", 27);
        this.duelInventory = new Duel(this.plugin, ChatColor.RED + "Choose ladder", 27);
    }
}
