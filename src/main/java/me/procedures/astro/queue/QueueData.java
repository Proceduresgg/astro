package me.procedures.astro.queue;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

@Getter
@Setter
public class QueueData {

    private Player player;

    private int min;
    private int max;

    private final long insertionTime = System.currentTimeMillis();

    public QueueData(Player player) {
        this.player = player;
    }
}
