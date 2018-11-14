package me.procedures.penguin.player;

import lombok.Getter;
import lombok.Setter;
import me.procedures.penguin.kit.KitContainer;
import me.procedures.penguin.ladder.impl.Ladder;

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
}