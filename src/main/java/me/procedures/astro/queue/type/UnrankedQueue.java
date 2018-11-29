package me.procedures.astro.queue.type;

import me.procedures.astro.AstroPlugin;
import me.procedures.astro.ladder.Ladder;
import me.procedures.astro.match.Match;
import me.procedures.astro.match.options.Unranked;
import me.procedures.astro.queue.AbstractQueue;
import me.procedures.astro.queue.QueueData;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class UnrankedQueue extends AbstractQueue {

    public UnrankedQueue(AstroPlugin plugin, Ladder ladder) {
        super(plugin, ladder);
        this.startQueueTask();
    }

    @Override
    public void createMatch(QueueData playerOne, QueueData playerTwo) {
        new Match(AstroPlugin.getInstance(), this.getLadder(), playerOne.getPlayer(), playerTwo.getPlayer(), new Unranked());
        this.setPlayingAmount(this.getPlayingAmount() + 2);
    }

    @Override
    public void startQueueTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                Iterator<QueueData> iterator = getQueue().iterator();

                if (iterator.hasNext()) {
                    QueueData player = iterator.next();

                    if (!iterator.hasNext()) {
                        return;
                    }

                    QueueData opponent = iterator.next();

                    if (opponent == player) {
                        return;
                    }

                    getQueue().remove(player);
                    getQueue().remove(opponent);

                    createMatch(player, opponent);
                }
            }
        }.runTaskTimer(this.getPlugin(), 0L, 2L);
    }
}
