package ch.epfl.osper.metadata.wikireader;

import ch.epfl.osper.metadata.model.ObservedProperty;
import ch.epfl.osper.metadata.wikireader.wikimodel.RevisionXML;
import ch.epfl.osper.metadata.wikireader.wikimodel.WikiPage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.Date;
import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.when;

/**
 * Created by kryvych on 11/12/14.
 */
@RunWith(MockitoJUnitRunner.class)
public class WikiMeasurementRecordTest {

    private static final String TEXT = "{{S Deploy Rec\n" +
            "|SerialNo=25552\n" +
            "|Location=wan7\n" +
            "|RelX=0\n" +
            "|RelY=2\n" +
            "|RelZ=4\n" +
            "|SlopeAngle=0\n" +
            "|FromDate=2006/12/22\n" +
            "|ServerName=http://montblanc.slf.ch:22001\n" +
            "|DBaseTableName=wannengrat_wan7\n" +
            "|DBaseParameterName=wind_speed_scalar_av, wind_speed_max, wind_direction, wind_direction_stdev, wind_speed_vector_av\n" +
            "|MeasurementMedia=Air, Air, Air, Air, Air\n" +
            "|MeasuredParameter=Wind Speed, Wind Speed, Wind Direction, Wind Direction Standard Deviation, Wind Speed\n" +
            "|Unit=m/s, m/s, &lt;sup&gt;o&lt;/sup&gt;, &lt;sup&gt;o&lt;/sup&gt;, m/s\n" +
            "|SamplingFreq=1Hz averaged over 10 mins\n" +
            "|PersonResponsible=lehning@slf.ch\n" +
            "|ContactNo=+41 81 4170 158\n" +
            "|Organization=SLF\n" +
            "|Department=SMM\n" +
            "|ParameterNumber=3,7,5,6,4\n" +
            "|DBaseName=wan7\n" +
            "|Parameter=wind_speed_scalar_av\n" +
            "|Responsible=lehning@slf.ch\n" +
            "}}";

    @Mock
    private WikiPage wikiPageMock;

    @Mock
    private RevisionXML revisionXMLMock;

    private WikiMesurementRecord subject;

    @Before
    public void initSubject() {
        when(revisionXMLMock.getText()).thenReturn(TEXT);
        when(wikiPageMock.getRevision()).thenReturn(revisionXMLMock);

        subject = new WikiMesurementRecord(wikiPageMock);
    }

    @Test
    public void stringFieldsTest() throws Exception {
        assertThat(subject.getSerialNo(), is("25552"));
        assertThat(subject.getDBaseTableName(), is("wannengrat_wan7"));
    }

    @Test
    public void observedPropertyTest() throws Exception {
        assertThat(subject.getObservedProperties().size(), is(5));
        assertThat(subject.getObservedProperties().contains(new ObservedProperty("Wind Speed", "Air", "m/s", "wind_speed_vector_av")), is(true));
    }

    @Test
    public void dateTest() throws Exception {
        assertThat(subject.getFromDate(), is(WikiMesurementRecord.DATE_FORMAT.parse("2006/12/22")));
        assertThat(subject.getToDate(), nullValue());
    }
}
