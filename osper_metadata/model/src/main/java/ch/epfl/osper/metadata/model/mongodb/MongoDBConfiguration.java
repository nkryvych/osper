package ch.epfl.osper.metadata.model.mongodb;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import javax.inject.Named;

import java.net.UnknownHostException;
import java.util.logging.Logger;

/**
 * Created by kryvych on 21/12/14.
 */

@Configuration
public class MongoDBConfiguration {

    protected final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean(name="measurementRecordCollection")
    public DBCollection getMeasurementRecordCollection(){
        try {
            MongoClient mongoClient = new MongoClient();
            DB db = mongoClient.getDB("metadata");
            return db.getCollection("measurementRecords");
        } catch (UnknownHostException e) {
            logger.error("CANNOT CONNECT TO MONGODB " , e);
            return null;
        }
    }
}
