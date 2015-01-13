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

    @RequestMapping(value="/sensors", method=RequestMethod.POST)
    public @ResponseBody
    String getSensors(@RequestBody Coordinate jo) {

        return jo.toString();
    }

    @RequestMapping(value = "/measurementRecords", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    String getMeasurementRecords(MetaDataQuery query) {
        System.out.println("query = " + query);

        List<MeasurementRecord> allMeasurementRecords = queryService.findMeasurementRecords(query);

        return converter.convertMeasurementRecords(allMeasurementRecords);
    }

    @RequestMapping(value = "/measurementRecords/measurementLocation/{measurementLocationName}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    String getMeasurementRecordsForMeasurementLocation(@PathVariable String measurementLocationName) {
        System.out.println("measurementLocationName = " + measurementLocationName);
        List<MeasurementRecord> allMeasurementRecords = queryService.findMeasurementRecordsByMeasurementLocation(measurementLocationName);

        return converter.convertMeasurementRecords(allMeasurementRecords);
    }

    @RequestMapping(value = "/measurementLocations", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    String getMeasurementLocations(MetaDataQuery query) {

        System.out.println("query = " + query);

        if (query.hasValidBoundingBox()) {
            List<MeasurementLocation> result = persistenceService.findLocationPointsWithinBox(query.getBoundingBox());
            Gson gson = new Gson();

            return gson.toJson(result);
        }
        return "Wrong query";
    }
}