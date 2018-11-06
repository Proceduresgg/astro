package me.procedures.penguin.managers.type;

import lombok.Getter;
import me.procedures.penguin.ladder.impl.Ladder;

import java.util.ArrayList;
import java.util.List;

@Getter
public class LadderManager {

    private List<Ladder> ladders = new ArrayList<>();
}
