package me.procedures.astro.utils;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessageUtil {

    public static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static String getDuelInventories(Player player) {
        TextComponent msg = new TextComponent("LOL");
        return null;
    }
}