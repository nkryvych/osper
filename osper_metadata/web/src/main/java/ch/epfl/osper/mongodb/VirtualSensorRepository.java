package ch.epfl.osper.mongodb;

import ch.epfl.osper.metadata.model.VirtualSensor;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

/**
 * Created by kryvych on 26/01/15.
 */
public interface VirtualSensorRepository extends CrudRepository<VirtualSensor, BigInteger> {

    VirtualSensor findOneByName(String name);
}
