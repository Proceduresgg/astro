package me.procedures.astro.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerUtil {

    public static void hideAllPlayers(Player player) {
        Bukkit.getServer().getOnlinePlayers()
                .stream()
                .filter(p -> p != player)
                .forEach(player::hidePlayer);
    }
}
