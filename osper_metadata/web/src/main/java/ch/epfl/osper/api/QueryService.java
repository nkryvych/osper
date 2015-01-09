package ch.epfl.osper.api;

import ch.epfl.osper.metadata.model.mongodb.MongoDBConfiguration;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;
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

    public String getAllMeasurementRecords(MeasurementRecordQuery recordQuery) {

        DBCollection collection = configuration.getMeasurementRecordCollection();


        BasicDBObject fields = new BasicDBObject("title", true)
                .append("_id", false)
                .append("type", true)
                .append("measurementLocation", true)
                .append("measurementLocationTitle", true)
                .append("samplingFreq", true)
                .append("serialNumber", true)
                .append("fromDate", true)
                .append("toDate", true)
                .append("server", true)
                .append("dbTableName", true)
                .append("organisation", true)
                .append("slopeAngle", true)
                .append("elevation", true)
                .append("aspect", true)
                .append("location", true)
                .append("observedProperty", true);


        DBCursor cursor = collection.find(buildDBQuery(recordQuery), fields);


        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append("[\n");
        StringBuilder join = Joiner.on(",\n").appendTo(resultBuilder, ((Iterable<DBObject>) cursor)).append("]");

        return join.toString();


    }

    protected BasicDBObject buildDBQuery(MeasurementRecordQuery recordQuery) {
        BasicDBObject query = new BasicDBObject();
        if (StringUtils.isNotEmpty(recordQuery.getMeasurementLocationName())) {
            query.append("measurementLocation", recordQuery.getMeasurementLocationName());
        }
        if (!recordQuery.getProperties().isEmpty()) {
            query.append("observedProperty", new BasicDBObject("$in", Lists.newArrayList(recordQuery.getProperties())));
        }
        if (recordQuery.hasValidBoundingBox()) {
            query.append("location", new BasicDBObject("$within",
                    new BasicDBObject("$box", recordQuery.getBoxAsArray())));
        }
        if (recordQuery.hasValidFromDate()) {
            query.append("fromDate", new BasicDBObject("$gte", recordQuery.getFromDateParsed()));
        }
        if (recordQuery.hasValidToDate()) {
            query.append("toDate", new BasicDBObject("$lte", recordQuery.getToDateParsed()));
        }

        return query;
    }

    public String getAllObservedProperties() {

        DBCollection collection = configuration.getMeasurementRecordCollection();
        List<String> observedProperty = collection.distinct("observedProperty");

        return Joiner.on(", ").join(observedProperty);

    }
}
