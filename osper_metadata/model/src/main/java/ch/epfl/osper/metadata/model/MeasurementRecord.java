package ch.epfl.osper.metadata.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;

/**
 * Created by kryvych on 01/12/14.
 */
public class MeasurementRecord {
    @Id
    private BigInteger id;

    private String wikiId;

    private String measurementLocationName;

    @DBRef
    private MeasurementLocation measurementLocation;

    private String serialNumber;

    private Date fromDate;
    private Date toDate;
    private String samplingFrequency;

    private String server;
    private String organisation;

    private Collection<ObservedProperty> observedProperties;

    private String dbTableName;


    private MeasurementRecord(String wikiId, String measurementLocationName, String serialNumber, Date fromDate, Date toDate, String samplingFrequency, String server, String organisation, Collection<ObservedProperty> observedProperties, String dbTableName) {
        this.wikiId = wikiId;
        this.measurementLocationName = measurementLocationName;
        this.serialNumber = serialNumber;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.samplingFrequency = samplingFrequency;
        this.server = server;
        this.organisation = organisation;
        this.observedProperties = observedProperties;
        this.dbTableName = dbTableName;
    }

    private MeasurementRecord() {
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public BigInteger getId() {
        return id;
    }

    public String getWikiId() {
        return wikiId;
    }

    public String getMeasurementLocationName() {
        return measurementLocationName;
    }

    public MeasurementLocation getMeasurementLocation() {
        return measurementLocation;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getSamplingFrequency() {
        return samplingFrequency;
    }

    public String getServer() {
        return server;
    }

    public String getOrganisation() {
        return organisation;
    }

    public Collection<ObservedProperty> getObservedProperties() {
        return observedProperties;
    }

    public String getDbTableName() {
        return dbTableName;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public void setMeasurementLocation(MeasurementLocation measurementLocation) {
        this.measurementLocation = measurementLocation;
    }

    public static class Builder {
        private String wikiId;
        private String measurementLocationName;
        private String serialNumber;
        private DeploymentPeriod deploymentDates;
        private String samplingFrequency;
        private String server;
        private String organisation;
        private Collection<ObservedProperty> observedProperties;
        private String dbTableName;
        private Date fromDate;
        private Date toDate;

        public Builder wikiId(String wikiId) {
            this.wikiId = wikiId;
            return this;
        }

        public Builder measurementLocatioName(String measurementLocationId) {
            this.measurementLocationName = measurementLocationId;
            return this;
        }

        public Builder serialNumber(String serialNumber) {
            this.serialNumber = serialNumber;
            return this;
        }

        public Builder deploymentDates(DeploymentPeriod deploymentDates) {
            this.deploymentDates = deploymentDates;
            return this;
        }

        public Builder samplingFrequency(String samplingFrequency) {
            this.samplingFrequency = samplingFrequency;
            return this;
        }

        public Builder server(String server) {
            this.server = server;
            return this;
        }

        public Builder organisation(String organisation) {
            this.organisation = organisation;
            return this;
        }

        public Builder observedProperties(Collection<ObservedProperty> observedProperties) {
            this.observedProperties = observedProperties;
            return this;
        }

        public Builder dbTableName(String dbTableName) {
            this.dbTableName = dbTableName;
            return this;
        }

        public Builder fromDate(Date fromDate) {
            this.fromDate = fromDate;
            return this;
        }

        public Builder toDate(Date toDate) {
            this.toDate = toDate;
            return this;
        }

        public MeasurementRecord createMeasurementRecord() {
            return new MeasurementRecord(wikiId, measurementLocationName, serialNumber, fromDate, toDate, samplingFrequency, server, organisation, observedProperties, dbTableName);
        }
    }
}
