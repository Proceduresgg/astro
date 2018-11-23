package me.procedures.astro.player;

import com.mongodb.client.model.Filters;
import lombok.Getter;
import lombok.Setter;
import me.procedures.astro.AstroPlugin;
import me.procedures.astro.kit.KitContainer;
import me.procedures.astro.kit.KitInventory;
import me.procedures.astro.ladder.Ladder;
import me.procedures.astro.match.AbstractMatch;
import me.procedures.astro.utils.ItemBuilder;
import org.bson.Document;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import us.rengo.milk.MilkPlugin;

import java.util.*;

@Getter
@Setter
public class PlayerProfile {

    private final UUID uuid;

    private final Map<Ladder, KitContainer> kits = new HashMap<>();
    private final Map<Ladder, Integer> ratings = new HashMap<>();

    private PlayerState state = PlayerState.LOBBY;

    private AbstractMatch match;

    public PlayerProfile(UUID identifier) {
        this.uuid = identifier;

        for (Ladder ladder : AstroPlugin.getInstance().getLadderManager().getLadders().values()) {
            this.ratings.put(ladder, 1000);
        }
    }

    public ItemStack[] getKits(Ladder ladder) {
        ItemStack[] kits = {new ItemBuilder(Material.ENCHANTED_BOOK, ChatColor.GOLD + "Default Kit").getItem()};
        KitContainer kitContainer = this.kits.get(ladder);

        return kits;
    }

    public void load() {
        Document document = AstroPlugin.getInstance().getMongo().getPracticeDatabase().getCollection("profiles").find(Filters.eq("uuid", this.uuid.toString())).first();

        if (document != null) {
            for (Ladder ladder : AstroPlugin.getInstance().getLadderManager().getLadders().values()) {
                this.ratings.put(ladder, document.getInteger(ladder.getName()) != null ? document.getInteger(ladder.getName()) : 1000);
            }
        }
    }
}
