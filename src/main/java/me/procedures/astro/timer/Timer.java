package me.procedures.astro.timer;

import lombok.Getter;
import me.procedures.astro.AstroPlugin;
import me.procedures.astro.runnables.ExpBarRunnable;
import org.bukkit.entity.Player;

@Getter
public class Timer {

    private long start;
    private long end;

    public Timer(int length, Player player) {
        this.start = System.currentTimeMillis();
        this.end = this.start + (length * 1000);

        this.showExpCountdown(player);
    }

    public long getTimeLeft() {
        return this.end - System.currentTimeMillis();
    }

    public boolean active() {
        if (System.currentTimeMillis() >= this.end) {
            return false;
        }

        return true;
    }

    public void showExpCountdown(Player player) {
        new ExpBarRunnable(this, player).runTaskTimer(AstroPlugin.getInstance(), 1L, 1L);
    }
}
