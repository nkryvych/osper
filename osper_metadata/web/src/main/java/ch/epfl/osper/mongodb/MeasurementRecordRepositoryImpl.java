package ch.epfl.osper.mongodb;

import ch.epfl.osper.metadata.model.MeasurementLocation;
import ch.epfl.osper.metadata.model.MeasurementRecord;
import ch.epfl.osper.metadata.services.MetaDataQuery;
import com.google.common.collect.Lists;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;

import javax.inject.Inject;
import java.util.*;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * Created by kryvych on 12/01/15.
 */
public class MeasurementRecordRepositoryImpl implements LocationAccessMeasurementRecordRepository {
    private final MongoOperations operations;

    private final MeasurementLocationRepository measurementLocationRepository;

    @Inject
    public MeasurementRecordRepositoryImpl(MongoOperations operations, MeasurementLocationRepository measurementLocationRepository) {
        this.operations = operations;
        this.measurementLocationRepository = measurementLocationRepository;
    }


    @Override
    public List<MeasurementRecord> findByMeasurementLocationName(String name) {
        MeasurementLocation measurementLocation = measurementLocationRepository.findByLocationName(name);
        if (measurementLocation == null) {
            return Lists.newArrayList();
        }
        return operations.find(Query.query(where("measurementLocation.$id").is(measurementLocation.getId())), MeasurementRecord.class);
    }

    @Override
    public List<MeasurementRecord> findForTimePeriod(Date from, Date to) {
        List<Criteria> andCriteriaList = new ArrayList<Criteria>();

        if (from != null) {
            Criteria criteria = where("fromDate").gte(from);
            andCriteriaList.add(criteria);
        }
        if( to != null) {
            Criteria criteria = where("toDate").lte(to);
            andCriteriaList.add(criteria);
        }

        return operations.find(Query.query(new Criteria().andOperator(andCriteriaList.toArray(new Criteria[andCriteriaList.size()]))), MeasurementRecord.class);
    }

    @Override
    public List<MeasurementRecord> findByObservedPropertyNames(Collection<String> propertyNames) {

        Query query = Query.query(where("observedProperties.name").in(propertyNames));
        return operations.find(query, MeasurementRecord.class);
    }

    @Override
    public List<MeasurementRecord> findByCriteria(Criteria criteria) {
        return operations.find(new Query(criteria), MeasurementRecord.class);
    }

    @Override
    public List<MeasurementRecord> findForMetaDataQuery(MetaDataQuery metaDataQuery) {
        Criteria criteria = buildMultiParameterCriteria(metaDataQuery);
        Query query = new Query(criteria);

        Set<String> observedProperties = metaDataQuery.getObservedProperties();
        if(!observedProperties.isEmpty()) {
            TextCriteria textCriteria = TextCriteria.forDefaultLanguage().
            matchingAny(observedProperties.toArray(new String[observedProperties.size()]));
            query.addCriteria(textCriteria);
        }
        return operations.find(query, MeasurementRecord.class);
    }

    @Override
    public List<MeasurementLocation> findMeasurementLocationsForMetaDataQuery(MetaDataQuery metaDataQuery) {
        Criteria criteria = buildMultiParameterCriteria(metaDataQuery);
        Query query = new Query(criteria);

        Set<String> observedProperties = metaDataQuery.getObservedProperties();
        if(!observedProperties.isEmpty()) {
            TextCriteria textCriteria = TextCriteria.forDefaultLanguage().
                    matchingAny(observedProperties.toArray(new String[observedProperties.size()]));
            query.addCriteria(textCriteria);
        }
        return operations.find(query, MeasurementLocation.class);
    }

    protected Criteria buildMultiParameterCriteria(MetaDataQuery metaDataQuery) {
        List<Criteria> criteriaList = new ArrayList<Criteria>();
        if (metaDataQuery.hasValidBoundingBox()) {
            Criteria criteria = where("locationPoint").within(metaDataQuery.getBoundingBox());
            criteriaList.add(criteria);
        }
        if (metaDataQuery.hasValidFromDate()) {
            Criteria criteria = where("fromDate").lte(metaDataQuery.getFromDateParsed());
            criteriaList.add(criteria);
        }
        if(metaDataQuery.hasValidToDate()) {
            Criteria criteria = where("toDate").gte(metaDataQuery.getToDateParsed());
            criteriaList.add(criteria);
        }

        Criteria result = new Criteria();
        if(!criteriaList.isEmpty()) {
            result.andOperator(criteriaList.toArray(new Criteria[criteriaList.size()]));
        }


        return result;
    }

}
