package me.procedures.astro.listeners;

import lombok.AllArgsConstructor;
import me.procedures.astro.AstroPlugin;
import me.procedures.astro.config.PracticeConfiguration;
import me.procedures.astro.match.Match;
import me.procedures.astro.match.MatchStatus;
import me.procedures.astro.player.PlayerProfile;
import me.procedures.astro.player.PlayerState;
import me.procedures.astro.queue.AbstractQueue;
import me.procedures.astro.utils.CC;
import me.procedures.astro.utils.GameUtil;
import me.procedures.astro.utils.MessageUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

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

        GameUtil.teleportToSpawn(player);

        this.plugin.getConfiguration().getMessages().getConfig().getStringList("join-messages")
                .forEach(message -> player.sendMessage(MessageUtil.color(message)));

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

        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        ItemStack item = event.getItem();

        if (item == null || item.getType() == Material.AIR) {
            return;
        }

        switch (profile.getState()) {
            case LOBBY:
                if (item.getType() == Material.IRON_SWORD) {
                    player.openInventory(this.plugin.getMenuManager().getUnrankedMenu().getInventory());

                } else if (item.getType() == Material.DIAMOND_SWORD) {
                    player.openInventory(this.plugin.getMenuManager().getRankedMenu().getInventory());

                } else if (item.getType() == Material.INK_SACK) {
                    GameUtil.teleportToSpawn(player);

                } else if (item.getType() == Material.NAME_TAG) {
                    player.sendMessage(PracticeConfiguration.COMING_SOON_MESSAGE);

                } else if (item.getType() == Material.BOOK) {
                    player.sendMessage(PracticeConfiguration.COMING_SOON_MESSAGE);
                }
                break;

            case FIGHTING:
                Match match = profile.getMatch();

                if (match != null) {
                    if (item.getType() == Material.BOOK) {
                        match.getLadder().getDefaultInventory().apply(player);

                    } else if (item.getType() == Material.ENDER_PEARL) {
                        if (match.getStatus() == MatchStatus.STARTING) {
                            event.setCancelled(true);

                        } else if (!profile.canPearl()) {
                            System.out.println("LOL");
                            event.setCancelled(true);
                        }
                    }
                }
                break;

            case QUEUING:
                if (item.getType() == Material.INK_SACK) {
                    AbstractQueue queue = profile.getQueue();

                    queue.removeFromQueue(player);
                }
                break;

            default: break;
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

        if (!match.getPlayers().containsKey(damager)) {
            event.setCancelled(true);

        } else if (match.getStatus() == MatchStatus.STARTING) {
            event.setCancelled(true);

        } else if (match.getPlayers().get(player).getTeam() == match.getPlayers().get(damager).getTeam()) {
            event.setCancelled(true);

        } else if (match.getPlayers().get(player).isDead()) {
            event.setCancelled(true);

        } else if (player.getHealth() - event.getFinalDamage() <= 0.0) {
            player.setHealth(20);
            match.handleDeath(player, player.getLocation(), this.plugin.getConfiguration().getString("match.death")
                    .replace("{player}", player.getName())
                    .replace("{killer}", damager.getName()));
        }
    }

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        PlayerProfile profile = this.plugin.getProfileManager().getProfile(player);

        if (profile.getState() != PlayerState.FIGHTING) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        PlayerProfile profile = this.plugin.getProfileManager().getProfile(player);

        if (profile.getState() != PlayerState.FIGHTING && profile.getState() != PlayerState.KIT_EDITOR) {
             event.setCancelled(true);
        } else {
            new BukkitRunnable() {
                @Override
                public void run() {
                    event.getItemDrop().remove();
                }
            }.runTaskTimer(this.plugin, 0, 60L);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        PlayerProfile profile = this.plugin.getProfileManager().getProfile(player);

        profile.getMatch().handleDeath(player, player.getLocation(), CC.PRIMARY + player.getName() + CC.TERTIARY + " has died.");

        event.setDeathMessage(null);
        event.getDrops().forEach(item -> item.setType(Material.AIR));
    }
}
