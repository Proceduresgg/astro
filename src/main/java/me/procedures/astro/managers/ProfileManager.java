package me.procedures.astro.managers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.procedures.astro.AstroPlugin;
import me.procedures.astro.player.PlayerProfile;
import org.bukkit.entity.Player;

import java.util.*;

@Getter
@AllArgsConstructor
public class ProfileManager {

    private final AstroPlugin plugin;

    private final Map<UUID, PlayerProfile> profiles = new HashMap<>();

    public PlayerProfile getProfile(UUID uuid) {
        return this.profiles.computeIfAbsent(uuid, k -> new PlayerProfile(uuid));
    }

    public PlayerProfile getProfile(Player player) {
        return this.getProfile(player.getUniqueId());
    }

    /*public void saveProfiles() {
        MongoDatabase mongoDatabase = this.plugin.getMongo().getMongoClient().getDatabase("zzzzz");
        MongoCollection<Document> collection = mongoDatabase.getCollection("profiles");

        for (PlayerProfile profile : this.profiles.values()) {
            UUID identifier = profile.getUuid();

            Document document = collection.find(new Document("uuid", identifier)).first();//new Document("uuid", profile.getUuid().toString());

            if (document == null) {
                document = new Document("uuid", identifier);
            }

            for (Map.Entry<Ladder, PlayerRating> entry : profile.getRatings().entrySet()) {
                document.append(entry.getKey().getName(), entry.getValue().getRating());
            }

            collection.insertOne(document);
        }
    }*/

    public void loadProfile(UUID identifier) {

    }
}
