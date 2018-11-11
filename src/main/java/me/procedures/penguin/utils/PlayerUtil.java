package me.procedures.penguin.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerUtil {

    public void hideAllPlayers(Player player) {
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            if (p == player){
                continue;
            }

            player.hidePlayer(p);
        }
    }
}
