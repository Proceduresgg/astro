package me.procedures.astro.player;

import com.mongodb.client.model.Filters;
import lombok.Getter;
import lombok.Setter;
import me.procedures.astro.AstroPlugin;
import me.procedures.astro.kit.KitContainer;
import me.procedures.astro.ladder.Ladder;
import me.procedures.astro.match.Match;
import me.procedures.astro.queue.AbstractQueue;
import me.procedures.astro.utils.ItemBuilder;
import me.procedures.astro.timer.Timer;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.*;

@Getter @Setter
public class PlayerProfile {

    private final UUID uuid;

    private final Map<Ladder, KitContainer> kits = new HashMap<>();
    private final Map<Ladder, Integer> ratings = new HashMap<>();

    private PlayerState state;

    private Match match;
    private AbstractQueue queue;

    private Timer enderpearlTimer;

    public PlayerProfile(UUID identifier) {
        this.uuid = identifier;
    }

    public ItemStack[] getKits(Ladder ladder) { // TODO: Make it return all the player kits
        ItemStack[] kits = {new ItemBuilder(Material.BOOK, ChatColor.GOLD + "Default Kit", 1).getItem()};
        KitContainer kitContainer = this.kits.get(ladder);

        return kits;
    }

    public void load() {
        Document document = AstroPlugin.getInstance().getMongo().getPracticeDatabase().getCollection("profiles").find(Filters.eq("uuid", this.uuid.toString())).first();

        if (document != null) {
            AstroPlugin.getInstance().getLadderManager().getLadders().values().forEach(ladder -> {
                this.ratings.put(ladder, document.getInteger(ladder.getName()) != null ? document.getInteger(ladder.getName()) : 1000);
            });
        }
    }

    public boolean canPearl() {
        if (this.enderpearlTimer == null) {
            this.addPearlTimer();

            return true;

        } else return !this.enderpearlTimer.active();
    }

    public int getElo(Ladder ladder) {
        return this.ratings.computeIfAbsent(ladder, k -> 1000);
    }

    public void addPearlTimer() {
        this.enderpearlTimer = new Timer(16, Bukkit.getPlayer(this.uuid));
    }
}
