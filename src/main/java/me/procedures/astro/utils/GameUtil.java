package me.procedures.astro.utils;

import me.procedures.astro.AstroPlugin;
import me.procedures.astro.inventories.StateInventories;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GameUtil {

    public static String SCOREBOARD_TITLE = CC.PRIMARY + ChatColor.BOLD.toString()
            + AstroPlugin.getInstance().getConfiguration().getMessages().getConfig().getString("server-name")
            + ChatColor.GRAY + " \u2758 "
            + ChatColor.RESET + "Practice";

    public static void resetPlayer(Player player) {
        player.setCanPickupItems(false);

        player.getInventory().clear();
        player.getInventory().setArmorContents(new ItemStack[4]);

        player.setHealth(20);
        player.setFoodLevel(20);
        player.setFireTicks(1);

        player.getActivePotionEffects().forEach(effect -> player.removePotionEffect(effect.getType()));

        //Clears all the arrows from a players body (Note: only for v1_8_3 server)
        //((CraftPlayer) player).getHandle().getDataWatcher().watch(9, (byte) 0);
    }

    public static void teleportToSpawn(Player player) {
        Bukkit.getServer().getOnlinePlayers().forEach(onlinePlayer -> {
            onlinePlayer.hidePlayer(player);

            if (!player.hasPermission("astro.donor")) {
                player.hidePlayer(onlinePlayer);
            }
        });

        GameUtil.resetPlayer(player);

        player.getInventory().setContents(StateInventories.LOBBY.getContents());
        player.updateInventory();
        player.teleport(player.getWorld().getSpawnLocation());
    }
}
