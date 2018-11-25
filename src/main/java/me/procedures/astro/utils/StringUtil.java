package me.procedures.astro.utils;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class StringUtil {

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    public static String getPlayerNames(List<Player> players) {
        if (players.size() == 0) {
            return "";
        }

        StringBuilder playerNames = new StringBuilder(players.get(0).getDisplayName());
        players.forEach(player -> playerNames.append(", ").append(player.getName()));

        return playerNames.toString();
    }
}
