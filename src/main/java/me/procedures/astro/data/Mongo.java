package me.procedures.astro.data;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class Mongo {

    private MongoClient mongoClient;
    private MongoDatabase practiceDatabase;

    public Mongo() {
        MongoClientOptions options = MongoClientOptions.builder().connectionsPerHost(50).build();
        MongoCredential credential = MongoCredential.createCredential("Shyon", "practice", "fuck".toCharArray());

        this.mongoClient = new MongoClient(new ServerAddress("127.0.0.1", 27017), credential, options);
        this.practiceDatabase = this.mongoClient.getDatabase("practice");
    }

    private boolean collectionExists(final String collectionName) {
        return this.practiceDatabase.listCollectionNames().into(new ArrayList<>()).contains(collectionName);
    }
}

