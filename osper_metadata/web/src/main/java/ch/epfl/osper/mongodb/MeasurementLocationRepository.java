package ch.epfl.osper.mongodb;

import ch.epfl.osper.metadata.model.MeasurementLocation;
import org.springframework.data.geo.Box;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by kryvych on 09/01/15.
 */
public interface MeasurementLocationRepository extends CrudRepository<MeasurementLocation, BigInteger> {


    List<MeasurementLocation> findByLocationWithin(Box b);

    List<MeasurementLocation> findByLocationPointWithin(Box b);

    MeasurementLocation findByLocationName(String locationName);

    MeasurementLocation findByDeploymentName(String deploymentName);

}
