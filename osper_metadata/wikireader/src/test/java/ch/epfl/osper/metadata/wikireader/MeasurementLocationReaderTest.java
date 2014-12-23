package ch.epfl.osper.metadata.wikireader;

import ch.epfl.osper.metadata.model.MeasurementLocationCache;
import ch.epfl.osper.metadata.wikireader.wikimodel.MediaWikiXMLReader;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;

public class MeasurementLocationReaderTest {

    private MeasurementLocationReader subject;

    private MeasurementLocationCache cache;

    private MediaWikiXMLReader wikiXMLReader;

    private LocationEnrichmentService locationEnrichmentService;


    @Before
    public void init() {
        cache = new MeasurementLocationCache();
        wikiXMLReader = new MediaWikiXMLReader();

        locationEnrichmentService = new LocationEnrichmentService();

        subject = new MeasurementLocationReader(cache, locationEnrichmentService, wikiXMLReader);
    }

    @Test
    public void readTest() throws Exception {
        InputStream fileStream = new FileInputStream("/Users/kryvych/Projects/osper/osper_metadata/wikireader/src/test/resources/MeasurementLocation.xml");
        int i = subject.readMeasurementLocations(fileStream);
        System.out.println("i = " + i);
    }
}