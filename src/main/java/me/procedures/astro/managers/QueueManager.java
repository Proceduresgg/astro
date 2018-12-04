package me.procedures.astro.managers;

import lombok.Getter;
import me.procedures.astro.AstroPlugin;
import me.procedures.astro.ladder.Ladder;
import me.procedures.astro.queue.QueueContainer;
import me.procedures.astro.queue.type.RankedQueue;
import me.procedures.astro.queue.type.UnrankedQueue;

import java.util.HashMap;
import java.util.Map;

@Getter
public class QueueManager {

    private final AstroPlugin plugin;

    private Map<Ladder, QueueContainer> queues = new HashMap<>();

    public QueueManager(AstroPlugin plugin) {
        this.plugin = plugin;

        this.initializeQueues();
    }

    private void initializeQueues() {
        this.plugin.getLadderManager().getLadders().values().forEach(ladder -> {
            UnrankedQueue unrankedQueue = new UnrankedQueue(this.plugin, ladder);
            RankedQueue rankedQueue = new RankedQueue(this.plugin, ladder);

            this.queues.put(ladder, new QueueContainer(unrankedQueue, rankedQueue));
        });
    }

    public int getTotalInFight() {
        return this.queues.values()
                .stream()
                .mapToInt(QueueContainer::getPlayingAmount)
                .sum();
    }

    public int getTotalInQueue() {
        return this.queues.values()
                .stream()
                .mapToInt(QueueContainer::getQueuingAmount)
                .sum();
    }

    public RankedQueue getRankedQueue(Ladder ladder) {
        return this.queues.get(ladder).getRankedQueue();
    }

    public UnrankedQueue getUnrankedQueue(Ladder ladder) {
        return this.queues.get(ladder).getUnrankedQueue();
    }

    public void createQueues(Ladder ladder) {
        this.queues.computeIfAbsent(ladder, k -> new QueueContainer(new UnrankedQueue(this.plugin, ladder), new RankedQueue(this.plugin, ladder)));
    }
}
