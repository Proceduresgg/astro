package me.procedures.astro.managers;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.procedures.astro.AstroPlugin;
import me.procedures.astro.arena.Arena;
import me.procedures.astro.arena.ArenaType;
import me.procedures.astro.ladder.Ladder;
import org.bson.Document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Getter
public class ArenaManager {

    private final AstroPlugin plugin;

    private final Map<String, Arena> arenas = new HashMap<>();

    public ArenaManager(AstroPlugin plugin) {
        this.plugin = plugin;

        this.loadArenas();
    }

    public Arena getRandomArena() {
        List<Arena> arenas = this.arenas.values()
                .stream()
                //.filter(arena -> arena.getType() == ArenaType.SINGULAR)
                //.filter(arena -> arena.getSpawnOne() == null)
                //.filter(arena -> arena.getSpawnTwo() == null)
                .collect(Collectors.toList());

        System.out.println(arenas.size());

        return arenas.get(ThreadLocalRandom.current().nextInt(0, arenas.size() - 1));
    }

    public Arena getSingularArena(Ladder ladder) {
        List<Arena> arenas = this.arenas.values()
                .stream()
                .filter(arena -> arena.getType() == ArenaType.MULTI)
                .filter(arena -> arena.getLadder() != ladder)
                .collect(Collectors.toList());

        return arenas.size() != 0 ? arenas.get(ThreadLocalRandom.current().nextInt(0, arenas.size())) : null;
    }

    public void saveArenas() {
        this.arenas.values().forEach(Arena::save);
    }

    private void loadArenas() {
        MongoCollection<Document> collection = this.plugin.getMongo().getPracticeDatabase().getCollection("arenas");

        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                Document document = cursor.next();

                if (document.getString("name") == null) {
                    collection.deleteOne(document);
                    continue;

                } else if (document.getString("name").contains(" ")) {
                    collection.deleteOne(document);
                    continue;
                }

                Arena arena = new Arena(document.getString("name"));
                arena.load(document);

                this.arenas.put(arena.getName(), arena);
            }
        }
    }
}
