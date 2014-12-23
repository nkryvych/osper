package ch.epfl.osper.metadata.wikireader;

import ch.epfl.osper.metadata.model.Coordinate;
import ch.epfl.osper.metadata.model.MeasurementLocation;
import ch.epfl.osper.metadata.model.MeasurementLocationCache;
import ch.epfl.osper.metadata.wikireader.wikimodel.MediaWikiXMLReader;
import ch.epfl.osper.metadata.wikireader.wikimodel.WikiPage;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.InputStream;
import java.util.List;

/**
 * Created by kryvych on 12/12/14.
 */
@Named
public class MeasurementLocationReader {

    private MeasurementLocationCache cache;

    private LocationEnrichmentService enrichmentService;

    private MediaWikiXMLReader wikiXMLReader;

    @Inject
    public MeasurementLocationReader(MeasurementLocationCache cache, LocationEnrichmentService enrichmentService, MediaWikiXMLReader wikiXMLReader) {
        this.cache = cache;
        this.enrichmentService = enrichmentService;
        this.wikiXMLReader = wikiXMLReader;
    }

    public int readMeasurementLocations(InputStream measurementRecordsInputStream) {
        int count = 0;
        List<WikiPage> pages = wikiXMLReader.parse(measurementRecordsInputStream);
        for (WikiPage page : pages) {
            WikiMesurementLocation wikiLocation = new WikiMesurementLocation(page);
            Coordinate coordinates = wikiLocation.getCoordinates();

            if (coordinates != null) {
                MeasurementLocation location = new MeasurementLocation(wikiLocation.getId()
                        , wikiLocation.getTitle(), coordinates, wikiLocation.getLocation());
                enrichmentService.addExtraInfo(location);
                location.setDeploymentName(wikiLocation.getDeployment());
                cache.putLocation(location);
                count++;
            }
        }


        return count;
    }

}
