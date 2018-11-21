package me.procedures.astro.managers;

import lombok.Getter;
import me.procedures.astro.AstroPlugin;
import me.procedures.astro.ladder.impl.Ladder;
import me.procedures.astro.queue.api.AbstractQueue;
import me.procedures.astro.queue.impl.UnrankedQueue;

import java.util.HashMap;
import java.util.Map;

@Getter
public class QueueManager {

    private final AstroPlugin plugin;

    private Map<Ladder, AbstractQueue> queues = new HashMap<>();

    public QueueManager(AstroPlugin plugin) {
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
