package me.procedures.astro.queue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.procedures.astro.queue.type.RankedQueue;
import me.procedures.astro.queue.type.UnrankedQueue;

@Getter @Setter
@RequiredArgsConstructor
public class QueueContainer {

    private final UnrankedQueue unrankedQueue;
    private final RankedQueue rankedQueue;

    public int getPlayingAmount() {
        return this.rankedQueue.getPlayingAmount() + this.unrankedQueue.getPlayingAmount();
    }

    public int getQueuingAmount() {
        return this.rankedQueue.getQueue().size() + this.unrankedQueue.getQueue().size();
    }
}
