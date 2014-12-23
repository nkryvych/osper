package ch.epfl.osper.metadata.model.mongodb;

import com.google.common.base.Joiner;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;
import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by kryvych on 21/12/14.
 */
@Controller
@Scope("prototype")
public class QueryService {

    private MongoDBConfiguration configuration;

    @Inject
    public QueryService(MongoDBConfiguration configuration) {
        this.configuration = configuration;
    }

    public String getAllMeasurementRecords() {

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
                .append("dbTableName", true)
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


    }

    public String getAllObservedProperties() {

        DBCollection collection = configuration.getMeasurementRecordCollection();
        List observedProperty = collection.distinct("observedProperty");

        return "";

    }
}
