package me.procedures.astro.match.team;

public enum MatchTeam {

    RED,
    BLUE;

    public static MatchTeam getOpposite(MatchTeam team) {
        return team == MatchTeam.RED ? MatchTeam.BLUE : MatchTeam.RED;
    }
}
