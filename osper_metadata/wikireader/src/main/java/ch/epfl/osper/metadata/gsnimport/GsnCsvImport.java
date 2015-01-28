package ch.epfl.osper.metadata.gsnimport;

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
 * Created by kryvych on 26/01/15.
 */

@Named
public class GsnCsvImport {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Set<VirtualSensor> readSensors(String fileName) {
        Set<VirtualSensor> sensors = Sets.newHashSet();
        try (FileReader reader = new FileReader(fileName)) {

            CsvReader csvReader = new CsvReader(reader, '\t');
            while (csvReader.readRecord()) {
                VirtualSensor.VirtualSensorBuilder builder = VirtualSensor.builder();
                builder.name(csvReader.get(0).trim());

                String isProtected = csvReader.get(1).trim();
                if(StringUtils.isNotEmpty(isProtected)) {
                    builder.isPublic(false);
                } else {
                    builder.isPublic(true);
                }
                sensors.add(builder.createVirtualSensor());

            }
        } catch (IOException e) {
            logger.error("Cannot read virtual sensors from " + fileName, e);
        }

        return sensors;
    }
}
