package me.procedures.penguin.ladder.impl;

import lombok.Getter;
import lombok.Setter;
import me.procedures.penguin.ladder.api.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Ladder implements ILadder {

    private final Map<LadderFlag, Boolean> ladderFlags = new HashMap<>();

    private String name;

    public Ladder(String name) {
        this.name = name;

        for (LadderFlag flag : LadderFlag.values()) {
            this.ladderFlags.put(flag, flag.isDefaultFlag());
        }
    }
}
