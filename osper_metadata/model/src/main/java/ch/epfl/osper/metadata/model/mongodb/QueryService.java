package ch.epfl.osper.metadata.model.mongodb;

import com.google.common.base.Joiner;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by kryvych on 21/12/14.
 */
public class QueryService {

    private MongoDBConfiguration configuration = new MongoDBConfiguration();

    public QueryService(MongoDBConfiguration configuration) {
        this.configuration = configuration;
    }

    public String getAllMeasurementRecords() {
        try {
            DBCollection collection = configuration.getMeasurementRecordCollection();

            // create an empty query
            BasicDBObject query = new BasicDBObject();

            BasicDBObject fields = new BasicDBObject("title", true)
                    .append("_id", false)
                    .append("type", true)
                    .append("mesurementLocation", true)
                    .append("samplingFreq", true)
                    .append("serialNumber", true)
                    .append("fromDate", true)
                    .append("toDate", true)
                    .append("server", true)
                    .append("organisation", true)
                    .append("slopeAngle", true)
                    .append("aspect", true)
                    .append("location", true)
                    .append("observedProperty", true);


            DBCursor cursor = collection.find(query, fields);


            StringBuilder resultBuilder = new StringBuilder();
            resultBuilder.append("[\n");
            StringBuilder join = Joiner.on(",\n").appendTo(resultBuilder, ((Iterable<DBObject>) cursor)).append("]");

            return join.toString();


        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "CANNOT ACCESS DB";
        }
    }

    public String getAllObservedProperties() {
        try {
            DBCollection collection = configuration.getMeasurementRecordCollection();
            List observedProperty = collection.distinct("observedProperty");

            return "";

        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "CANNOT ACCESS DB";
        }
    }
}
