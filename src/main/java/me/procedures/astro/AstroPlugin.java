package me.procedures.astro;

import co.aikar.commands.InvalidCommandArgument;
import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import me.procedures.astro.commands.DuelCommand;
import me.procedures.astro.commands.LadderCommand;
import me.procedures.astro.data.Mongo;
import me.procedures.astro.ladder.Ladder;
import me.procedures.astro.listeners.ChatListener;
import me.procedures.astro.listeners.PlayerListener;
import me.procedures.astro.managers.LadderManager;
import me.procedures.astro.managers.MenuManager;
import me.procedures.astro.managers.ProfileManager;
import me.procedures.astro.managers.QueueManager;
import me.procedures.astro.utils.PlayerUtil;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

@Getter
public class AstroPlugin extends JavaPlugin {

    @Getter private static AstroPlugin instance;

    public static final ChatColor SERVER_COLOR_BRIGHT = ChatColor.AQUA;
    public static final ChatColor SERVER_COLOR_LIGHT = ChatColor.YELLOW;
    public static final ChatColor SERVER_COLOR_DIM = ChatColor.GRAY;

    private Mongo mongo;

    private LadderManager ladderManager;
    private ProfileManager profileManager;
    private QueueManager queueManager;
    private MenuManager menuManager;

    private PlayerUtil playerUtil;

    public void onEnable() {
        instance = this;

        this.mongo = new Mongo();

        this.ladderManager = new LadderManager(this);
        this.profileManager = new ProfileManager(this);
        this.queueManager = new QueueManager(this);
        this.menuManager = new MenuManager(this);

        this.playerUtil = new PlayerUtil();

        this.registerListeners();
        this.registerCommands(new PaperCommandManager(this));
    }

    public void onDisable() {
        this.ladderManager.saveLadders();
        //this.profileManager.saveProfiles();
    }

    private void registerListeners() {
        Arrays.asList(new PlayerListener(this), new ChatListener(this)).forEach(listener -> this.getServer().getPluginManager().registerEvents(listener, this));
    }

    private void registerCommands(PaperCommandManager commandManager) {
        this.registerContexts(commandManager);
        this.registerDependencies(commandManager);

        Arrays.asList(new LadderCommand(), new DuelCommand()).forEach(commandManager::registerCommand);
    }

    private void registerContexts(PaperCommandManager commandManager) {
        commandManager.getCommandContexts().registerContext(Ladder.class, c -> {
            String arg = c.popFirstArg();

            if (!this.ladderManager.getLadders().containsKey(arg.toLowerCase())) {
                c.getSender().sendMessage(SERVER_COLOR_BRIGHT + "The specified rank does not exist.");
                throw new InvalidCommandArgument(true);
            }

            return this.ladderManager.getLadders().get(arg);
        });
    }

    private void registerDependencies(PaperCommandManager commandManager) {
        commandManager.registerDependency(LadderManager.class, this.ladderManager);
        commandManager.registerDependency(Mongo.class, this.mongo);
    }
}
