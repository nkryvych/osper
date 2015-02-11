package ch.epfl.osper.metadata.rest;

import ch.epfl.osper.metadata.gsnimport.GsnCsvImport;
import ch.epfl.osper.metadata.model.MeasurementLocation;
import ch.epfl.osper.metadata.model.MeasurementRecord;
import ch.epfl.osper.metadata.model.TaxonomyProperty;
import ch.epfl.osper.metadata.model.VirtualSensor;
import ch.epfl.osper.metadata.services.PersistenceService;
import ch.epfl.osper.metadata.taxonomy.TaxonomyReader;
import ch.epfl.osper.metadata.wikireader.MeasurementLocationReader;
import ch.epfl.osper.metadata.wikireader.MeasurementRecordReader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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

    private GsnCsvImport gsnCsvImport;

    private TaxonomyReader taxonomyReader;

    @Inject
    public AdminService(MeasurementLocationReader measurementLocationReader, MeasurementRecordReader measurementRecordReader, PersistenceService persistenceService, GsnCsvImport gsnCsvImport, TaxonomyReader taxonomyReader) {
        this.measurementLocationReader = measurementLocationReader;
        this.measurementRecordReader = measurementRecordReader;
        this.persistenceService = persistenceService;
        this.gsnCsvImport = gsnCsvImport;
        this.taxonomyReader = taxonomyReader;
    }

    @RequestMapping(value = "/updateMeasurementLocations",  method = RequestMethod.POST)
    public
    @ResponseBody
    String updateMeasurementLocations(@RequestParam(value="file", required=true) MultipartFile file) throws FileNotFoundException {

        System.out.println("AdminService.updateMeasurementLocations");


        InputStream fileStream = new FileInputStream("/Users/kryvych/Projects/osper/osper_metadata/wikireader/src/test/resources/MeasurementLocation.xml");

        Set<MeasurementLocation> measurementLocations = measurementLocationReader.readMeasurementLocations(fileStream);
        persistenceService.writeMeasurementLocations(measurementLocations);

        return String.valueOf(measurementLocations.size());
    }

    @RequestMapping(value = "/reloadMeasurementRecords", method = RequestMethod.GET)
    public
    @ResponseBody
    String reloadMeasurementRecords(@RequestParam String fileName) throws FileNotFoundException {
        System.out.println("AdminService.reloadMeasurementRecords");

//        InputStream recordStream = new FileInputStream("/Users/kryvych/Projects/osper/osper_metadata/wikireader/src/test/resources/MeasurementRecord.xml");
        InputStream recordStream = new FileInputStream(fileName);

        Set<MeasurementRecord> records = measurementRecordReader.parseMeasurementRecords(recordStream);
        int count = persistenceService.writeMeasurementRecords(records);
        return String.valueOf(count);


    }

    @RequestMapping(value = "/reloadTaxonomy", method = RequestMethod.GET)
    public
    @ResponseBody
    String reloadTaxonomy(@RequestParam String fileName) throws FileNotFoundException {

        System.out.println("AdminService.reloadVirtualSensors");

        Set<TaxonomyProperty> taxonomyProperties = taxonomyReader.readTaxonomy(fileName);
        persistenceService.writeTaxonomy(taxonomyProperties);

        return String.valueOf(taxonomyProperties.size());

    }

}
