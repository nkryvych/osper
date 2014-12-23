package ch.epfl.osper.metadata.wikireader;

import ch.epfl.osper.metadata.model.Location;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;
import java.util.Collection;

/**
 * Created by kryvych on 19/12/14.
 */
@Named
@Scope("prototype")
public class LocationEnrichmentService {

    public static final String COORDINATES_URL = "http://www.ebi.uniprot.org/uniprot/?query=accession:{0}&format=tab&columns=id,database(reactome)";

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private RestTemplate restTemplate;

    @Inject
    public LocationEnrichmentService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Collection<String> fetchReactomeIds(String uniprotId) {
        String url = MessageFormat.format(COORDINATES_URL, uniprotId);
//
//        try {
//            String result = restTemplate.getForObject(url, String.class);
//            return parseResult(result);
//        } catch (RestClientException e) {
//            logger.error("", e);
//            return Lists.newArrayList();
//        }

        return null;
    }



    public boolean addExtraInfo(Location location) {
        return true;
    }
}
