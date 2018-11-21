package me.procedures.astro.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GameUtil {

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
}
