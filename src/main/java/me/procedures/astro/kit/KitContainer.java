package me.procedures.astro.kit;

import lombok.Getter;
import lombok.Setter;

/**
 * Holds the five kits a player gets per ladder.
 */

@Getter
@Setter
public class KitContainer {

    private KitInventory kitOne;
    private KitInventory kitTwo;
    private KitInventory kitThree;
    private KitInventory kitFour;
    private KitInventory kitFive;
}
