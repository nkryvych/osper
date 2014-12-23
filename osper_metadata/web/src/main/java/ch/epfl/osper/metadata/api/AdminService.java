package ch.epfl.osper.metadata.api;

import ch.epfl.osper.metadata.model.mongodb.MongoDBConfiguration;
import ch.epfl.osper.metadata.model.MeasurementLocationCache;
import ch.epfl.osper.metadata.wikireader.LocationEnrichmentService;
import ch.epfl.osper.metadata.wikireader.MeasurementLocationReader;
import ch.epfl.osper.metadata.wikireader.MeasurementRecordReader;
import ch.epfl.osper.metadata.wikireader.wikimodel.MediaWikiXMLReader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.UnknownHostException;

/**
 * Created by kryvych on 21/12/14.
 */
@Controller
@RequestMapping("/admin")
public class AdminService {

    private MeasurementLocationReader measurementLocationReader;

    private MeasurementRecordReader measurementRecordReader;

    private MeasurementLocationCache cache = new MeasurementLocationCache();

    private MediaWikiXMLReader  wikiXMLReader = new MediaWikiXMLReader();

    private LocationEnrichmentService locationEnrichmentService = new LocationEnrichmentService();

    private MongoDBConfiguration mongoDBConfiguration = new MongoDBConfiguration();


//    public AdminService(MeasurementLocationCache cache, MediaWikiXMLReader wikiXMLReader, LocationEnrichmentService locationEnrichmentService, MongoDBConfiguration mongoDBConfiguration) {
//        this.cache = cache;
//        this.wikiXMLReader = wikiXMLReader;
//        this.locationEnrichmentService = locationEnrichmentService;
//        this.mongoDBConfiguration = mongoDBConfiguration;
//    }

    @RequestMapping(value="/updateMeasurementLocations", method= RequestMethod.GET)
    public @ResponseBody
    String updateMeasurementLocations() throws FileNotFoundException {


        InputStream fileStream = new FileInputStream("/Users/kryvych/Projects/osper/osper_metadata/wikireader/src/test/resources/MeasurementLocation.xml");

        measurementLocationReader = new MeasurementLocationReader(cache, locationEnrichmentService, wikiXMLReader);

        int i = measurementLocationReader.readMeasurementLocations(fileStream);
        return String.valueOf(i);
    }

    @RequestMapping(value="/reloadMeasurementRecords", method= RequestMethod.GET)
    public @ResponseBody
    String reloadMeasurementRecords() throws FileNotFoundException {

        updateMeasurementLocations();

        try {

            measurementRecordReader = new MeasurementRecordReader(cache, wikiXMLReader, mongoDBConfiguration.getMeasurementRecordCollection());
            InputStream recordStream = new FileInputStream("/Users/kryvych/Projects/osper/osper_metadata/wikireader/src/test/resources/MeasurementRecord.xml");

            int i = measurementRecordReader.readMeasurementRecords(recordStream);
            return String.valueOf(i);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return String.valueOf(-1);
        }


    }
}
