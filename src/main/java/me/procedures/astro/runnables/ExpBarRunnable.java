package me.procedures.astro.runnables;

import me.procedures.astro.AstroPlugin;
import me.procedures.astro.player.PlayerProfile;
import me.procedures.astro.player.PlayerState;
import me.procedures.astro.timer.Timer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ExpBarRunnable extends BukkitRunnable {

    private final Timer timer;
    private final Player player;
    private final PlayerProfile profile;

    public ExpBarRunnable(Timer timer, Player player) {
        this.timer = timer;
        this.player = player;
        this.profile = AstroPlugin.getInstance().getProfileManager().getProfile(player);
    }

    @Override
    public void run() {
        if (!this.timer.active() || this.profile.getState() != PlayerState.FIGHTING) {
            this.player.setExp(0);
            this.player.setLevel(0);

            this.cancel();

        } else {
            this.player.setLevel((int) Math.round(this.timer.getTimeLeft() / 1000.0));
            this.player.setExp((float) this.timer.getTimeLeft() / 16_000.0F);
        }
    }
}
