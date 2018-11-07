package me.procedures.penguin.utils;

import me.procedures.penguin.player.PlayerRating;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GameUtil {

    public static final int kFactor = 32;

    public static void resetPlayer(Player player) {
        player.setCanPickupItems(false);

        player.getInventory().clear();
        player.getInventory().setArmorContents(new ItemStack[4]);

        player.getActivePotionEffects().forEach(effect -> player.removePotionEffect(effect.getType()));

        player.setHealth(20);
        player.setFoodLevel(20);
        player.setFireTicks(1);
    }

    public static void updateRating(PlayerRating winner, PlayerRating loser) {
        double winnerRating = (double) winner.getRating();
        double loserRating = (double) loser.getRating();

        winnerRating = Math.pow(10, winnerRating / loserRating);
        loserRating = Math.pow(10, loserRating / winnerRating);

        double winnerExpected = winnerRating / (winnerRating + loserRating);
        double loserExpected = loserRating / (loserRating + winnerRating);

        winnerRating = winner.getRating() + 32 * (1 - winnerExpected);
        loserRating = loser.getRating() + 32 * (0 - loserExpected);

        winner.setRating((int) winnerRating);
        loser.setRating((int) loserRating);
    }
}
