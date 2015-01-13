package ch.epfl.osper.mongodb;

import ch.epfl.osper.metadata.model.MeasurementLocation;
import ch.epfl.osper.metadata.model.MeasurementRecord;
import org.springframework.data.geo.Box;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by kryvych on 09/01/15.
 */
public interface MeasurementRecordRepository extends LocationAccessMeasurementRecordRepository, CrudRepository<MeasurementRecord, BigInteger> {

    List<MeasurementRecord> findByLocationPointWithin(Box box);


}
