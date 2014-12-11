package ch.epfl.osper.metadata.wikireader;

import ch.epfl.osper.metadata.model.Coordinates;
import ch.epfl.osper.metadata.wikireader.wikimodel.WikiPage;

/**
 * Created by kryvych on 11/12/14.
 */
public class WikiMesurementLocation extends WikiPageProxy {
    public WikiMesurementLocation(WikiPage page) {
        super(page);
    }

    public String getDeployment() {
        return getPropertyValue("|Deployment");
    }

    public String getLocation() {
        return getPropertyValue("|Location");
    }

    public Coordinates getCoordinates() {
        return null;
    }

    public int getAltitude() {
        String value = getPropertyValue("|Altitude");
        if (value!=null) {
            return Integer.parseInt(value);
        }
        //ToDo: what return if altitude is not specified
        return 0;
    }

    public String getExperiments() {
        return getPropertyValue("|experiments");
    }

    public String getCoordinateSystem() {
        return getPropertyValue("|Coordinate_System");
    }
}
