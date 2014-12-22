package ch.epfl.osper.metadata.model;

import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

/**
 * Created by kryvych on 01/12/14.
 */
public class MeasurementLocation extends Location {

    private String id;

    private String locationName;

    private String deploymentName;

    private String tableName;

    private Set<String> observedProperties = Sets.newHashSet();

    private Set<MeasurementRecord> records = Sets.newHashSet();

    private Multimap<MeasurementRecord, DeploymentPeriod> sensorDeploymentTimes;

    //ToDo: what is the earliest Date and Date for for ongoing measurements
    private DeploymentPeriod deploymentPeriod = new DeploymentPeriod(null, null);


    public MeasurementLocation(String id, String title, Coordinate coordinate, String locationName) {
        super(id, title, coordinate);
        this.locationName = locationName;
    }

    public String getDeploymentName() {
        return deploymentName;
    }

    public void addMeasurementRecord(MeasurementRecord measurementRecord) {
        records.add(measurementRecord);
        observedProperties.addAll(measurementRecord.getObservedPropertyNames());
        deploymentPeriod.merge(measurementRecord.getDeploymentDates());
    }

    public Collection<String> getObservedProperties() {
        return observedProperties;
    }

    public String getTableName() {
        return tableName;
    }

    public void setDeploymentName(String deploymentName) {
        this.deploymentName = deploymentName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getLocationName() {
        return locationName;
    }

}
