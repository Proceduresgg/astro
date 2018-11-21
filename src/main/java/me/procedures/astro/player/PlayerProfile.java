package me.procedures.astro.player;

import lombok.Getter;
import lombok.Setter;
import me.procedures.astro.kit.KitContainer;
import me.procedures.astro.ladder.impl.Ladder;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class PlayerProfile {

    private final UUID uuid;

    private final Map<Ladder, KitContainer> kits = new HashMap<>();
    private final Map<Ladder, PlayerRating> ratings = new HashMap<>();

    private PlayerState state = PlayerState.LOBBY;

    public PlayerProfile(UUID identifier) {
        this.uuid = identifier;
    }

    public void load(Document document) throws Exception {

    }
}
