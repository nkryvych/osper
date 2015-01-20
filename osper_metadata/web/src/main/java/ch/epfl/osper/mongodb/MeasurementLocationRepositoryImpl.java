package ch.epfl.osper.mongodb;

import ch.epfl.osper.metadata.model.MeasurementLocation;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;

import javax.inject.Inject;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * Created by kryvych on 14/01/15.
 */
public class MeasurementLocationRepositoryImpl implements MeasurementLocationRepositoryCustom {
    private final MongoOperations operations;

    @Inject
    public MeasurementLocationRepositoryImpl(MongoOperations operations) {
        this.operations = operations;
    }


    @Override
    public List<MeasurementLocation> findAllOrderedByDeployment() {
        AggregationResults<MeasurementLocation> aggregate = operations.aggregate(newAggregation(Aggregation.sort(Sort.Direction.ASC, "deploymentName")), "measurement_location", MeasurementLocation.class);

        return aggregate.getMappedResults();
    }
}
