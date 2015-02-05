package ch.epfl.osper.mongodb;

import ch.epfl.osper.metadata.model.MeasurementRecord;
import ch.epfl.osper.metadata.model.ObservedProperty;
import ch.epfl.osper.metadata.model.TaxonomyProperty;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by kryvych on 30/01/15.
 */
public interface TaxonomyRepository extends CrudRepository<TaxonomyProperty, BigInteger> {

    public TaxonomyProperty findByColumnName(String columnName);

    public List<TaxonomyProperty> findByTaxonomyName(String taxonomyName);
}
