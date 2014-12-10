package ch.epfl.osper.export.json;

import ch.epfl.osper.metadata.model.Deployment;
import ch.epfl.osper.metadata.model.MeasurementLocation;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.TimeZone;

/**
 * Created by kryvych on 01/12/14.
 */
public class JSONWriter {

//    public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    static {
        DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    public String writeDeployments(Collection<Deployment> deployments) {

        JSONObject featureCollection = new JSONObject();
        try {
            JSONArray featureList = new JSONArray();
            // iterate through your list
            for (Deployment deployment : deployments) {
                // {"geometry": {"type": "Point", "coordinates": [-94.149, 36.33]}
                JSONObject point = new JSONObject();
                // construct a JSONArray from a string; can also use an array or list
                JSONArray coord = new JSONArray("[" + deployment.getCoordinates().getLongitude() + "," + deployment.getCoordinates().getLatitude() + "]");
                point.put("coordinates", coord);

                JSONObject feature = new JSONObject();
                point.put("type", "Point");

                feature.put("proerties", writeProperties(deployment));
                feature.put("geometry", point);

                featureList.put(feature);
                featureCollection.put("features", featureList);
                featureCollection.put("type", "featureCollection");
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        // output the result
        return featureCollection.toString();
    }

    public String writeMesurementLocations(Collection<MeasurementLocation> measurementLocations) {

        return null;
    }
    protected LinkedHashMap writeProperties(Deployment deployment) {
        LinkedHashMap properties =  new LinkedHashMap();
        properties.put("numberOfMeasurementLocations", deployment.getMeasurementLocations().size());

        properties.put("todate", deployment.getTo() != null?DATE_FORMAT.format(deployment.getTo()): null);
        properties.put("fromdate", deployment.getFrom() != null?DATE_FORMAT.format(deployment.getFrom()): null);

        properties.put("type", deployment.getClass().getSimpleName());
        properties.put("title", deployment.getTitle());

        return properties;
    }
}
