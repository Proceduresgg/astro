package me.procedures.astro.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import me.procedures.astro.AstroPlugin;
import me.procedures.astro.arena.Arena;
import me.procedures.astro.location.AstroLocation;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@CommandAlias("arena")
@CommandPermission("astro.admin")
public class ArenaCommand extends BaseCommand {

    @Dependency private AstroPlugin plugin;

    @Syntax("<name>")
    @Subcommand("create")
    public void onCreate(Player player, String name) {
        if (this.plugin.getArenaManager().getArenas().containsKey(name)) {
            player.sendMessage(ChatColor.AQUA + "That arena already exists.");
            return;
        }

        Arena arena = new Arena(name);
        arena.save();

        player.sendMessage(ChatColor.AQUA + "Arena created.");

        this.plugin.getArenaManager().getArenas().put(name, arena);
    }

    @Syntax("<arena> <1 / 2>")
    @Subcommand("setspawn")
    public void onSetSpawn(Player player, Arena arena, int spawn) {
        if (spawn == 1) {
            arena.setSpawnOne(new AstroLocation(player.getLocation()));

        } else if (spawn == 2) {
            arena.setSpawnTwo(new AstroLocation(player.getLocation()));

        } else {
            player.sendMessage(ChatColor.AQUA + "You must either specify 1 or 2 as the third parameter.");
            return;
        }

        player.sendMessage(ChatColor.AQUA + "Spawn Point updated.");
        arena.save();
    }
}
