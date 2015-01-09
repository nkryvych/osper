package ch.epfl.osper.metadata.wikireader;

import ch.epfl.osper.metadata.model.Coordinate;
import ch.epfl.osper.metadata.wikireader.wikimodel.WikiPage;
import com.google.common.base.CharMatcher;
import org.apache.commons.lang.StringUtils;

/**
 * Created by kryvych on 11/12/14.
 */
public class WikiMesurementLocation extends WikiPageProxy {

    private static final CharMatcher DIGIT_MATCHER = CharMatcher.DIGIT.or(CharMatcher.anyOf("."));

    public WikiMesurementLocation(WikiPage page) {
        super(page);
    }

    public String getDeployment() {
        return getPropertyValue("|Deployment");
    }

    public String getLocation() {
        return getPropertyValue("|Location");
    }

    public Coordinate getCoordinates() {
        if (getPropertyValue("|Coordinates") == null) {
            logger.info("No coordinates specified for MeasurementLocation " + getTitle());
            return null;
        }
        String[] coordinateStrings = getPropertyValue("|Coordinates").split(",");
        if (coordinateStrings.length != 2 || StringUtils.isEmpty(coordinateStrings[1]) ||
                StringUtils.isEmpty(coordinateStrings[0])) {
            logger.info("No valid coordinates specified for MeasurementLocation " + getTitle());
            return null;
        }

        double latitude = 0;
        double longitude = 0;
        try {
            latitude = parseCoordinate(coordinateStrings[0].trim());
            longitude = parseCoordinate(coordinateStrings[1].trim());
        } catch (NumberFormatException e) {
            logger.info("No coordinates specified for MeasurementLocation " + getTitle());
            return null;
        }

        if (coordinateStrings[0].trim().endsWith("S") || coordinateStrings[0].trim().endsWith("s")) {
            latitude *= -1;
        }

        if (coordinateStrings[1].trim().endsWith("W") || coordinateStrings[1].trim().endsWith("w")) {
            longitude *= -1;
        }
        return new Coordinate(latitude, longitude);

    }

    public double[] getLocationPoint() {
        Coordinate coordinates = getCoordinates();
        return new double[] {coordinates.getLatitude(), coordinates.getLongitude()};
    }

    protected double parseCoordinate(String coordinateString) throws NumberFormatException{
        return Double.parseDouble(DIGIT_MATCHER.retainFrom(coordinateString));
    }

    public int getAltitude() {
        String value = getPropertyValue("|Altitude");
        if (value != null) {
            return Integer.parseInt(DIGIT_MATCHER.retainFrom(value));
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
