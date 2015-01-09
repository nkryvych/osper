package ch.epfl.osper.metadata.wikireader;

import ch.epfl.osper.metadata.model.MapLocation;
import ch.epfl.osper.metadata.model.MeasurementLocation;
import ch.epfl.osper.metadata.model.MeasurementLocationCache;
import ch.epfl.osper.metadata.wikireader.wikimodel.MediaWikiXMLReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Set;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MeasurementLocationReaderTest {

    private MeasurementLocationReader subject;

    private MeasurementLocationCache cache;

    private MediaWikiXMLReader wikiXMLReader;

    @Mock
    private LocationEnrichmentService locationEnrichmentServiceMock;


    @Before
    public void init() {
        cache = new MeasurementLocationCache();
        wikiXMLReader = new MediaWikiXMLReader();

        when(locationEnrichmentServiceMock.addExtraInfo(any(MeasurementLocation.class))).thenReturn(true);

        subject = new MeasurementLocationReader(locationEnrichmentServiceMock, wikiXMLReader);
    }

    @Test
    public void readTest() throws Exception {
        InputStream fileStream = new FileInputStream("/Users/kryvych/Projects/osper/osper_metadata/wikireader/src/test/resources/MeasurementLocation.xml");
        Set<MeasurementLocation> measurementLocations = subject.readMeasurementLocations(fileStream);
        int i = measurementLocations.size();
        System.out.println("i = " + i);
    }
}