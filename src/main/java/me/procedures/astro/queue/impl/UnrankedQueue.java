package me.procedures.astro.queue.impl;

import me.procedures.astro.AstroPlugin;
import me.procedures.astro.ladder.Ladder;
import me.procedures.astro.match.Match;
import me.procedures.astro.match.options.Unranked;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class UnrankedQueue extends AbstractQueue {

    public UnrankedQueue(AstroPlugin plugin, Ladder ladder) {
        super(plugin, ladder);
        this.startQueueTask();
    }

    @Override
    public void createMatch(QueuePlayer playerOne, QueuePlayer playerTwo) {
        new Match(AstroPlugin.getInstance(), this.getLadder(), Collections.singletonList(playerOne.getPlayer()), Collections.singletonList(playerTwo.getPlayer()), Collections.singletonList(new Unranked()));
    }

    @Override
    public void startQueueTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                Iterator<QueuePlayer> iterator = getQueue().iterator();

                if (iterator.hasNext()) {
                    QueuePlayer player = iterator.next();

                    if (!iterator.hasNext() || iterator.next() == player) {
                        return;
                    }

                    QueuePlayer opponent = iterator.next();

                    getQueue().remove(player);
                    getQueue().remove(opponent);

                    createMatch(player, opponent);
                }
            }
        }.runTaskTimer(this.getPlugin(), 0L, 2L);
    }
}
