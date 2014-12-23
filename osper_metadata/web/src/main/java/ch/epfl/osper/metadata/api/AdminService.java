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

import javax.inject.Inject;
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

    @Inject
    public AdminService(MeasurementLocationReader measurementLocationReader, MeasurementRecordReader measurementRecordReader) {
        this.measurementLocationReader = measurementLocationReader;
        this.measurementRecordReader = measurementRecordReader;
    }

    @RequestMapping(value = "/updateMeasurementLocations", method = RequestMethod.GET)
    public
    @ResponseBody
    String updateMeasurementLocations() throws FileNotFoundException {


        InputStream fileStream = new FileInputStream("/Users/kryvych/Projects/osper/osper_metadata/wikireader/src/test/resources/MeasurementLocation.xml");

        int i = measurementLocationReader.readMeasurementLocations(fileStream);
        return String.valueOf(i);
    }

    @RequestMapping(value = "/reloadMeasurementRecords", method = RequestMethod.GET)
    public
    @ResponseBody
    String reloadMeasurementRecords() throws FileNotFoundException {

        updateMeasurementLocations();

        InputStream recordStream = new FileInputStream("/Users/kryvych/Projects/osper/osper_metadata/wikireader/src/test/resources/MeasurementRecord.xml");

        int i = measurementRecordReader.readMeasurementRecords(recordStream);
        return String.valueOf(i);


    }
}
