package ch.epfl.osper.metadata.api;

import ch.epfl.osper.metadata.model.MeasurementLocation;
import ch.epfl.osper.metadata.model.MeasurementRecord;
import ch.epfl.osper.metadata.model.mongodb.MongoDBConfiguration;
import ch.epfl.osper.metadata.model.MeasurementLocationCache;
import ch.epfl.osper.metadata.services.MeasurementLocationService;
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
import java.util.Set;

/**
 * Created by kryvych on 21/12/14.
 */
@Controller
@RequestMapping("/admin")
public class AdminService {

    private MeasurementLocationReader measurementLocationReader;

    private MeasurementRecordReader measurementRecordReader;

    private MeasurementLocationService measurementLocationService;
    @Inject
    public AdminService(MeasurementLocationReader measurementLocationReader, MeasurementRecordReader measurementRecordReader, MeasurementLocationService measurementLocationService) {
        this.measurementLocationReader = measurementLocationReader;
        this.measurementRecordReader = measurementRecordReader;
        this.measurementLocationService = measurementLocationService;
    }

    @RequestMapping(value = "/updateMeasurementLocations", method = RequestMethod.GET)
    public
    @ResponseBody
    String updateMeasurementLocations() throws FileNotFoundException {

        System.out.println("AdminService.updateMeasurementLocations");

        InputStream fileStream = new FileInputStream("/Users/kryvych/Projects/osper/osper_metadata/wikireader/src/test/resources/MeasurementLocation.xml");

        Set<MeasurementLocation> measurementLocations = measurementLocationReader.readMeasurementLocations(fileStream);
        measurementLocationService.writeMeasurementLocations(measurementLocations);

        return String.valueOf(measurementLocations.size());
    }

    @RequestMapping(value = "/reloadMeasurementRecords", method = RequestMethod.GET)
    public
    @ResponseBody
    String reloadMeasurementRecords() throws FileNotFoundException {
        System.out.println("AdminService.reloadMeasurementRecords");
        updateMeasurementLocations();

        InputStream recordStream = new FileInputStream("/Users/kryvych/Projects/osper/osper_metadata/wikireader/src/test/resources/MeasurementRecord.xml");

        Set<MeasurementRecord> i = measurementRecordReader.parseMeasurementRecords(recordStream);
        return String.valueOf(i.size());


    }
}
