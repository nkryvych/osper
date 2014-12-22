package ch.epfl.osper.api;

import ch.epfl.osper.metadata.model.Coordinate;
import ch.epfl.osper.metadata.model.MockFactory;
import ch.epfl.osper.metadata.model.mongodb.MongoDBConfiguration;
import ch.epfl.osper.metadata.model.mongodb.QueryService;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by kryvych on 24/11/14.
 */
@Controller
@RequestMapping("/metadata")
public class MetadataController {

//    private JSONWriter deploymentJSONWriter = new JSONWriter();


    private QueryService queryService = new QueryService(new MongoDBConfiguration());

    public MetadataController() {
    }

//    @RequestMapping(value="/deployments", method=RequestMethod.GET)
//    public @ResponseBody
//    String getDeployments(@RequestParam(value="from", required=false) String fromDate, @RequestParam(value="to", required=false) String toDate) {
//
//
//        String json = deploymentJSONWriter.writeDeployments(Sets.newHashSet(MockFactory.createDeployments()));
//        return json;
//    }

    @RequestMapping(value = "/bla", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    String getBla() {
//        String json = deploymentJSONWriter.writeDeployments(Sets.newHashSet(MockFactory.createDeployment(NAME, FROM, TO, LATITUDE, LONGITUDE, false)));
        String json = "" +
                "value = \"/json/experiments\", method = RequestMethod.GET, produces = \"application/json\"";
        return json;
    }

    @RequestMapping(value="/sensors", method=RequestMethod.POST)
    public @ResponseBody
    String getSensors(@RequestBody Coordinate jo) {



        return jo.toString();
    }

    @RequestMapping(value = "/measurementRecords", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    String getMeasurementRecords() {
        return queryService.getAllMeasurementRecords();
    }

//    @RequestMapping(value = "/observedProperties", method = RequestMethod.GET, produces = "application/json")
//    public @ResponseBody
//    String getObservedProperties() {
//        return "";
//    }
}
