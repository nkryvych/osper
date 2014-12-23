package ch.epfl.osper.metadata.wikireader;

import ch.epfl.osper.metadata.model.MeasurementLocationCache;
import ch.epfl.osper.metadata.model.mongodb.MongoDBConfiguration;
import ch.epfl.osper.metadata.wikireader.wikimodel.MediaWikiXMLReader;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.junit.Before;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

public class MeasurementRecordReaderTest {

    private MeasurementRecordReader subject;

    private MeasurementLocationReader measurementLocationReader;

    private MeasurementLocationCache cache;

    private MediaWikiXMLReader wikiXMLReader;

    private LocationEnrichmentService locationEnrichmentService;

    private MongoDBConfiguration mongoDBConfiguration = new MongoDBConfiguration();



    @Before
    public void init() throws Exception{
        cache = new MeasurementLocationCache();
        wikiXMLReader = new MediaWikiXMLReader();

        locationEnrichmentService = new LocationEnrichmentService(null);

        measurementLocationReader = new MeasurementLocationReader(cache, locationEnrichmentService, wikiXMLReader);
        subject = new MeasurementRecordReader(cache, wikiXMLReader, mongoDBConfiguration.getMeasurementRecordCollection());
    }

//    @Test
    public void readTest() throws Exception {
        InputStream locationStream = new FileInputStream("/Users/kryvych/Projects/osper/osper_metadata/wikireader/src/test/resources/MeasurementLocation.xml");
        int i = measurementLocationReader.readMeasurementLocations(locationStream);
        System.out.println("i = " + i);

        InputStream recordStream = new FileInputStream("/Users/kryvych/Projects/osper/osper_metadata/wikireader/src/test/resources/MeasurementRecord.xml");
        i = subject.readMeasurementRecords(recordStream);
        System.out.println("i = " + i);


        DBCollection collection = mongoDBConfiguration.getMeasurementRecordCollection();
        List observedProperty = collection.distinct("observedProperty");

    }


//    @Test
    public void mongoTest() throws Exception {
        InputStream locationStream = new FileInputStream("/Users/kryvych/Projects/osper/osper_metadata/wikireader/src/test/resources/MeasurementLocation.xml");

        DBCollection collection = mongoDBConfiguration.getMeasurementRecordCollection();
        // create an empty query
        BasicDBObject query = new BasicDBObject();

//        BasicDBObject fields = new BasicDBObject("title", false)
//                .append("_id", true)
//                .append("type", false)
//                .append("mesurementLocation", false)
//                .append("samplingFreq", false)
//                .append("serialNumber", false)
//                .append("fromDate", false)
//                .append("toDate", false)
//                .append("server", false)
//                .append("organisation", false)
//                .append("slopeAngle", false)
//                .append("aspect", false)
//                .append("location", false)
//                .append("observedProperty", true);


//        BasicDBObject fields = new BasicDBObject()
//                .append("_id", false)
//                .append("observedProperty", true);


        DBCursor cursor = collection.find(query);
        for (DBObject dbObject : cursor) {
            Object observedProperty = dbObject.get("observedProperty");
            System.out.println(observedProperty);
        }
    }
}