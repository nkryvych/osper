package ch.epfl.osper.metadata.services;

import ch.epfl.osper.metadata.model.MeasurementLocation;
import ch.epfl.osper.metadata.model.MeasurementRecord;
import ch.epfl.osper.mongodb.MeasurementLocationRepository;
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

    final private MeasurementRecordRepository recordRepository;
    final private MeasurementLocationRepository locationRepository;


    @Inject
    public QueryMongoDBService(MeasurementRecordRepository recordRepository, MeasurementLocationRepository locationRepository) {
        this.recordRepository = recordRepository;
        this.locationRepository = locationRepository;
    }

    public List<MeasurementRecord> findMeasurementRecords(MetaDataQuery metaDataQuery) {
        return recordRepository.findForMetaDataQuery(metaDataQuery);
    }

    public List<MeasurementRecord> findMeasurementRecordsByMeasurementLocation(String measurementLocationName) {
        return recordRepository.findByMeasurementLocationName(measurementLocationName);
    }

    public List<MeasurementLocation> findAllMeasurementLocations() {
        return locationRepository.findAllOrderedByDeployment();
    }

}
