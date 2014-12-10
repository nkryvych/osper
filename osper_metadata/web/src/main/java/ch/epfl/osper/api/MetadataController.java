package ch.epfl.osper.api;

import ch.epfl.osper.export.json.JSONWriter;
import ch.epfl.osper.metadata.model.MockFactory;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by kryvych on 24/11/14.
 */
@Controller
@RequestMapping("/metadata")
public class MetadataController {

    private JSONWriter deploymentJSONWriter = new JSONWriter();



    @RequestMapping(value="/deployments", method=RequestMethod.GET)
    public @ResponseBody
    String getDeployments(@RequestParam(value="from", required=false) String fromDate, @RequestParam(value="to", required=false) String toDate) {


        String json = deploymentJSONWriter.writeDeployments(Sets.newHashSet(MockFactory.createDeployments()));
        return json;
    }

    @RequestMapping(value = "/bla", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    String getBla() {
//        String json = deploymentJSONWriter.writeDeployments(Sets.newHashSet(MockFactory.createDeployment(NAME, FROM, TO, LATITUDE, LONGITUDE, false)));
        String json = "value = \"/json/experiments\", method = RequestMethod.GET, produces = \"application/json\"";
        return json;
    }
}
