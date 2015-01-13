package ch.epfl.osper.metadata.rest;

import ch.epfl.osper.metadata.model.MeasurementLocation;
import ch.epfl.osper.metadata.model.MeasurementRecord;
import ch.epfl.osper.metadata.services.PersistenceService;
import ch.epfl.osper.metadata.wikireader.MeasurementLocationReader;
import ch.epfl.osper.metadata.wikireader.MeasurementRecordReader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Set;

/**
 * Created by kryvych on 21/12/14.
 */
@Controller
@RequestMapping("/admin")
public class AdminService {

    private MeasurementLocationReader measurementLocationReader;

    private MeasurementRecordReader measurementRecordReader;

    private PersistenceService persistenceService;
    @Inject
    public AdminService(MeasurementLocationReader measurementLocationReader, MeasurementRecordReader measurementRecordReader, PersistenceService persistenceService) {
        this.measurementLocationReader = measurementLocationReader;
        this.measurementRecordReader = measurementRecordReader;
        this.persistenceService = persistenceService;
    }

    @RequestMapping(value = "/updateMeasurementLocations", method = RequestMethod.GET)
    public
    @ResponseBody
    String updateMeasurementLocations() throws FileNotFoundException {

        System.out.println("AdminService.updateMeasurementLocations");

        InputStream fileStream = new FileInputStream("/Users/kryvych/Projects/osper/osper_metadata/wikireader/src/test/resources/MeasurementLocation.xml");

        Set<MeasurementLocation> measurementLocations = measurementLocationReader.readMeasurementLocations(fileStream);
        persistenceService.writeMeasurementLocations(measurementLocations);

        return String.valueOf(measurementLocations.size());
    }

    @RequestMapping(value = "/reloadMeasurementRecords", method = RequestMethod.GET)
    public
    @ResponseBody
    String reloadMeasurementRecords() throws FileNotFoundException {
        System.out.println("AdminService.reloadMeasurementRecords");
//        updateMeasurementLocations();

        InputStream recordStream = new FileInputStream("/Users/kryvych/Projects/osper/osper_metadata/wikireader/src/test/resources/MeasurementRecord.xml");

        Set<MeasurementRecord> records = measurementRecordReader.parseMeasurementRecords(recordStream);
        persistenceService.writeMeasurementRecords(records);
        return String.valueOf(records.size());


    }
}
