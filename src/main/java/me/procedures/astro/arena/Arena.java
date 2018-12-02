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

@Getter @Setter
public class Arena {

    private String name;

    private AstroLocation spawnOne;
    private AstroLocation spawnTwo;

    private ArenaType type = ArenaType.MULTI;

    /* If the arena type is multi, then the ladder is null since any ladder can use it. */
    private Ladder ladder = null;

    public Arena(String name) {
        this.name = name;
    }

    public void save() {
        Document document = new Document("name", this.name)
                .append("spawn-one", LocationUtil.serializeAstroLocation(this.spawnOne))
                .append("spawn-two", LocationUtil.serializeAstroLocation(this.spawnTwo))
                .append("arena-type", this.type.toString());

        AstroPlugin.getInstance().getMongo().getPracticeDatabase().getCollection("arenas").replaceOne(Filters.eq("name", this.name), document, new ReplaceOptions().upsert(true));
    }

    public void load(Document document) {
        try {
            this.spawnOne = LocationUtil.deserializeAstroLocation(document.getString("spawn-one"));
            this.spawnTwo = LocationUtil.deserializeAstroLocation(document.getString("spawn-two"));
            this.type = ArenaType.valueOf(document.getString("arena-type"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
