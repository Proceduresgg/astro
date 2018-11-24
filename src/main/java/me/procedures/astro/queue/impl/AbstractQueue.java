package me.procedures.astro.queue.impl;

import lombok.Getter;
import me.procedures.astro.AstroPlugin;
import me.procedures.astro.inventories.StateInventories;
import me.procedures.astro.ladder.Ladder;
import me.procedures.astro.player.PlayerProfile;
import me.procedures.astro.player.PlayerState;
import me.procedures.astro.utils.GameUtil;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;

@Getter
public abstract class AbstractQueue {

    private final AstroPlugin plugin;
    private final Ladder ladder;

    private List<QueuePlayer> queue = new LinkedList<>();

    private int playingAmount;

    public AbstractQueue(AstroPlugin plugin, Ladder ladder) {
        this.plugin = plugin;
        this.ladder = ladder;
    }

    public abstract void createMatch(QueuePlayer playerOne, QueuePlayer playerTwo);

    public abstract void startQueueTask();

    public void addToQueue(Player player) {
        PlayerProfile playerProfile = this.plugin.getProfileManager().getProfile(player);
        QueuePlayer queuePlayer = new QueuePlayer(player);

        //int min = playerProfile.getRatings().get(this.ladder).getRating() - 250;
        //int max = playerProfile.getRatings().get(this.ladder).getRating() + 250;

        //queuePlayer.setMin(min);
        //queuePlayer.setMax(max);

        this.queue.add(queuePlayer);

        GameUtil.resetPlayer(player);
        player.getInventory().setContents(StateInventories.QUEUE.getContents());
        playerProfile.setState(PlayerState.QUEUING);

        this.playingAmount++;
    }

    public void removeFromQueue(Player player) {
        PlayerProfile playerProfile = this.plugin.getProfileManager().getProfile(player);

        playerProfile.setState(PlayerState.LOBBY);
        player.getInventory().setContents(StateInventories.LOBBY.getContents());

        GameUtil.resetPlayer(player);

        this.queue.forEach(queuePlayer -> {
            if (queuePlayer.getPlayer() == player) {
                this.queue.remove(queuePlayer);
            }
        });

        this.playingAmount--;
    }
}
