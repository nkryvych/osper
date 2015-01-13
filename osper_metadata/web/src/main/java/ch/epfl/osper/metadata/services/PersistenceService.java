package ch.epfl.osper.metadata.services;

import ch.epfl.osper.metadata.model.MeasurementLocation;
import ch.epfl.osper.metadata.model.MeasurementRecord;
import ch.epfl.osper.mongodb.MeasurementLocationRepository;
import ch.epfl.osper.mongodb.MeasurementRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.geo.Box;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
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

    @Inject
    public PersistenceService(MeasurementLocationRepository repository, MeasurementRecordRepository recordRepository) {
        this.measurementLocationRepository = repository;
        this.recordRepository = recordRepository;
    }

    public void writeMeasurementLocations(Set<MeasurementLocation> measurementLocations) {
        measurementLocationRepository.deleteAll();
        measurementLocationRepository.save(measurementLocations);
    }

    public List<MeasurementLocation> findLocationsWithinBox(Box box) {

        System.out.println("box = " + box);
        return measurementLocationRepository.findByLocationWithin(box);
    }

    public List<MeasurementLocation> findLocationPointsWithinBox(Box box) {

        System.out.println("box = " + box);
        return measurementLocationRepository.findByLocationPointWithin(box);
    }

    public void writeMeasurementRecords(Set<MeasurementRecord> records) {
        recordRepository.deleteAll();
        for (MeasurementRecord record : records) {
            MeasurementLocation measurementLocation = measurementLocationRepository.findByLocationName(record.getMeasurementLocationName());
            if (measurementLocation != null) {
                record.setMeasurementLocation(measurementLocation);
                recordRepository.save(record);
            } else {
                logger.info("No location found for " + record.getMeasurementLocationName());
            }
        }
    }
}
