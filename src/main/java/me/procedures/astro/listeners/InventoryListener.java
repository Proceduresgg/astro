package me.procedures.astro.listeners;

import lombok.AllArgsConstructor;
import me.procedures.astro.AstroPlugin;
import me.procedures.astro.inventories.ConsumerMenu;
import me.procedures.astro.player.PlayerProfile;
import me.procedures.astro.player.PlayerState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

@AllArgsConstructor
public class InventoryListener implements Listener {

    private final AstroPlugin plugin;

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        PlayerProfile profile = this.plugin.getProfileManager().getProfile(player);

        if (event.getInventory().getHolder() instanceof ConsumerMenu) {
            ConsumerMenu menu = (ConsumerMenu) event.getInventory().getHolder();

            if (menu.getConsumer() != null) {
                menu.getConsumer().accept(event);
            }

        } else if (profile.getState() != PlayerState.FIGHTING) {
            event.setCancelled(true);
        }
    }
}

