package me.procedures.astro.match.team;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Player;

@Getter @Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class MatchPlayer {

    private Player player;

    private MatchTeam team;

    private boolean dead = false;
}
