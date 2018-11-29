package me.procedures.astro.managers;

import lombok.Getter;
import me.procedures.astro.AstroPlugin;
import me.procedures.astro.inventories.type.DuelMenu;
import me.procedures.astro.inventories.type.UnrankedMenu;
import org.bukkit.ChatColor;

@Getter
public class MenuManager {

    private final AstroPlugin plugin;

    private UnrankedMenu unrankedInventory;
    private DuelMenu duelMenuInventory;

    public MenuManager(AstroPlugin plugin) {
        this.plugin = plugin;
        this.unrankedInventory = new UnrankedMenu(this.plugin, ChatColor.RED + "Unranked Menu Queue", 27);
        this.duelMenuInventory = new DuelMenu(this.plugin, ChatColor.RED + "Choose ladder", 27);
    }
}
