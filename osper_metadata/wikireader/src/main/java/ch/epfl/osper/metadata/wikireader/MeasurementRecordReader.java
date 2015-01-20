package ch.epfl.osper.metadata.wikireader;

import ch.epfl.osper.metadata.model.MeasurementRecord;
import ch.epfl.osper.metadata.wikireader.wikimodel.MediaWikiXMLReader;
import ch.epfl.osper.metadata.wikireader.wikimodel.WikiPage;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
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


    public Set<MeasurementRecord> parseMeasurementRecords(InputStream measurementRecordsInputStream) {
        Set<MeasurementRecord> result = Sets.newHashSet();
        int count = 0;
        List<WikiPage> pages = wikiXMLReader.parse(measurementRecordsInputStream);


        for (WikiPage page : pages) {
            WikiMesurementRecord record = new WikiMesurementRecord(page);

            if (record.getObservedProperties().size() == 0) {
                logger.info("No observed properties found for " + record.getTitle());
                continue;
            }

            if (StringUtils.isEmpty(record.getDBaseTableName())) {
                logger.info("No db table name " + record.getTitle());
                continue;
            }


            MeasurementRecord measurementRecord = MeasurementRecord.getBuilder().measurementLocatioName(record.getLocation())
                    .title(record.getTitle())
                    .samplingFrequency(record.getSamplingFreq())
                    .serialNumber(record.getSerialNo())
                    .email(record.getPersonResponsible())
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
