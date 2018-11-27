package me.procedures.astro.listeners;

import lombok.AllArgsConstructor;
import me.procedures.astro.AstroPlugin;
import me.procedures.astro.match.Match;
import me.procedures.astro.player.PlayerProfile;
import me.procedures.astro.player.PlayerState;
import me.procedures.astro.utils.CC;
import me.procedures.astro.utils.GameUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
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
        /* Creating and loading in the player profile */
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

        switch (profile.getState()) {
            case FIGHTING:
                profile.getMatch().handleDeath(player, player.getLocation(), player.getDisplayName() + " has left the match.");
                break;

            case QUEUING:
                profile.getQueue().removeFromQueue(player);
                break;

            default: break;
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        /* Any damage done to a player who isn't fighting is cancelled. */
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

        if (profile.getState() != PlayerState.LOBBY) {
            return;

        } else if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
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

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player) || !(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();
        PlayerProfile playerProfile = this.plugin.getProfileManager().getProfile(player);

        Player damager = (Player) event.getDamager();
        PlayerProfile damagerProfile = this.plugin.getProfileManager().getProfile(player);

        if (playerProfile.getState() != PlayerState.FIGHTING || damagerProfile.getState() != PlayerState.FIGHTING) {
            event.setCancelled(true);
        }

        Match match = playerProfile.getMatch();

        if (!match.getMatchPlayers().containsKey(damager)) {
            event.setCancelled(true);

        } else if (match.getMatchPlayers().get(player).getTeam() == match.getMatchPlayers().get(damager).getTeam()) {
            event.setCancelled(true);

        } else if (match.getMatchPlayers().get(player).isDead()) {
            event.setCancelled(true);

        } else if (player.getHealth() - event.getFinalDamage() <= 0.0) {
            match.handleDeath(player, player.getLocation(), CC.BRIGHT + player.getName() + CC.DIM + " has been slain by " + CC.BRIGHT + damager.getName() + CC.DIM + ".");
        }
    }
}
