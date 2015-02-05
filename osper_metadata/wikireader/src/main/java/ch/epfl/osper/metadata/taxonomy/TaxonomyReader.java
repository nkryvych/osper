package ch.epfl.osper.metadata.taxonomy;

import ch.epfl.osper.metadata.model.TaxonomyProperty;
import ch.epfl.osper.metadata.model.VirtualSensor;
import com.csvreader.CsvReader;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

/**
 * Created by kryvych on 02/02/15.
 */

@Named
public class TaxonomyReader {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Set<TaxonomyProperty> readTaxonomy(String fileName) {
        Set<TaxonomyProperty> result = Sets.newHashSet();
        try (FileReader reader = new FileReader(fileName)) {

            CsvReader csvReader = new CsvReader(reader);
            while (csvReader.readRecord()) {
                TaxonomyProperty property = new TaxonomyProperty(csvReader.get(0).trim(),
                        csvReader.get(2).trim().toLowerCase(), csvReader.get(3).trim().toLowerCase());
                result.add(property);

            }
        } catch (IOException e) {
            logger.error("Cannot read taxonomy values from " + fileName, e);
        }

        return result;
    }
}
