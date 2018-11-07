package me.procedures.penguin.managers;

import lombok.Getter;
import me.procedures.penguin.ladder.impl.Ladder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class LadderManager {

    private final Map<String, Ladder> ladders = new HashMap<>();

    public LadderManager() {
        this.loadLadders();
    }

    public void saveLadders() {

    }

    public void loadLadders() {

    }
}
