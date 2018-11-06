package me.procedures.penguin.player;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PlayerProfile {

    private UUID identifier;

    private PlayerState playerState = PlayerState.LOBBY;

    public PlayerProfile(UUID identifier) {
        this.identifier = identifier;
    }

    public void save() {

    }
}
