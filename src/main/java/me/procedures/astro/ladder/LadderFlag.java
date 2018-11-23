package me.procedures.astro.ladder;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LadderFlag {

    ALLOW_HUNGER(true),
    ALLOW_HEAL(true),
    ALLOW_KIT_EDITOR(true),
    SHOW_DEFAULT_KIT(true),
    WATER_KILL(false),
    NO_HIT_DELAY(false),
    BREAK_SNOW(false),
    SINGULAR_ARENA(false);

    private boolean defaultFlag;
}
