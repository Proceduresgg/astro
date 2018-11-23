package me.procedures.astro.queue.impl;

import me.procedures.astro.AstroPlugin;
import me.procedures.astro.ladder.Ladder;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Iterator;

public class UnrankedQueue extends AbstractQueue {

    public UnrankedQueue(AstroPlugin plugin, Ladder ladder) {
        super(plugin, ladder);
        this.startQueueTask();
    }

    @Override
    public void createMatch(QueuePlayer playerOne, QueuePlayer playerTwo) {

    }

    @Override
    public void startQueueTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                Iterator<QueuePlayer> iterator = getQueue().iterator();

                while (iterator.hasNext()) {
                    QueuePlayer player = iterator.next();

                    if (iterator.hasNext()) {
                        QueuePlayer opponent = iterator.next();

                        getQueue().remove(player);
                        getQueue().remove(opponent);

                        createMatch(player, opponent);
                    }
                }
            }
        }.runTaskLater(this.getPlugin(), 20L);
    }
}
