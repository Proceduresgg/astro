package me.procedures.astro.listeners;

import lombok.AllArgsConstructor;
import me.procedures.astro.AstroPlugin;
import me.procedures.astro.inventories.StateInventories;
import me.procedures.astro.kit.KitInventory;
import me.procedures.astro.match.AbstractMatch;
import me.procedures.astro.player.PlayerProfile;
import me.procedures.astro.player.PlayerState;
import me.procedures.astro.utils.GameUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class PlayerListener implements Listener {

    private final AstroPlugin plugin;

    @EventHandler
    public void onPreLogin(AsyncPlayerPreLoginEvent event) {
        PlayerProfile profile = this.plugin.getProfileManager().getProfile(event.getUniqueId());
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerProfile profile = this.plugin.getProfileManager().getProfile(player);

        GameUtil.resetPlayer(player);

        player.getInventory().setContents(StateInventories.LOBBY.getContents());
        player.updateInventory();

        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> onlinePlayer.hidePlayer(player));
        Bukkit.getOnlinePlayers().stream()
                .filter(onlinePlayer -> !onlinePlayer.hasPermission("gg.donor"))
                .forEach(player::hidePlayer);

        player.sendMessage(AstroPlugin.SERVER_COLOR_BRIGHT + "Fight other players by using /duel [player].");

        event.setJoinMessage(null);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerProfile profile = this.plugin.getProfileManager().getProfiles().remove(player.getUniqueId());

        event.setQuitMessage(null);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            PlayerProfile profile = this.plugin.getProfileManager().getProfile(player);

            if (profile.getState() != PlayerState.FIGHTING) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            PlayerProfile profile = this.plugin.getProfileManager().getProfile(player);

            if (profile.getState() != PlayerState.FIGHTING) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        PlayerProfile profile = this.plugin.getProfileManager().getProfile(player);

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack item = event.getItem();

            if (item == null || item.getType() == Material.AIR || item.getItemMeta().getDisplayName() == null) {
                return;
            }

            switch (ChatColor.stripColor(item.getItemMeta().getDisplayName().toUpperCase())) {
                case "RANKED QUEUE":
                    this.plugin.getMenuManager().getRankedInventory().open(player);
                    break;

                case "DEFAULT KIT":
                    AbstractMatch match = profile.getMatch();

                    if (match != null) {
                        match.getLadder().getDefaultInventory().apply(player);
                    }
                    break;

                default:

            }
        }
    }
}
