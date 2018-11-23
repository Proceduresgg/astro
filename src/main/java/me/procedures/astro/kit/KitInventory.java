package me.procedures.astro.kit;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
@RequiredArgsConstructor
public class KitInventory {

    private ItemStack[] contents;
    private ItemStack[] armor;

    public void apply(Player player) {
        player.getInventory().setContents(this.contents);
        player.getInventory().setArmorContents(this.armor);
    }
}
