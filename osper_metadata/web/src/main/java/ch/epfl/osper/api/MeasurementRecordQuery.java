package ch.epfl.osper.api;


import org.apache.commons.lang.StringUtils;
import org.springframework.data.geo.Box;
import org.springframework.data.geo.Point;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by kryvych on 17/12/14.
 */
public class MeasurementRecordQuery {

    protected static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private String measurementLocationName;
    private Set<String> observedProperties;
    private String fromDate;
    private String toDate;
    private double minLat = Double.NaN;
    private double minLon = Double.NaN;
    private double maxLat = Double.NaN;
    private double maxLon = Double.NaN;

    private Set<String> properties = new HashSet<>();
    private Date fromDateParsed;
    private Date toDateParsed;


    public void setMinLon(double minLon) {
        this.minLon = minLon;
    }


    public void setMaxLat(double maxLat) {
        this.maxLat = maxLat;
    }

    public void setMinLat(double minLat) {
        this.minLat = minLat;
    }


    public void setMaxLon(double maxLon) {
        this.maxLon = maxLon;
    }


    public List<double[]> getBoxAsArray() {
        List<double[]> box = new ArrayList<>();
        box.add(new double[]{minLat, minLon}); //Starting coordinate
        box.add(new double[]{maxLat, maxLon}); // Ending coordinate
        return box;
    }

    public Box getBoundingBox() {
        return new Box(new Point(minLat, minLon), new Point(maxLat, maxLon));
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public Set<String> getObservedProperties() {
        return observedProperties;
    }


    public void setObservedProperties(Set<String> observedProperties) {
        this.observedProperties = observedProperties;
    }

    public Set<String> getProperties() {
        return properties;
    }

    public boolean hasValidBoundingBox() {
        return !Double.isNaN(minLat) && !Double.isNaN(minLon) && !Double.isNaN(maxLat) && !Double.isNaN(maxLon);
    }

    public void setProperties(Set<String> properties) {
        this.properties = properties;
    }

    public String getMeasurementLocationName() {
        return measurementLocationName;
    }

    public void setMeasurementLocationName(String measurementLocationName) {
        this.measurementLocationName = measurementLocationName;
    }

    public boolean hasValidFromDate() {
        if(StringUtils.isNotEmpty(fromDate)) {
            try {
                fromDateParsed = DATE_FORMAT.parse(fromDate);
                return true;
            } catch (ParseException e) {
                return false;
            }
        }
        return false;
    }

    public boolean hasValidToDate() {
        if(StringUtils.isNotEmpty(toDate)) {
            try {
                toDateParsed = DATE_FORMAT.parse(toDate);
                return true;
            } catch (ParseException e) {
                return false;
            }
        }
        return false;
    }

    public Date getFromDateParsed() {
        return fromDateParsed;
    }

    public Date getToDateParsed() {
        return toDateParsed;
    }

    @Override
    public String toString() {
        return "MeasurementRecordQuery{" +
                "measurementLocationName='" + measurementLocationName + '\'' +
                ", observedProperties=" + observedProperties +
                ", fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                ", minLat=" + minLat +
                ", minLon=" + minLon +
                ", maxLat=" + maxLat +
                ", maxLon=" + maxLon +
                ", properties=" + properties +
                '}';
    }
}
