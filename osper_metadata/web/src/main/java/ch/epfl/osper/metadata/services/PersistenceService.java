package ch.epfl.osper.metadata.services;

import ch.epfl.osper.metadata.model.MeasurementLocation;
import ch.epfl.osper.metadata.model.MeasurementRecord;
import ch.epfl.osper.metadata.model.TaxonomyProperty;
import ch.epfl.osper.metadata.model.VirtualSensor;
import ch.epfl.osper.mongodb.MeasurementLocationRepository;
import ch.epfl.osper.mongodb.MeasurementRecordRepository;
import ch.epfl.osper.mongodb.TaxonomyRepository;
import ch.epfl.osper.mongodb.VirtualSensorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.geo.Box;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by kryvych on 09/01/15.
 */
@Service
public class PersistenceService {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private MeasurementLocationRepository measurementLocationRepository;
    private MeasurementRecordRepository recordRepository;
    private VirtualSensorRepository virtualSensorRepository;
    private TaxonomyRepository taxonomyRepository;

    @Inject
    public PersistenceService(MeasurementLocationRepository repository, MeasurementRecordRepository recordRepository, VirtualSensorRepository virtualSensorRepository, TaxonomyRepository taxonomyRepository) {
        this.measurementLocationRepository = repository;
        this.recordRepository = recordRepository;
        this.virtualSensorRepository = virtualSensorRepository;
        this.taxonomyRepository = taxonomyRepository;
    }

    public void writeMeasurementLocations(Set<MeasurementLocation> measurementLocations) {
        measurementLocationRepository.deleteAll();
        measurementLocationRepository.save(measurementLocations);
    }


    public void writeMeasurementRecords(Set<MeasurementRecord> records) {
        recordRepository.deleteAll();
        for (MeasurementRecord record : records) {
            MeasurementLocation measurementLocation = measurementLocationRepository.findByLocationName(record.getMeasurementLocationName());
            if (measurementLocation != null) {
                record.setMeasurementLocation(measurementLocation);
                VirtualSensor sensor = virtualSensorRepository.findOneByName(record.getDbTableName());
                if (sensor != null) {
                    record.setPublic(sensor.isPublic());
                    record.setInGSN(true);
                }
                recordRepository.save(record);
            } else {
                logger.info("No location found for " + record.getMeasurementLocationName());
            }
        }
    }

    public void writeVirtualSensors(Set<VirtualSensor> sensors) {
        virtualSensorRepository.deleteAll();
        virtualSensorRepository.save(sensors);
    }

    public void writeTaxonomy(Set<TaxonomyProperty> properties) {
        taxonomyRepository.deleteAll();
        taxonomyRepository.save(properties);
    }
}
