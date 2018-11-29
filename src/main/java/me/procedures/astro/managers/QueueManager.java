package me.procedures.astro.managers;

import lombok.Getter;
import me.procedures.astro.AstroPlugin;
import me.procedures.astro.ladder.Ladder;
import me.procedures.astro.queue.AbstractQueue;
import me.procedures.astro.queue.type.UnrankedQueue;

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
        this.plugin.getLadderManager().getLadders().values().forEach(ladder -> {
            this.queues.put(ladder, new UnrankedQueue(this.plugin, ladder));
        });
    }

    public int getTotalInFight() {
        return this.queues.values()
                .stream()
                .mapToInt(AbstractQueue::getPlayingAmount)
                .sum();
    }

    public int getTotalInQueue() {
        return this.queues.values()
                .stream()
                .mapToInt(queue -> queue.getQueue().size())
                .sum();
    }
}
