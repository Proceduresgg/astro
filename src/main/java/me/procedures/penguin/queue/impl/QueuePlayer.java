package me.procedures.penguin.queue.impl;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

@Getter
@Setter
public class QueuePlayer {

    private Player player;

    private int min;
    private int max;

    public QueuePlayer(Player player) {
        this.player = player;
    }
}
