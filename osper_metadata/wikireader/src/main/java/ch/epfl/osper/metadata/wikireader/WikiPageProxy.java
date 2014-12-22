package ch.epfl.osper.metadata.wikireader;

import ch.epfl.osper.metadata.wikireader.wikimodel.WikiPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Properties;

/**
 * Created by kryvych on 11/12/14.
 */
public abstract class WikiPageProxy {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected WikiPage page;
    private Properties properties;

    public WikiPageProxy(WikiPage page) {
        this.page = page;
        initProperties(page);
    }

    protected void initProperties(WikiPage page) {
        String text = page.getRevision().getText();
        String s = text.replace(" | ", "|");
        Reader reader = new StringReader(s);
        properties = new Properties();
        try {
            properties.load(reader);
            reader.close();
        } catch (IOException e) {
            logger.error("Cannot read text from Wiki for MeasurementRecord " + getTitle(), e);
        }
    }

    public String getTitle() {
        return page.getTitle().trim();
    }

    public String getId() {
        return page.getId().trim();
    }

    protected String getPropertyValue(String key) {
        return properties.getProperty(key);
    }

    public static void main(String[] args) {
        String input = "{{M_Location\n" +
                " | DeploymentName = Schafberg\n" +
                " | Location = Schafberg B2\n" +
                " | Coordinates = 46.498778°N,9.92521799999999°E\n" +
                " | Altitude = 2732\n" +
                " | SwissLatitude = 790855\n" +
                " | SwissLongitude = 152744\n" +
                " | Image = \n" +
                " | experiments = SLF permafrost borehole data\n" +
                "}}";

        System.out.println(input.replace(" | ", "|"));
    }
}
