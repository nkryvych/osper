package ch.epfl.osper.metadata.wikireader;

import ch.epfl.osper.metadata.model.Coordinate;
import ch.epfl.osper.metadata.wikireader.wikimodel.RevisionXML;
import ch.epfl.osper.metadata.wikireader.wikimodel.WikiPage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WikiMesurementLocationTest {
    private static final String TEXT = "{{M Location\n" +
            "|Deployment=Wannengrat\n" +
            "|Location=wan7\n" +
            "|Coordinates=46.807626° N, 9.788119° E\n" +
            "|Altitude=2399\n" +
            "|Image=CIMG9898.JPG\n" +
            "|experiments=Measuring and Modelling the Snow Distribution on Wannengrat, tramm: triggering of rapid mass movements in steep terrain, toward better decision tools for the management of frequent avalanches, \n" +
            "|Coordinate_System=WGS84\n" +
            "}}\n" +
            "\n" +
            "Name: Steintaelli\n" +
            "\n" +
            "'''Data: [[Data::Wan7_Data]]'''";

    @Mock
    private WikiPage wikiPageMock;

    @Mock
    private RevisionXML revisionXMLMock;

    private WikiMesurementLocation subject;

    @Before
    public void initSubject() {
        when(revisionXMLMock.getText()).thenReturn(TEXT);
        when(wikiPageMock.getRevision()).thenReturn(revisionXMLMock);

        subject = new WikiMesurementLocation(wikiPageMock);
    }


    @Test
    public void parseCoordinatesTest() throws Exception {
        assertThat(subject.parseCoordinate("46.807626° N"), is(46.807626));
        assertThat(subject.parseCoordinate("8.7725584282"), is(8.7725584282));
        assertThat(subject.parseCoordinate("5m"), is(5d));
    }

    @Test
    public void getCoordinatesTest() throws Exception {
        assertThat(subject.getCoordinates(), is(new Coordinate(46.807626, 9.788119)));
    }

    @Test
    public void testNegativeCoordinate() throws Exception {

        when(revisionXMLMock.getText()).thenReturn("|Coordinates=46.807626° s, 9.788119° W\n");
        when(wikiPageMock.getRevision()).thenReturn(revisionXMLMock);

        subject = new WikiMesurementLocation(wikiPageMock);

        assertThat(subject.getCoordinates(), is(new Coordinate(-46.807626, -9.788119)));
    }

    @Test
    public void testNoWhitespaceInCoordinates() throws Exception {

        when(revisionXMLMock.getText()).thenReturn("|Coordinates = 46.10537918° N,7.270517947° E");
        when(wikiPageMock.getRevision()).thenReturn(revisionXMLMock);

        subject = new WikiMesurementLocation(wikiPageMock);

        assertThat(subject.getCoordinates(), is(new Coordinate(46.10537918, 7.270517947)));
    }

    @Test
    public void testLocation() throws Exception {
        assertThat(subject.getLocation(), is("wan7"));
    }
}