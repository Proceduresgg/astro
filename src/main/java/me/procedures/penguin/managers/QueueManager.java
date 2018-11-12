package me.procedures.penguin.managers;

import lombok.Getter;
import me.procedures.penguin.PenguinPlugin;
import me.procedures.penguin.ladder.impl.Ladder;
import me.procedures.penguin.queue.api.AbstractQueue;
import me.procedures.penguin.queue.impl.UnrankedQueue;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

@Getter
public class QueueManager {

    private final PenguinPlugin plugin;

    private Map<Ladder, AbstractQueue> queues = new HashMap<>();

    public QueueManager(PenguinPlugin plugin) {
        this.plugin = plugin;
        this.initializeQueues();
    }

    private void initializeQueues() {
        for (Ladder ladder : this.plugin.getLadderManager().getLadders().values()) {
            UnrankedQueue unrankedQueue = new UnrankedQueue(this.plugin, ladder);

            this.queues.put(ladder, unrankedQueue);
        }
    }
}
