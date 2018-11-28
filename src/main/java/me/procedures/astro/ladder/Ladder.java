package me.procedures.astro.ladder;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import lombok.Setter;
import me.procedures.astro.AstroPlugin;
import me.procedures.astro.kit.KitInventory;
import me.procedures.astro.utils.InventoryUtil;
import org.bson.Document;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Ladder implements ILadder {

    private final Map<LadderFlag, Boolean> ladderFlags = new HashMap<>();

    private String name;

    private KitInventory defaultInventory = new KitInventory();
    private ItemStack displayItem = new ItemStack(Material.DIAMOND_SWORD);

    private int displayOrder = 20;

    public Ladder(String name) {
        this.name = name;

        Arrays.stream(LadderFlag.values())
                .forEach(ladderFlag -> this.ladderFlags.put(ladderFlag, ladderFlag.isDefaultFlag()));
    }

    public void save() {
        Document document = new Document("name", this.name)
                .append("display-order", this.displayOrder)
                .append("default-inv", InventoryUtil.playerInvToString(this.defaultInventory));

        this.ladderFlags.keySet().forEach(flag -> document.append(flag.toString(), this.ladderFlags.get(flag)));

        AstroPlugin.getInstance().getMongo().getPracticeDatabase().getCollection("ladders").replaceOne(Filters.eq("name", this.name), document, new ReplaceOptions().upsert(true));
    }

    public void load(Document document) {
        try {
            this.displayOrder = document.getInteger("display-order");

            String inventory = document.getString("default-inv");

            if (inventory != null) {
                this.defaultInventory = InventoryUtil.playerInventoryFromString(inventory);
            }

            Arrays.stream(LadderFlag.values())
                    .forEach(ladderFlag -> this.ladderFlags.put(ladderFlag, document.getBoolean(ladderFlag.toString())));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
