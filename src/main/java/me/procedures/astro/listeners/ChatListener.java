package me.procedures.astro.listeners;

import lombok.AllArgsConstructor;
import me.procedures.astro.AstroPlugin;
import me.procedures.astro.utils.MessageUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import us.rengo.milk.MilkPlugin;
import us.rengo.milk.player.PlayerProfile;

@AllArgsConstructor
public class ChatListener implements Listener {

    private final AstroPlugin plugin;

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        event.setFormat(MessageUtil.color(ChatColor.GREEN.toString() + ChatColor.GRAY + ": " + ChatColor.WHITE + "%2$s"));
    }
}
