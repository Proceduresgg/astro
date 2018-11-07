package me.procedures.penguin.kit;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
@RequiredArgsConstructor
public class KitInventory {

    private ItemStack[] contents;
    private ItemStack[] armor;
}
