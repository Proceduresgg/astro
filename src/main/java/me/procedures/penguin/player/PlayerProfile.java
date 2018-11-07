package me.procedures.penguin.player;

import com.mongodb.client.MongoCollection;
import lombok.Getter;
import lombok.Setter;
import me.procedures.penguin.kit.KitContainer;
import me.procedures.penguin.ladder.impl.Ladder;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class PlayerProfile {

    private final UUID identifier;

    private final Map<Ladder, KitContainer> kits = new HashMap<>();
    private final Map<Ladder, PlayerRating> ratingMap = new HashMap<>();

    private PlayerState playerState = PlayerState.LOBBY;

    public PlayerProfile(UUID identifier) {
        this.identifier = identifier;
    }
}
