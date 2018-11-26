package me.procedures.astro.listeners;

import lombok.AllArgsConstructor;
import me.procedures.astro.AstroPlugin;
import me.procedures.astro.match.Match;
import me.procedures.astro.player.PlayerProfile;
import me.procedures.astro.player.PlayerState;
import me.procedures.astro.utils.CC;
import me.procedures.astro.utils.GameUtil;
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

        profile.setState(PlayerState.LOBBY);

        player.sendMessage(CC.BRIGHT + "Fight other players by using /duel [player].");

        GameUtil.teleportToSpawn(player);

        event.setJoinMessage(null);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerProfile profile = this.plugin.getProfileManager().getProfiles().remove(player.getUniqueId());

        event.setQuitMessage(null);

        if (profile.getState() == PlayerState.FIGHTING) {
            profile.getMatch().handleDeath(player, player.getLocation(), player.getDisplayName() + " has left the match.");

        } else if (profile.getState() == PlayerState.QUEUING) {
            profile.getQueue().removeFromQueue(player);
        }
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

        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        ItemStack item = event.getItem();

        if (item == null || item.getType() == Material.AIR) {
            event.setCancelled(false);

        } else if (item.getType() == Material.DIAMOND_SWORD) {
            this.plugin.getMenuManager().getRankedInventory().open(player);

        } else if (item.getType() == Material.INK_SACK) {
            GameUtil.teleportToSpawn(player);

        } else if (item.getType() == Material.BOOK) {
            Match match = profile.getMatch();

            if (match != null) {
                match.getLadder().getDefaultInventory().apply(player);
            }
        }
    }
}
