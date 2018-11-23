package me.procedures.astro.arena;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

@Getter
@Setter
@AllArgsConstructor
public class Arena {

    private String name;

    private Location spawnOne = Bukkit.getWorld("world").getSpawnLocation();
    private Location spawnTwo = Bukkit.getWorld("world").getSpawnLocation();

    public void save() {

    }
}
