package me.procedures.astro.runnables;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.procedures.astro.match.Match;
import me.procedures.astro.utils.CC;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Set;

@Getter @Setter
@AllArgsConstructor
public class MatchStartRunnable extends BukkitRunnable {

    private Match match;

    private int count;

    @Override
    public void run() {
        switch (this.count) {
            case 0:
                this.match.startMatch();
                this.match.sendMessage(CC.LIGHT + "The match has started.");

                this.cancel();
                return;

            case 1:
                this.match.sendMessage(CC.LIGHT + "The match is starting in " + CC.BRIGHT + this.count + "second.");
                break;

            default:
                this.match.sendMessage(CC.LIGHT + "The match is starting in " + CC.BRIGHT + this.count + "seconds.");
                break;
        }

        this.count--;
    }
}
