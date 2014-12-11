package ch.epfl.osper.metadata.wikireader.wikimodel;

import ch.epfl.osper.metadata.wikireader.wikimodel.MediaWikiXMLReader;
import ch.epfl.osper.metadata.wikireader.wikimodel.WikiPage;
import org.junit.Test;

import java.util.List;

public class MeasurementLocationXMLReaderTest {

    private MediaWikiXMLReader subject = new MediaWikiXMLReader();

    @Test
    public void readTest() throws Exception {
        List<WikiPage> parse = subject.parse(MEASUREMENT_LOCATION);
        System.out.println("parse = " + parse);
    }


    @Test
    public void testWrite() {
        String write = subject.write();
        System.out.println(write);
    }

    private static final String MEASUREMENT_LOCATION = "<mediawiki xmlns=\"http://www.mediawiki.org/xml/export-0.3/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.mediawiki.org/xml/export-0.3/ http://www.mediawiki.org/xml/export-0.3.xsd\" version=\"0.3\" xml:lang=\"en\">\n" +
            "  <siteinfo>\n" +
            "    <sitename>SwissExperiment</sitename>\n" +
            "    <base>http://www.swiss-experiment.ch/index.php/Main_Page</base>\n" +
            "    <generator>MediaWiki 1.15.1</generator>\n" +
            "    <case>first-letter</case>\n" +
            "    <namespaces>\n" +
            "      <namespace key=\"-2\">Media</namespace>\n" +
            "      <namespace key=\"-1\">Special</namespace>\n" +
            "      <namespace key=\"0\" />\n" +
            "      <namespace key=\"1\">Talk</namespace>\n" +
            "      <namespace key=\"2\">User</namespace>\n" +
            "      <namespace key=\"3\">User talk</namespace>" +
            "    </namespaces>\n" +
            "  </siteinfo>\n" +
            "  <page>\n" +
            "    <title>Fieldsite:Eb78a5f</title>\n" +
            "    <id>14696</id>\n" +
            "    <revision>\n" +
            "      <id>64408</id>\n" +
            "      <timestamp>2011-12-01T08:10:35Z</timestamp>\n" +
            "      <contributor>\n" +
            "        <username>Ndawes</username>\n" +
            "        <id>131</id>\n" +
            "      </contributor>\n" +
            "      <text xml:space=\"preserve\">{{M Location\n" +
            "|DeploymentName=Damma\n" +
            "|Location=Runoff\n" +
            "|Altitude=1844\n" +
            "|Coordinates=46.64324° N,  8.4707778° E\n" +
            "|experiments=biglink, \n" +
            "}}</text>\n" +
            "    </revision>\n" +
            "  </page>" +
            "  <page>\n" +
            "    <title>Wannengrat:Wan6</title>\n" +
            "    <id>4621</id>\n" +
            "    <revision>\n" +
            "      <id>64910</id>\n" +
            "      <timestamp>2012-01-02T20:20:26Z</timestamp>\n" +
            "      <contributor>\n" +
            "        <username>Ndawes</username>\n" +
            "        <id>131</id>\n" +
            "      </contributor>\n" +
            "      <text xml:space=\"preserve\">{{M Location\n" +
            "|Deployment=Wannengrat\n" +
            "|Location=wan6\n" +
            "|Coordinates=46.806238° N, 9.786617° E\n" +
            "|Altitude=2434\n" +
            "|Image=SwissExMedia3.jpg\n" +
            "|experiments=Measuring and Modelling the Snow Distribution on Wannengrat, tramm: triggering of rapid mass movements in steep terrain, toward better decision tools for the management of frequent avalanches, \n" +
            "|Coordinate_System=WGS84\n" +
            "}}\n" +
            "[http://montblanc.slf.ch:22001 Data]\n" +
            "\n" +
            "Name: Wind Steintaelli\n" +
            "\n" +
            "'''Data: [[Data::Wan6_Data]]'''</text>\n" +
            "    </revision>\n" +
            "  </page>" +
            "</mediawiki>";
}