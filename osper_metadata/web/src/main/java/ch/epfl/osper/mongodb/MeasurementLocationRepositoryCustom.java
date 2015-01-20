package ch.epfl.osper.mongodb;

import ch.epfl.osper.metadata.model.MeasurementLocation;

import java.util.List;

/**
 * Created by kryvych on 14/01/15.
 */
public interface MeasurementLocationRepositoryCustom {

    List<MeasurementLocation> findAllOrderedByDeployment();

}
