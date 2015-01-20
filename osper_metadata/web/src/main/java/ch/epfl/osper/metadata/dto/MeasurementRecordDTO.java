package ch.epfl.osper.metadata.dto;

import ch.epfl.osper.metadata.model.MeasurementLocation;
import ch.epfl.osper.metadata.model.MeasurementRecord;
import ch.epfl.osper.metadata.model.ObservedProperty;
import com.google.common.collect.Sets;
import org.springframework.data.geo.Point;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

/**
 * Created by kryvych on 20/01/15.
 */
public class MeasurementRecordDTO extends MeasurementRecord{

    private MeasurementRecord record;

    private Date fromDate;
    private Date toDate;
    private Set<ObservedProperty> observedProperties;

    public MeasurementRecordDTO(MeasurementRecord record) {
        this.record = record;
        this.fromDate = record.getFromDate();
        this.toDate = record.getToDate();
        this.observedProperties = Sets.newHashSet(record.getObservedProperties());
    }

    public boolean addMeasurementRecord(MeasurementRecord record) {
        if (!this.record.getDbTableName().equals(record.getDbTableName())) {
            return false;
        }
        if (fromDate != null &&  record.getFromDate()!=null && this.fromDate.after(record.getFromDate())) {
            this.fromDate = record.getFromDate();
        }
        if (toDate != null &&  record.getToDate()!=null && this.toDate.before(record.getToDate())) {
            this.toDate = record.getToDate();
        }

        observedProperties.addAll(record.getObservedProperties());
        return true;
    }

    public String getMeasurementLocationName() {
        return record.getMeasurementLocationName();
    }

    public String getSerialNumber() {
        return record.getSerialNumber();
    }

    public String getServer() {
        return record.getServer();
    }

    public String getOrganisation() {
        return record.getOrganisation();
    }

    public Collection<ObservedProperty> getObservedProperties() {
        return observedProperties;
    }

    public String getDbTableName() {
        return record.getDbTableName();
    }

    public Date getFromDate() {
        return fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public String getTitle() {
        return record.getTitle();
    }

    public Point getLocationPoint() {
        return record.getLocationPoint();
    }

    public MeasurementLocation getMeasurementLocation() {
        return record.getMeasurementLocation();
    }

    public String getEmail() {
        return record.getEmail();
    }
}
