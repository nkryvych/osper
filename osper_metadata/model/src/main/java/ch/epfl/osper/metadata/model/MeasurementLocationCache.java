package ch.epfl.osper.metadata.model;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * Created by kryvych on 10/12/14.
 */
public class MeasurementLocationCache {
    private Map<String, String> locationToDBtable = Maps.newHashMap();

    private Multimap<String, MeasurementRecord> locationToRecords = ArrayListMultimap.create();

    private Map<String, MeasurementLocation> locationNameToLocation = Maps.newHashMap();

    private Set<String> observedProperties = Sets.newHashSet();

    public String putDBTableForLocation(String locationName, String dbTableName) {
        return locationToDBtable.put(locationName, dbTableName);
    }

    public boolean putMeasurementRecordForLocation(String locationName, MeasurementRecord measurementRecord) {
        return locationToRecords.put(locationName, measurementRecord);
    }

    public Location putLocation(MeasurementLocation location) {
        return locationNameToLocation.put(location.getLocationName(), location);
    }

    public MeasurementLocation getLocation(String locationName) {
        return locationNameToLocation.get(locationName);
    }

    public void addObservedProperty(String observedProperty) {
        observedProperties.add(observedProperty);
    }

    public Set<String> getObservedProperties() {
        return Collections.unmodifiableSet(observedProperties);
    }
}
