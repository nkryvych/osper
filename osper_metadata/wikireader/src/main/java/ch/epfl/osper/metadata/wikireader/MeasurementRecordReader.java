package ch.epfl.osper.metadata.wikireader;

import ch.epfl.osper.metadata.model.*;
import ch.epfl.osper.metadata.wikireader.wikimodel.MediaWikiXMLReader;
import ch.epfl.osper.metadata.wikireader.wikimodel.WikiPage;
import com.google.common.collect.Lists;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by kryvych on 21/12/14.
 */
@Named
public class MeasurementRecordReader {
    protected static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private MeasurementLocationCache cache;

    private MediaWikiXMLReader wikiXMLReader;

    private DBCollection recordCollection;

    @Inject
    public MeasurementRecordReader(MeasurementLocationCache cache, MediaWikiXMLReader wikiXMLReader, DBCollection recordCollection) {
        this.cache = cache;
        this.wikiXMLReader = wikiXMLReader;
        this.recordCollection = recordCollection;
    }

    public int readMeasurementRecords(InputStream measurementRecordsInputStream) {
        int count = 0;
        List<WikiPage> pages = wikiXMLReader.parse(measurementRecordsInputStream);

        //remove all records from collection
        recordCollection.remove(new BasicDBObject());

        for (WikiPage page : pages) {
            WikiMesurementRecord record = new WikiMesurementRecord(page);

            MeasurementLocation location = cache.getLocation(record.getLocation());
            if (location == null) {
                logger.info("No location found for " + record.getLocation());
                continue;
            }

            if (record.getObservedProperties().size() == 0) {
                logger.info("No observed properties found for " + record.getLocation());
                continue;
            }

            Coordinate coordinate = location.getCoordinate();

            BasicDBObject document = new BasicDBObject("title", record.getTitle())
                    .append("type", "MeasurementRecord")
                    .append("mesurementLocation", record.getLocation())
                    .append("samplingFreq", record.getSamplingFreq())
                    .append("serialNumber", record.getSerialNo())
                    .append("server", record.getServerName())
                    .append("dbTableName", record.getDBaseTableName())
                    .append("organisation", record.getOrganization())
                    .append("fromDate", record.getFromDate() != null ? DATE_FORMAT.format(record.getFromDate()) : null)
                    .append("toDate", record.getToDate() != null ? DATE_FORMAT.format(record.getToDate()) : null)
                    .append("slopeAngle", location.getSlope())
                    .append("aspect", location.getAspect())
                    .append("elevation", location.getElevation())
                    .append("location", new BasicDBObject("type", "Point")
                            .append("coordinates", Lists.newArrayList(coordinate.getLatitude(), coordinate.getLongitude())));

            Set<String> properties = new HashSet<String>();
            for (ObservedProperty observedProperty : record.getObservedProperties()) {
                String name = observedProperty.getName();
                if (StringUtils.isNotEmpty(name)) {
                    properties.add(name);
                    cache.addObservedProperty(observedProperty);
                }
            }

            document.append("observedProperty", Lists.newArrayList(properties));
            recordCollection.insert(document);
            count++;
        }


        return count;
    }

}
