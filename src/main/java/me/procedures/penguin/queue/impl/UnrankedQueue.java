package me.procedures.penguin.queue.impl;

import me.procedures.penguin.PenguinPlugin;
import me.procedures.penguin.ladder.impl.Ladder;
import me.procedures.penguin.queue.api.AbstractQueue;
import me.procedures.penguin.queue.api.Queue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Iterator;

public class UnrankedQueue extends AbstractQueue {

    public UnrankedQueue(PenguinPlugin plugin, Ladder ladder) {
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
