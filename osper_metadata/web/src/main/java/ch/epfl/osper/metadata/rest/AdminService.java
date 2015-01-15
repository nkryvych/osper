package ch.epfl.osper.metadata.rest;

import ch.epfl.osper.metadata.model.MeasurementLocation;
import ch.epfl.osper.metadata.model.MeasurementRecord;
import ch.epfl.osper.metadata.services.PersistenceService;
import ch.epfl.osper.metadata.wikireader.MeasurementLocationReader;
import ch.epfl.osper.metadata.wikireader.MeasurementRecordReader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.io.IOException;
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

    @RequestMapping(value = "/updateMeasurementLocations",  method = RequestMethod.POST)
    public
    @ResponseBody
    String updateMeasurementLocations(@RequestParam(value="file", required=true) MultipartFile file) throws IOException {

        System.out.println("AdminService.updateMeasurementLocations");

//        InputStream fileStream = new FileInputStream("/Users/kryvych/Projects/osper/osper_metadata/wikireader/src/test/resources/MeasurementLocation.xml");

        Set<MeasurementLocation> measurementLocations = measurementLocationReader.readMeasurementLocations(file.getInputStream());
        persistenceService.writeMeasurementLocations(measurementLocations);

        return String.valueOf(measurementLocations.size());
    }

    @RequestMapping(value = "/updateMeasurementLocations",  method = RequestMethod.POST)
    public
    @ResponseBody
    String updateMeasurementRecords(@RequestParam(value="file", required=true) MultipartFile file) throws IOException {
        System.out.println("AdminService.reloadMeasurementRecords");

//        InputStream recordStream = new FileInputStream("/Users/kryvych/Projects/osper/osper_metadata/wikireader/src/test/resources/MeasurementRecord.xml");

        Set<MeasurementRecord> records = measurementRecordReader.parseMeasurementRecords(file.getInputStream());
        persistenceService.writeMeasurementRecords(records);
        return String.valueOf(records.size());


    }
}
