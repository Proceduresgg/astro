package me.procedures.penguin.ladder.impl;

import me.procedures.penguin.ladder.api.*;

import java.util.HashMap;
import java.util.Map;

public class Ladder implements ILadder {

    private String name;

    private Map<LadderFlag, Boolean> ladderFlags = new HashMap<>();

    public Ladder() {

        for (LadderFlag flag : LadderFlag.values()) {
            this.ladderFlags.put(flag, flag.getDefaultState());
        }
    }
}
