package me.procedures.astro.listeners;

import lombok.RequiredArgsConstructor;
import me.procedures.astro.AstroPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

@RequiredArgsConstructor
public class EnviornmentListener implements Listener {

    private AstroPlugin plugin;

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        event.setCancelled(true);
    }
}
