package me.procedures.astro.arena;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.procedures.astro.AstroPlugin;
import me.procedures.astro.ladder.Ladder;
import me.procedures.astro.ladder.LadderFlag;
import me.procedures.astro.location.AstroLocation;
import me.procedures.astro.utils.InventoryUtil;
import me.procedures.astro.utils.LocationUtil;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.Arrays;
import java.util.logging.Level;

@Getter @Setter
public class Arena {

    private String name;

    private AstroLocation spawnOne, spawnTwo;

    private ArenaType type = ArenaType.MULTI;

    /* If the arena type is multi, then the ladder is null since any ladder can use it. */
    private Ladder ladder = null;

    public Arena(String name) {
        this.name = name;
    }

    public void save() {
        Document document = new Document("name", this.name)
                .append("arena-type", this.type.toString());

        if (this.spawnOne != null) {
            document.append("spawn-one", LocationUtil.serializeAstroLocation(this.spawnOne));
        }

        if (this.spawnTwo != null) {
            document.append("spawn-two", LocationUtil.serializeAstroLocation(this.spawnTwo));
        }

        System.out.println("Saved " + this.name);

        AstroPlugin.getInstance().getMongo().getPracticeDatabase().getCollection("arenas").replaceOne(Filters.eq("name", this.name), document, new ReplaceOptions().upsert(true));
    }

    public void load(Document document) {
        try {
            this.spawnOne = LocationUtil.deserializeAstroLocation(document.getString("spawn-one"));
            this.spawnTwo = LocationUtil.deserializeAstroLocation(document.getString("spawn-two"));
            this.type = ArenaType.valueOf(document.getString("arena-type"));

            System.out.println("Loaded " + this.name);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Location getSpawnOne() {
        return this.spawnOne.toLocation();
    }

    public Location getSpawnTwo() {
        return this.spawnTwo.toLocation();
    }
}
