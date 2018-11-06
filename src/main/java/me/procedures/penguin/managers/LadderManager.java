package me.procedures.penguin.managers;

import lombok.Getter;
import me.procedures.penguin.ladder.impl.Ladder;

import java.util.ArrayList;
import java.util.List;

@Getter
public class LadderManager {

    private final List<Ladder> ladders = new ArrayList<>();
}
