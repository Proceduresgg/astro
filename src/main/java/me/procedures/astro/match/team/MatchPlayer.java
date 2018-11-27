package me.procedures.astro.match.team;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

@Getter @Setter
@AllArgsConstructor
public class MatchPlayer {

    private Player player;

    private MatchTeam team;

    private boolean dead = false;
}
