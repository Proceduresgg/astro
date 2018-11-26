package me.procedures.astro.match.team;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

@Getter
@Setter
public class MatchPlayer {

    private Player player;

    private MatchTeam team;

    private boolean dead = false;

    public MatchPlayer(Player player, MatchTeam team) {
        this.player = player;
        this.team = team;
    }
}
