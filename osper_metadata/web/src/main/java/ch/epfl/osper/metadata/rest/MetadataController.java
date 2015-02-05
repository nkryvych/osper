package ch.epfl.osper.metadata.rest;

import ch.epfl.osper.metadata.model.Coordinate;
import ch.epfl.osper.metadata.model.MeasurementLocation;
import ch.epfl.osper.metadata.model.MeasurementLocationCache;
import ch.epfl.osper.metadata.model.MeasurementRecord;
import ch.epfl.osper.metadata.services.GeoJSONConverter;
import ch.epfl.osper.metadata.services.MetaDataQuery;
import ch.epfl.osper.metadata.services.PersistenceService;
import ch.epfl.osper.metadata.services.QueryMongoDBService;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;

/**
 * Created by kryvych on 24/11/14.
 */
@Controller
@RequestMapping("/metadata")
public class MetadataController {

    private QueryMongoDBService queryService;

    private MeasurementLocationCache cache;

    private PersistenceService persistenceService;

    private GeoJSONConverter converter;

    @Inject
    public MetadataController(QueryMongoDBService queryService, MeasurementLocationCache cache, PersistenceService persistenceService, GeoJSONConverter converter) {
        this.queryService = queryService;
        this.cache = cache;
        this.persistenceService = persistenceService;
        this.converter = converter;
    }

    @RequestMapping(value = "/measurementRecords", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    String getMeasurementRecords(MetaDataQuery query) {
        System.out.println("query = " + query);

        Collection<MeasurementRecord> allMeasurementRecords = queryService.findMeasurementRecords(query, false);

        return converter.convertMeasurementRecords(allMeasurementRecords, false);
    }

    @RequestMapping(value = "/measurementRecords/unfolded", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    String getMeasurementRecordsUnfolded(MetaDataQuery query) {
        System.out.println("query = " + query);

        Collection<MeasurementRecord> allMeasurementRecords = queryService.findMeasurementRecords(query, true);

        return converter.convertMeasurementRecords(allMeasurementRecords, false);
    }

    @RequestMapping(value = "/measurementRecords/measurementLocation/{measurementLocationName}", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    String getMeasurementRecordsForMeasurementLocation(@PathVariable String measurementLocationName) {
        System.out.println("measurementLocationName = " + measurementLocationName);
        List<MeasurementRecord> allMeasurementRecords = queryService.findMeasurementRecordsByMeasurementLocation(measurementLocationName);

        return converter.convertMeasurementRecords(allMeasurementRecords, false);
    }

@RequestMapping(value = "/measurementRecords/{dbTableName}", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    String getMeasurementRecordsForDbTableName(@PathVariable String dbTableName) {
    System.out.println("dbTableName = " + dbTableName);
        Collection<MeasurementRecord> allMeasurementRecords = queryService.findMeasurementRecordsForVirtualSensor(dbTableName);

        return converter.convertMeasurementRecords(allMeasurementRecords, true);
    }

    @RequestMapping(value = "/measurementLocations", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    String getMeasurementLocations(MetaDataQuery query) {

        System.out.println("query = " + query);

        List<MeasurementLocation> result = queryService.findAllMeasurementLocations();
        Gson gson = new Gson();
        return gson.toJson(result);

    }

    @RequestMapping(value = "measurementRecords/position", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    String getMeasurementRecordsForLocation(@RequestParam String lat, @RequestParam String lon) {

        Collection<MeasurementRecord> result = queryService.findMeasurementRecordByLocation(lat, lon);

        return converter.convertMeasurementRecords(result, false);

    }
}
