package ch.epfl.osper.metadata.services;

import ch.epfl.osper.metadata.dto.MeasurementRecordDTO;
import ch.epfl.osper.metadata.model.MeasurementLocation;
import ch.epfl.osper.metadata.model.MeasurementRecord;
import ch.epfl.osper.mongodb.MeasurementLocationRepository;
import ch.epfl.osper.mongodb.MeasurementRecordRepository;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Created by kryvych on 21/12/14.
 */
@Controller
@Scope("prototype")
public class QueryMongoDBService {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    final private MeasurementRecordRepository recordRepository;
    final private MeasurementLocationRepository locationRepository;


    @Inject
    public QueryMongoDBService(MeasurementRecordRepository recordRepository, MeasurementLocationRepository locationRepository) {
        this.recordRepository = recordRepository;
        this.locationRepository = locationRepository;
    }

    public Collection<MeasurementRecord> findMeasurementRecords(MetaDataQuery metaDataQuery, boolean unfolded) {
        //ToDo: method buildDTOs puts all results to memory. If functionality needed implement DB solution

        List<MeasurementRecord> records = recordRepository.findForMetaDataQuery(metaDataQuery);
        if (unfolded) {
            return records;
        }
        return buildDTOs(records);

    }

    public List<MeasurementRecord> findMeasurementRecordsByMeasurementLocation(String measurementLocationName) {
        return recordRepository.findByMeasurementLocationName(measurementLocationName);
    }

    public List<MeasurementLocation> findAllMeasurementLocations() {
        return locationRepository.findAllOrderedByDeployment();
    }

    public Collection<MeasurementRecord> findMeasurementRecordByLocation(String lat, String lon) {
        if (NumberUtils.isNumber(lat) && NumberUtils.isNumber(lon)) {
            List<MeasurementRecord> records = recordRepository.findByLocationPoint(new Point(NumberUtils.createDouble(lat), NumberUtils.createDouble(lon)));
            return buildDTOs(records);
        } else {
            logger.info("No valid lat or lon is passed");
            return Lists.newArrayList();
        }
    }

    private Collection<MeasurementRecord> buildDTOs(List<MeasurementRecord> records) {
        Map<String, MeasurementRecord> dbNameToRecord = Maps.newHashMap();
        for (MeasurementRecord record : records) {
            if (dbNameToRecord.containsKey(record.getDbTableName())) {
                MeasurementRecordDTO measurementRecordDTO = (MeasurementRecordDTO) dbNameToRecord.get(record.getDbTableName());
                measurementRecordDTO.addMeasurementRecord(record);
            } else {
                MeasurementRecordDTO measurementRecordDTO = new MeasurementRecordDTO(record);
                dbNameToRecord.put(record.getDbTableName(), measurementRecordDTO);

            }
        }
        return dbNameToRecord.values();
    }


}
