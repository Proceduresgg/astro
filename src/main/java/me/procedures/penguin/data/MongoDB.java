package me.procedures.penguin.data;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import lombok.Getter;

@Getter
public class MongoDB {
    private final MongoClient mongoClient = new MongoClient("localhost", 27017);
    private final MongoCredential mongoCredential = MongoCredential.createCredential("procedures", "practiceDatabase", "efkwe;lfkew;l".toCharArray());
}
