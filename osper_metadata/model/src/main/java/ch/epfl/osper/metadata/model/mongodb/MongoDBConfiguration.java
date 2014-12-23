package ch.epfl.osper.metadata.model.mongodb;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;


import javax.inject.Named;

import java.net.UnknownHostException;

/**
 * Created by kryvych on 21/12/14.
 */

//@Configuration
public class MongoDBConfiguration {

//    @Bean(name="measurementRecordCollection")
    public DBCollection getMeasurementRecordCollection() throws UnknownHostException {
        MongoClient mongoClient = new MongoClient();
        DB db = mongoClient.getDB("metadata");
        return db.getCollection("measurementRecords");
    }
}
