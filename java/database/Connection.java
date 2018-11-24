package database;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.log4j.Logger;


import java.util.Arrays;

import static database.Utils.*;
import static database.security.AccessData.getAccessInstance;


public final class Connection {

    private static final Logger log = Logger.getLogger(Connection.class);

    private static String db_name;

    private static final String db_collection_students     = "students";
    private static final String db_collection_users        = "users";
    private static final String db_collection_universities = "universities";
    private static final String db_collection_posts        = "posts";
    private static final String db_collection_files        = "files";
    private static final String db_collection_interests    = "interests";
    private static final String db_collection_comments     = "comments";
    private static final String db_collection_blacklist    = "blacklist";

    private static MongoClient mongoClient = null;

    // Method to make a connection to the mongodb server listening on a default port
    private static MongoClient getClient() {

        if (mongoClient == null) {

            db_name = getAccessInstance().getResource("MongoDbName");

            if (getAccessInstance().getEnvironment().equalsIgnoreCase("prod")) {

                log.info("new prod connection");

                MongoCredential credential = MongoCredential.createCredential(
                        getAccessInstance().getResource("MongoUsername"),
                        db_name,
                        getAccessInstance().getResource("MongoPassword").toCharArray());

                mongoClient = new MongoClient(new ServerAddress(
                        getAccessInstance().getResource("MongoUrl"),
                        Integer.parseInt(getAccessInstance().getResource("MongoPort"))),
                        Arrays.asList(credential));

            } else if (getAccessInstance().getEnvironment().equalsIgnoreCase("dev")) {

                log.info("new dev connection");

                mongoClient = new MongoClient(getAccessInstance().getResource("MongoUrl"),
                        Integer.parseInt(getAccessInstance().getResource("MongoPort")));

            }

        }

        return mongoClient;
    }

    /**
     * If database doesn't exists, MongoDB will create it for you
     */
    private static MongoDatabase getMongoDB() {
        return getClient().getDatabase(db_name);
    }

    public static MongoCollection getMongoCollection(int collection) {
        switch (collection) {
            case STUDENT:
                return getMongoDB().getCollection(db_collection_students);

            case USER:
                return getMongoDB().getCollection(db_collection_users);

            case POSTS:
                return getMongoDB().getCollection(db_collection_posts);

            case UNIVERSITY:
                return getMongoDB().getCollection(db_collection_universities);

            case FILE:
                return getMongoDB().getCollection(db_collection_files);

            case INTEREST:
                return getMongoDB().getCollection(db_collection_interests);

            case COMMENT:
                return getMongoDB().getCollection(db_collection_comments);

            case BLACKLIST:
                return getMongoDB().getCollection(db_collection_blacklist);
        }

        return null;
    }

    public static void closeConnection() {
        mongoClient.close();
        mongoClient = null;
    }


}
