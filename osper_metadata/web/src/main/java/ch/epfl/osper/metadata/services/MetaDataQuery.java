package ch.epfl.osper.metadata.services;


import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.geo.Box;
import org.springframework.data.geo.Point;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by kryvych on 17/12/14.
 */
public class MetaDataQuery {

    protected static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private String measurementLocationName;
    private Set<String> observedProperties = Sets.newHashSet();
    private String fromDate;
    private String toDate;
    private double minLat = Double.NaN;
    private double minLon = Double.NaN;
    private double maxLat = Double.NaN;
    private double maxLon = Double.NaN;

    private double lat = Double.NaN;
    private double lon = Double.NaN;

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

    public Box getBoundingBox() {
        return new Box(new Point(minLat, minLon), new Point(maxLat, maxLon));
    }

    public Point getLocationPoint() {
        return new Point(lat, lon);
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


    public boolean hasValidBoundingBox() {
        return !Double.isNaN(minLat) && !Double.isNaN(minLon) && !Double.isNaN(maxLat) && !Double.isNaN(maxLon);
    }

    public boolean hasValidLocation() {
        return !Double.isNaN(lat) && !Double.isNaN(lon);
    }

    public String getMeasurementLocationName() {
        return measurementLocationName;
    }

    public void setMeasurementLocationName(String measurementLocationName) {
        this.measurementLocationName = measurementLocationName;
    }

    public boolean hasValidFromDate() {
        if (StringUtils.isNotEmpty(fromDate)) {
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
        if (StringUtils.isNotEmpty(toDate)) {
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
                '}';
    }
}
