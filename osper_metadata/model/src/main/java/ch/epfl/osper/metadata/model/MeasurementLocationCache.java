package ch.epfl.osper.metadata.model;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

import java.util.Map;

/**
 * Created by kryvych on 10/12/14.
 */
public class MeasurementLocationCache {
    private Map<String, String> locationToDBtable = Maps.newHashMap();

    private Multimap<String, MeasurementRecord> locationToRecords = ArrayListMultimap.create();


    public String putDBTableForLocation(String locationName, String dbTableName) {
        return locationToDBtable.put(locationName, dbTableName);
    }

    public boolean putMeasurementRecordForLocation(String locationName, MeasurementRecord measurementRecord) {
        return locationToRecords.put(locationName, measurementRecord);
    }

}
