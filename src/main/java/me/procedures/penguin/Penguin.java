package me.procedures.penguin;

import lombok.Getter;
import me.procedures.penguin.listeners.PlayerListener;
import me.procedures.penguin.managers.ManagerHandler;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

@Getter
public class Penguin extends JavaPlugin {
    private ManagerHandler managerHandler;

    public void onEnable() {
        this.managerHandler = new ManagerHandler();
        this.registerListeners();
    }

    private void registerListeners() {
        Arrays.asList(new PlayerListener(this.managerHandler)).forEach(listener -> this.getServer().getPluginManager().registerEvents(listener, this));
    }
}
