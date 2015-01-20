package ch.epfl.osper.mongodb;

import ch.epfl.osper.metadata.model.MeasurementLocation;
import ch.epfl.osper.metadata.model.MeasurementRecord;
import ch.epfl.osper.metadata.services.MetaDataQuery;
import org.springframework.data.geo.Box;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by kryvych on 12/01/15.
 */
public interface LocationAccessMeasurementRecordRepository {

    List<MeasurementRecord> findByMeasurementLocationName(String name);

    List<MeasurementRecord> findForTimePeriod(Date from, Date to);

    List<MeasurementRecord> findByObservedPropertyNames(Collection<String> properties);

    List<MeasurementRecord> findByCriteria(Criteria criteria);

    List<MeasurementRecord> findForMetaDataQuery(MetaDataQuery metaDataQuery);

    List<MeasurementLocation> findMeasurementLocationsForMetaDataQuery(MetaDataQuery metaDataQuery);
}
