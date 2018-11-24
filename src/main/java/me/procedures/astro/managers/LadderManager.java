package me.procedures.astro.managers;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import lombok.Getter;
import me.procedures.astro.AstroPlugin;
import me.procedures.astro.ladder.Ladder;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

@Getter
public class LadderManager {

    private final AstroPlugin plugin;

    private final Map<String, Ladder> ladders = new HashMap<>();

    public LadderManager(AstroPlugin plugin) {
        this.plugin = plugin;
        this.loadLadders();
    }

    private void loadLadders() {
        MongoCollection<Document> collection = this.plugin.getMongo().getPracticeDatabase().getCollection("ladders");

        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                Document document = cursor.next();

                if (document.getString("name") == null) {
                    collection.deleteOne(document);
                    continue;
                }

                Ladder ladder = new Ladder(document.getString("name"));
                ladder.load(document);

                this.ladders.put(ladder.getName(), ladder);
            }
        }
    }


    public void saveLadders() {
        this.ladders.values().forEach(Ladder::save);
    }
}
