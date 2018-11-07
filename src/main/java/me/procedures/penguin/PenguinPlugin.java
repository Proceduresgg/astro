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
        this.ladderManager = new LadderManager();
        this.profileManager = new ProfileManager();

        this.registerListeners();
    }

    public void onDisable() {
        this.ladderManager.saveLadders();
        this.profileManager.saveProfiles();
    }

    private void registerListeners() {
        Arrays.asList(new PlayerListener(this)).forEach(listener -> this.getServer().getPluginManager().registerEvents(listener, this));
    }
}
