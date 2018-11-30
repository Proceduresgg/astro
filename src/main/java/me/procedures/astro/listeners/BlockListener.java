package me.procedures.astro.listeners;

import lombok.RequiredArgsConstructor;
import me.procedures.astro.AstroPlugin;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

@RequiredArgsConstructor
public class BlockListener implements Listener {

    private final AstroPlugin plugin;

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        if (!((player.hasPermission("astro.build") || player.isOp()) && player.getGameMode() == GameMode.CREATIVE)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if (!((player.hasPermission("astro.build") || player.isOp()) && player.getGameMode() == GameMode.CREATIVE)) {
            event.setCancelled(true);
        }
    }
}
