package backend;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.HashMap;

public class MongoConnector {
    private static final MongoConnector SINGLE_INSTANCE = new MongoConnector();
    private MongoClient client;
    private MongoDatabase database;
    private HashMap<String, MongoCollection<Document>> collections;

    private MongoConnector() {
        collections = new HashMap<String, MongoCollection<Document>>();
    }

    public static MongoConnector getInstance() {
        return SINGLE_INSTANCE;
    }

    public void startConnection(String host, int port, String databaseName) {
        if(client == null) {
            client = new MongoClient(host, port);
            database = client.getDatabase(databaseName);
        }
    }

    public MongoCollection<Document> getCollection(String collectionName) {
        if(!collections.containsKey(collectionName))
            collections.put(collectionName, database.getCollection(collectionName));

        return collections.get(collectionName);
    }

}
