package ch.epfl.osper.metadata.wikireader;

import ch.epfl.osper.metadata.model.Coordinate;
import ch.epfl.osper.metadata.model.MeasurementLocation;
import ch.epfl.osper.metadata.model.MeasurementLocationCache;
import ch.epfl.osper.metadata.model.MeasurementRecord;
import ch.epfl.osper.metadata.wikireader.wikimodel.MediaWikiXMLReader;
import ch.epfl.osper.metadata.wikireader.wikimodel.WikiPage;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

/**
 * Created by kryvych on 12/12/14.
 */
@Named
public class MeasurementLocationReader {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private LocationEnrichmentService enrichmentService;

    private MediaWikiXMLReader wikiXMLReader;

    @Inject
    public MeasurementLocationReader(LocationEnrichmentService enrichmentService, MediaWikiXMLReader wikiXMLReader) {
        this.enrichmentService = enrichmentService;
        this.wikiXMLReader = wikiXMLReader;
    }

    public Set<MeasurementLocation> readMeasurementLocations(InputStream measurementRecordsInputStream) {
        int count = 0;
        Set<MeasurementLocation> result = Sets.newHashSet();

        List<WikiPage> pages = wikiXMLReader.parse(measurementRecordsInputStream);
        for (WikiPage page : pages) {
            WikiMesurementLocation wikiLocation = new WikiMesurementLocation(page);
            Coordinate coordinates = wikiLocation.getCoordinates();

            if (coordinates != null) {
                MeasurementLocation location = MeasurementLocation.getBuilder()
                        .wikiId(wikiLocation.getId())
                        .title(wikiLocation.getTitle())
                        .location(wikiLocation.getLocationPoint())
                        .deploymentName(wikiLocation.getDeployment())
                        .locationName(wikiLocation.getLocation())
                        .createMeasurementLocation();

                enrichmentService.addExtraInfo(location);
                result.add(location);
                count++;
                if ((count % 100) == 0) {
                    logger.info("Processed " + count + " measurement locations");
                }


            }
        }

        return result;
    }

}
