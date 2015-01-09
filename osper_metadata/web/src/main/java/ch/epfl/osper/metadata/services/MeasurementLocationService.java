package ch.epfl.osper.metadata.services;

import ch.epfl.osper.metadata.model.MeasurementLocation;
import ch.epfl.osper.mongodb.MeasurementLocationRepository;
import org.springframework.data.geo.Box;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

/**
 * Created by kryvych on 09/01/15.
 */
@Service
public class MeasurementLocationService {

    MeasurementLocationRepository repository;

    @Inject
    public MeasurementLocationService(MeasurementLocationRepository repository) {
        this.repository = repository;
    }

    public void writeMeasurementLocations(Set<MeasurementLocation> measurementLocations) {
        repository.deleteAll();
        repository.save(measurementLocations);
    }

    public List<MeasurementLocation> findLocationsWithinBox(Box box) {

        System.out.println("box = " + box);
        return repository.findByLocationWithin(box);
    }

    public List<MeasurementLocation> findLocationPointsWithinBox(Box box) {

        System.out.println("box = " + box);
        return repository.findByLocationPointWithin(box);
    }
}
