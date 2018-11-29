package me.procedures.astro.runnables;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.procedures.astro.AstroPlugin;
import me.procedures.astro.match.Match;
import me.procedures.astro.utils.MessageUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

@Getter @Setter
@AllArgsConstructor
public class MatchStartRunnable extends BukkitRunnable {

    private Match match;

    private int count;

    @Override
    public void run() {
        FileConfiguration messageConfig = AstroPlugin.getInstance().getConfiguration().getMessages().getConfig();

        switch (this.count) {
            case 0:
                this.match.startMatch();
                this.match.sendMessage(MessageUtil.color(messageConfig.getString("match.start")));

                this.cancel();
                return;

            default:
                this.match.sendMessage(MessageUtil.color(messageConfig.getString("match.countdown").replace('$', Character.forDigit(this.count, 10))));
                break;
        }

        this.count--;
    }
}
