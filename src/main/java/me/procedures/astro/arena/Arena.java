package me.procedures.astro.arena;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

@Getter
@Setter
@AllArgsConstructor
public class Arena {

    private String name;

    private Location spawnOne;
    private Location spawnTwo;

    public void save() {

    }
}
