package me.procedures.penguin.managers.type;

import me.procedures.penguin.player.impl.PlayerProfile;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProfileManager {

    private Map<UUID, PlayerProfile> profiles = new HashMap<>();

    public PlayerProfile getProfile(UUID uuid) {
        return this.profiles.computeIfAbsent(uuid, k -> new PlayerProfile(uuid));
    }

    public PlayerProfile getProfile(Player player) {
        return this.getProfile(player.getUniqueId());
    }
}
