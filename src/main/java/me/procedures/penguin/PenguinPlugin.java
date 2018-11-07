package me.procedures.penguin;

import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import me.procedures.penguin.commands.LadderCommand;
import me.procedures.penguin.listeners.PlayerListener;
import me.procedures.penguin.managers.LadderManager;
import me.procedures.penguin.managers.ProfileManager;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

@Getter
public class PenguinPlugin extends JavaPlugin {

    public final static ChatColor serverColorBright = ChatColor.AQUA;
    public final static ChatColor serverColorDim = ChatColor.GRAY;

    private LadderManager ladderManager;
    private ProfileManager profileManager;

    private PaperCommandManager commandManager;

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

    private void registerCommands() {
        this.commandManager = new PaperCommandManager(this);

        this.commandManager.registerDependency(PenguinPlugin.class, this);

        Arrays.asList(new LadderCommand()).forEach(command -> this.commandManager.registerCommand(command));
    }
}
