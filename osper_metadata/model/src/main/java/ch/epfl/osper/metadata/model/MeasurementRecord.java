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

    private String serialNumber;

    private DeploymentPeriod deploymentDates;

    private String samplingFrequency;

    private String experimentalMethod;

    private Collection<ObservedProperty> observedProperties;

    private Map<String, ObservedProperty> parameterNameToObservedProperty= new HashMap<String, ObservedProperty>();

    public MeasurementRecord(String id, String measurementLocationId, String serialNumber) {
        this.id = id;
        this.measurementLocationId = measurementLocationId;
        this.serialNumber = serialNumber;
    }

    public String getId() {
        return id;
    }

    public String getMeasurementLocationId() {
        return measurementLocationId;
    }

    public DeploymentPeriod getDeploymentDates() {
        return deploymentDates;
    }

    public Collection<ObservedProperty> getObservedProperties() {
        return observedProperties;
    }

    public Collection<String> getObservedPropertyNames() {
        return parameterNameToObservedProperty.keySet();
    }

    public String getSamplingFrequency() {
        return samplingFrequency;
    }

    public String getExperimentalMethod() {
        return experimentalMethod;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MeasurementRecord that = (MeasurementRecord) o;

        if (!measurementLocationId.equals(that.measurementLocationId)) return false;
        if (!observedProperties.equals(that.observedProperties)) return false;
        if (serialNumber != null ? !serialNumber.equals(that.serialNumber) : that.serialNumber != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = measurementLocationId.hashCode();
        result = 31 * result + (serialNumber != null ? serialNumber.hashCode() : 0);
        result = 31 * result + observedProperties.hashCode();
        return result;
    }
}
