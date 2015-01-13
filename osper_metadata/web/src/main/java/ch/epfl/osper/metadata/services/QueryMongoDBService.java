package ch.epfl.osper.metadata.services;

import ch.epfl.osper.metadata.model.MeasurementRecord;
import ch.epfl.osper.mongodb.MeasurementRecordRepository;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by kryvych on 21/12/14.
 */
@Controller
@Scope("prototype")
public class QueryMongoDBService {

    private MeasurementRecordRepository recordRepository;


    @Inject
    public QueryMongoDBService(MeasurementRecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    public List<MeasurementRecord> findMeasurementRecords(MetaDataQuery metaDataQuery) {
        return recordRepository.findForMetaDataQuery(metaDataQuery);
    }

    public List<MeasurementRecord> findMeasurementRecordsByMeasurementLocation(String measurementLocationName) {
        return recordRepository.findByMeasurementLocationName(measurementLocationName);
    }

}
