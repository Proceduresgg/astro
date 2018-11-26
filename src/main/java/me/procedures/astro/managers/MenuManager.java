package me.procedures.astro.managers;

import lombok.Getter;
import me.procedures.astro.AstroPlugin;
import me.procedures.astro.inventories.type.DuelMenu;
import me.procedures.astro.inventories.type.RankedMenu;
import org.bukkit.ChatColor;

@Getter
public class MenuManager {

    private final AstroPlugin plugin;

    private RankedMenu rankedInventory;
    private DuelMenu duelMenuInventory;

    public MenuManager(AstroPlugin plugin) {
        this.plugin = plugin;
        this.rankedInventory = new RankedMenu(this.plugin, ChatColor.RED + "Ranked Menu Queue", 27);
        this.duelMenuInventory = new DuelMenu(this.plugin, ChatColor.RED + "Choose ladder", 27);
    }
}
