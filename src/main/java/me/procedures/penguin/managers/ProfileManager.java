package me.procedures.penguin.managers;

import lombok.Getter;
import me.procedures.penguin.player.PlayerProfile;
import org.bukkit.entity.Player;

import java.util.*;

@Getter
public class ProfileManager {

    private final Map<UUID, PlayerProfile> profiles = new HashMap<>();

    public PlayerProfile getProfile(UUID uuid) {
        return this.profiles.computeIfAbsent(uuid, k -> new PlayerProfile(uuid));
    }

    public PlayerProfile getProfile(Player player) {
        return this.getProfile(player.getUniqueId());
    }

    public void saveProfiles() {
        for (PlayerProfile profile : this.profiles.values()) {
            profile.save();
        }
    }
}
