package me.procedures.penguin.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GameUtils {

    public static void resetPlayer(Player player) {
        player.setCanPickupItems(false);

        player.getInventory().clear();
        player.getInventory().setArmorContents(new ItemStack[4]);

        player.getActivePotionEffects().forEach(effect -> player.removePotionEffect(effect.getType()));

        player.setHealth(20);
        player.setFoodLevel(20);
        player.setFireTicks(1);
    }
}
