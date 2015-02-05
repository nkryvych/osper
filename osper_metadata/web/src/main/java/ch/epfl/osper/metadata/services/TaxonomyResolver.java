package ch.epfl.osper.metadata.services;

import ch.epfl.osper.metadata.model.MeasurementRecord;
import ch.epfl.osper.metadata.model.ObservedProperty;
import ch.epfl.osper.metadata.model.TaxonomyProperty;
import ch.epfl.osper.mongodb.MeasurementRecordRepository;
import ch.epfl.osper.mongodb.TaxonomyRepository;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by kryvych on 29/01/15.
 */
@Service
public class TaxonomyResolver {

    private final TaxonomyRepository taxonomyRepository;
    private final MeasurementRecordRepository recordRepository;

    @Inject
    public TaxonomyResolver(TaxonomyRepository taxonomyRepository, MeasurementRecordRepository recordRepository) {
        this.taxonomyRepository = taxonomyRepository;
        this.recordRepository = recordRepository;
    }

    public Set<String> getColumnNamesForTerm(String term) {
        List<TaxonomyProperty> properties = taxonomyRepository.findByTaxonomyName(term);
        return Sets.newConcurrentHashSet(Collections2.transform(properties, new Function<TaxonomyProperty, String>() {
            @Override
            public String apply(TaxonomyProperty taxonomyProperty) {
                return taxonomyProperty.getColumnName();
            }
        }));
    }

    public Collection<String> getColumnNameInTableForTerm(String tableName, Collection<String> terms) {
        Set<String> columnNamesForTerm = Sets.newHashSet();
        for (String term : terms) {
            columnNamesForTerm.addAll(getColumnNamesForTerm(term));
        }

        List<MeasurementRecord> records = recordRepository.findByDbTableName(tableName);
        Set<String> columnNames = Sets.newHashSet();
        for (MeasurementRecord record : records) {
            for (ObservedProperty observedProperty : record.getObservedProperties()) {
                columnNames.add(observedProperty.getColumnName());
            }
        }
        return Sets.intersection(columnNamesForTerm, columnNames);

    }

    public String getTermForColumnName(String columnName) {
        TaxonomyProperty taxonomyProperty = taxonomyRepository.findByColumnName(columnName);
        return taxonomyProperty != null? taxonomyProperty.getTaxonomyName():"NA";

    }


}
