package me.procedures.penguin.managers;

import lombok.Getter;
import me.procedures.penguin.managers.type.LadderManager;
import me.procedures.penguin.managers.type.ProfileManager;

@Getter
public class ManagerHandler {

    private ProfileManager profileManager = new ProfileManager();
    private LadderManager ladderManager = new LadderManager();
}
