package ch.epfl.osper.metadata.wikireader;

import ch.epfl.osper.metadata.model.MeasurementRecord;
import ch.epfl.osper.metadata.wikireader.wikimodel.MediaWikiXMLReader;
import ch.epfl.osper.metadata.wikireader.wikimodel.WikiPage;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

/**
 * Created by kryvych on 21/12/14.
 */
@Named
public class MeasurementRecordReader {
    protected static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private MediaWikiXMLReader wikiXMLReader;

    @Inject
    public MeasurementRecordReader( MediaWikiXMLReader wikiXMLReader) {
        this.wikiXMLReader = wikiXMLReader;
    }

//    public int readMeasurementRecords(InputStream measurementRecordsInputStream) {
//        int count = 0;
//        List<WikiPage> pages = wikiXMLReader.parse(measurementRecordsInputStream);
//
//        //remove all records from collection
//        recordCollection.remove(new BasicDBObject());
//
//        for (WikiPage page : pages) {
//            WikiMesurementRecord record = new WikiMesurementRecord(page);
//
//            MeasurementLocation location = cache.getLocation(record.getLocation());
//            if (location == null) {
//                logger.info("No location found for " + record.getLocation());
//                continue;
//            }
//
//            if (record.getObservedProperties().size() == 0) {
//                logger.info("No observed properties found for " + record.getLocation());
//                continue;
//            }
//
//            Coordinate coordinate = location.getCoordinate();
//
//            BasicDBObject document = new BasicDBObject("title", record.getTitle())
//                    .append("type", "MeasurementRecord")
//                    .append("measurementLocation", record.getLocation())
//                    .append("measurementLocationTitle", location.getTitle())
//                    .append("samplingFreq", record.getSamplingFreq())
//                    .append("serialNumber", record.getSerialNo())
//                    .append("server", record.getServerName())
//                    .append("dbTableName", record.getDBaseTableName())
//                    .append("organisation", record.getOrganization())
//                    .append("fromDate", record.getFromDate() != null ? DATE_FORMAT.format(record.getFromDate()) : null)
//                    .append("toDate", record.getToDate() != null ? DATE_FORMAT.format(record.getToDate()) : DATE_FORMAT.format(new Date()))
////                    .append("fromDate", record.getFromDate() != null ? record.getFromDate() : null)
////                    .append("toDate", record.getToDate() != null ? record.getToDate() : null)
//                    .append("slopeAngle", location.getSlope())
//                    .append("aspect", location.getAspect())
//                    .append("elevation", location.getElevation())
//                    .append("location", new BasicDBObject("type", "Point")
//                            .append("coordinates", Lists.newArrayList(coordinate.getLatitude(), coordinate.getLongitude())));
//
//            Set<String> properties = new HashSet<String>();
//            for (ObservedProperty observedProperty : record.getObservedProperties()) {
//                String name = observedProperty.getName();
//                if (StringUtils.isNotEmpty(name)) {
//                    properties.add(observedProperty.getMedia() + " : "  + name);
//                    cache.addObservedProperty(observedProperty);
//                }
//            }
//
//            document.append("observedProperty", Lists.newArrayList(properties));
//            recordCollection.insert(document);
//            count++;
//            if ((count % 500) == 0) {
//                logger.info("Processed " + count + " measurement records");
//            }
//        }
//
//
//        return count;
//    }

    public Set<MeasurementRecord> parseMeasurementRecords(InputStream measurementRecordsInputStream) {
        Set<MeasurementRecord> result = Sets.newHashSet();
        int count = 0;
        List<WikiPage> pages = wikiXMLReader.parse(measurementRecordsInputStream);


        for (WikiPage page : pages) {
            WikiMesurementRecord record = new WikiMesurementRecord(page);

            if (record.getObservedProperties().size() == 0) {
                logger.info("No observed properties found for " + record.getLocation());
                continue;
            }

            MeasurementRecord measurementRecord = MeasurementRecord.getBuilder().measurementLocatioName(record.getLocation())
                    .title(record.getTitle())
                    .samplingFrequency(record.getSamplingFreq())
                    .serialNumber(record.getSerialNo())
                    .server(record.getServerName())
                    .dbTableName(record.getDBaseTableName())
                    .organisation(record.getOrganization())
                    .fromDate(record.getFromDate())
                    .toDate(record.getToDate())
                    .observedProperties(record.getObservedProperties())
                    .createMeasurementRecord();

            count++;
            if ((count % 500) == 0) {
                logger.info("Processed " + count + " measurement records");
            }

            result.add(measurementRecord);
        }


        return result;
    }
}
