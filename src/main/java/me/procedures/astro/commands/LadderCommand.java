package me.procedures.astro.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import me.procedures.astro.data.Mongo;
import me.procedures.astro.kit.KitInventory;
import me.procedures.astro.ladder.Ladder;
import me.procedures.astro.managers.LadderManager;
import me.procedures.astro.managers.QueueManager;
import me.procedures.astro.queue.type.UnrankedQueue;
import me.procedures.astro.utils.CC;
import me.procedures.astro.utils.InventoryUtil;
import org.bson.Document;
import org.bukkit.entity.Player;

@CommandAlias("ladder")
@CommandPermission("procedures.manager")
public class LadderCommand extends BaseCommand {

    @Dependency private LadderManager ladderManager;
    @Dependency private QueueManager queueManager;
    @Dependency private Mongo mongo;

    @Default
    public void onDefault(Player player) {
        player.sendMessage(CC.PRIMARY + "Usage: /ladder [create, delete, setdefaultinventory, setorder, setname]");
    }

    @Subcommand("create")
    public void onCreate(Player player, String name) {
        if (this.ladderManager.getLadders().containsKey(name)) {
            player.sendMessage(CC.PRIMARY + "That ladder already exists.");
            return;
        }

        Ladder ladder = new Ladder(name);
        ladder.save();

        player.sendMessage(CC.PRIMARY + "That ladder has been created.");

        this.ladderManager.getLadders().put(name, ladder);
        this.queueManager.getQueues().put(ladder, new UnrankedQueue(this.queueManager.getPlugin(), ladder));
    }

    @Subcommand("delete")
    public void onDelete(Player player, Ladder ladder) {
        this.ladderManager.getLadders().remove(ladder.getName());
        this.queueManager.getQueues().remove(ladder);

        MongoCollection<Document> collection = this.mongo.getPracticeDatabase().getCollection("ladders");

        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                Document document = cursor.next();

                if (document.getString("name").equals(ladder.getName())) {
                    collection.deleteOne(document);
                    break;
                }
            }
        }

        player.sendMessage(CC.PRIMARY + "That ladder has been deleted.");
    }

    @Subcommand("setdefaultinventory")
    public void onSetDefaultInventory(Player player, Ladder ladder) {
        KitInventory kitInventory = InventoryUtil.inventoryFromPlayer(player);

        ladder.setDefaultInventory(kitInventory);
        ladder.save();

        player.sendMessage(CC.PRIMARY + "The default inventory for that ladder has been updated.");
    }

    @Subcommand("setorder")
    public void onSetOrder(Player player, Ladder ladder, int order) {
        if (order > 54 || order < 0) {
            player.sendMessage(CC.PRIMARY + "You need to specify an order within 0 - 54.");
            return;
        }

        ladder.setDisplayOrder(order);
        ladder.save();

        player.sendMessage(CC.PRIMARY + "The display order for that ladder has been set.");
    }
}
