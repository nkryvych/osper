package ch.epfl.osper.metadata.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kryvych on 01/12/14.
 */
public class MeasurementRecord {
    private String id;

    private String measurementLocationId;

    private Collection<DeploymentPeriod> deploymentDates;

    private String samplingFrequency;

    private String experimentalMethod;

    private Collection<ObservedProperty> observedProperties;

    private Map<String, ObservedProperty> parameterNameToObservedProperty= new HashMap<String, ObservedProperty>();

    public MeasurementRecord(String id, String measurementLocationId, Collection<DeploymentPeriod> deploymentDates) {
        this.id = id;
        this.measurementLocationId = measurementLocationId;
        this.deploymentDates = deploymentDates;
    }

    public String getId() {
        return id;
    }

    public String getMeasurementLocationId() {
        return measurementLocationId;
    }

    public Collection<DeploymentPeriod> getDeploymentDates() {
        return deploymentDates;
    }

    public Collection<ObservedProperty> getObservedProperties() {
        return observedProperties;
    }

    public Collection<String> getObservedPropertyNames() {
        return parameterNameToObservedProperty.keySet();
    }

}
