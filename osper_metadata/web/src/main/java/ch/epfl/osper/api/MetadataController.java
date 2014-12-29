package ch.epfl.osper.api;

import ch.epfl.osper.metadata.model.Coordinate;
import ch.epfl.osper.metadata.model.MeasurementLocationCache;
import ch.epfl.osper.metadata.model.MockFactory;
import ch.epfl.osper.metadata.model.ObservedProperty;
import ch.epfl.osper.metadata.model.mongodb.MongoDBConfiguration;
import ch.epfl.osper.api.QueryService;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Collections;

/**
 * Created by kryvych on 24/11/14.
 */
@Controller
@RequestMapping("/metadata")
public class MetadataController {

    private QueryService queryService;

    private MeasurementLocationCache cache;

    @Inject
    public MetadataController(QueryService queryService, MeasurementLocationCache cache) {
        this.queryService = queryService;
        this.cache = cache;
    }

    @RequestMapping(value="/sensors", method=RequestMethod.POST)
    public @ResponseBody
    String getSensors(@RequestBody Coordinate jo) {

        return jo.toString();
    }

    @RequestMapping(value = "/measurementRecords", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    String getMeasurementRecords(MeasurementRecordQuery query) {
        System.out.println("query = " + query);
        return queryService.getAllMeasurementRecords(query);
    }

    @RequestMapping(value = "/observedProperties", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    String getObservedProperties() {
        return Joiner.on("\n").join(Collections2.transform(cache.getObservedProperties(), new Function<ObservedProperty, String>() {
            @Override
            public String apply(ObservedProperty observedProperty) {
                return observedProperty.getName() + " ; " +  observedProperty.getMedia();
            }
        }));
    }
}
