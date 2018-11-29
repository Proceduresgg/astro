package me.procedures.astro.scoreboard;

import me.joeleoli.frame.FrameAdapter;
import me.procedures.astro.AstroPlugin;
import me.procedures.astro.player.PlayerProfile;
import me.procedures.astro.player.PlayerState;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterResolver implements FrameAdapter {

    private Map<PlayerState, FrameAdapter> adapters = new HashMap<>();

    public AdapterResolver() {
        this.adapters.put(PlayerState.LOBBY, new LobbyAdapter());
        this.adapters.put(PlayerState.FIGHTING, new MatchAdapter());
        this.adapters.put(PlayerState.SPECTATING, new LobbyAdapter());
        this.adapters.put(PlayerState.KIT_EDITOR, new MatchAdapter());
        this.adapters.put(PlayerState.QUEUING, new LobbyAdapter());
    }

    @Override
    public String getTitle(Player player) {
        PlayerProfile profile = AstroPlugin.getInstance().getProfileManager().getProfile(player);

        return this.adapters.get(profile.getState()).getTitle(player);
    }

    @Override
    public List<String> getLines(Player player) {
        PlayerProfile profile = AstroPlugin.getInstance().getProfileManager().getProfile(player);

        List<String> lines = this.adapters.get(profile.getState()).getLines(player);
        List<String> toReturn = new ArrayList<>();

        if (lines.size() != 0) {
            toReturn.add(ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "---------------------");
            toReturn.addAll(lines);
            toReturn.add("");
            toReturn.add(ChatColor.DARK_RED.toString() + "astro.gg");
            toReturn.add(ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "---------------------");
        }

        return toReturn;
    }
}
