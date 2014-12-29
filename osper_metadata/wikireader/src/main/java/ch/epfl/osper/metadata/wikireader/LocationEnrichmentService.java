package ch.epfl.osper.metadata.wikireader;

import ch.epfl.osper.metadata.model.Location;
import com.google.common.collect.Lists;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collection;

/**
 * Created by kryvych on 19/12/14.
 */
@Named
@Scope("prototype")
public class LocationEnrichmentService {

    // http://osgl.ethz.ch/webservices/gsndem/get2?lon=8.50693&lat=47.40803
    public static final String COORDINATES_URL = "http://osgl.ethz.ch/webservices/gsndem/get2?lon={0}&lat={1}";

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private RestTemplate restTemplate;

    @Inject
    public LocationEnrichmentService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }



    public boolean addExtraInfo(Location location) {
        String url = MessageFormat.format(COORDINATES_URL, location.getCoordinate().getLongitude(), location.getCoordinate().getLatitude());

        try {
            String result = restTemplate.getForObject(url, String.class);
            parseResult(result, location);
            return true;

        } catch (RestClientException | ParserConfigurationException | SAXException | IOException e) {
            logger.error("Cannot get extra information for location ", e);
            return false;
        }

    }

    protected void parseResult(String xmlString, Location location) throws ParserConfigurationException, IOException, SAXException {
        //Create DocumentBuilderFactory for reading xml file

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse(IOUtils.toInputStream(xmlString, "UTF-8"));
        NodeList dem = document.getElementsByTagName("dem");
        Element item = (Element) dem.item(0);
        location.setElevation(Double.parseDouble(item.getElementsByTagName("elevation").item(0).getTextContent()));
        location.setSlope(Double.parseDouble(item.getElementsByTagName("slope").item(0).getTextContent()));
        location.setAspect(item.getElementsByTagName("aspect").item(0).getTextContent());


    }
}
