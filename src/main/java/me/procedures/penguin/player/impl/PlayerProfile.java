package me.procedures.penguin.player.impl;

import lombok.Getter;
import lombok.Setter;
import me.procedures.penguin.player.api.PlayerState;

import java.util.UUID;

@Getter
@Setter
public class PlayerProfile {
    private UUID identifier;

    private PlayerState playerState = PlayerState.LOBBY;

    public PlayerProfile(UUID identifier) {
        this.identifier = identifier;
    }
}