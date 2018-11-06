package me.procedures.penguin;

import lombok.Getter;
import me.procedures.penguin.listeners.PlayerListener;
import me.procedures.penguin.managers.LadderManager;
import me.procedures.penguin.managers.ProfileManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

@Getter
public class PenguinPlugin extends JavaPlugin {

    private LadderManager ladderManager;
    private ProfileManager profileManager;

    public void onEnable() {


        this.registerListeners();
    }

    private void registerListeners() {
        Arrays.asList(new PlayerListener(this)).forEach(listener -> this.getServer().getPluginManager().registerEvents(listener, this));
    }
}
