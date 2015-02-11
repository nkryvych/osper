package ch.epfl.osper.metadata.services;

import ch.epfl.osper.metadata.model.MeasurementRecord;
import ch.epfl.osper.metadata.model.ObservedProperty;
import com.google.common.collect.Sets;
import com.google.gson.stream.JsonWriter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;
import java.util.Set;

/**
 * Created by kryvych on 13/01/15.
 */
@Service
public class GeoJSONConverter<T extends MeasurementRecord> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private final Properties configuration;

    private final TaxonomyResolver taxonomyResolver;

    @Inject
    public GeoJSONConverter(Properties configuration, TaxonomyResolver taxonomyResolver) {
        this.configuration = configuration;
        this.taxonomyResolver = taxonomyResolver;
    }

    public String convertMeasurementRecords(Collection<T> records, boolean detailed) {
        try (StringWriter writer = new StringWriter()) {
            writeRecordstoJsonStream(writer, records, detailed);
            return writer.toString();
        } catch (IOException e) {
            logger.error("Cannot write JSON! ", e);
            return "";
        }
    }

    public String convirtMeasuremetRecord(T measurementRecord) {
        return "";
    }

    protected void writeRecordstoJsonStream(StringWriter out, Collection<T> records, boolean detailed) throws IOException {
        JsonWriter writer = new JsonWriter(out);
        writer.beginObject();
        writer.name("type").value("FeatureCollection");
        writer.name("features");
        writer.beginArray();
        for (T record : records) {
            writeRecord(writer, record, detailed);
        }
        writer.endArray();
        writer.endObject();
        writer.close();
    }

//    protected void writeRecordsArray(JsonWriter writer, Collection<T> records) throws IOException {
//        writer.beginArray();
//        for (T record : records) {
//            writeRecord(writer, record);
//        }
//        writer.endArray();
//    }

    protected void writeRecord(JsonWriter writer, T record, boolean detailed) throws IOException {
        writer.beginObject();
        writer.name("type").value("Feature");
        writer.name("geometry");
        //Geometry
        writer.beginObject();
        writer.name("type").value("Point");
        writer.name("coordinates");
        writePoint(writer, record.getLocationPoint());
        writer.endObject();

        writer.name("properties");
        //Properties
        writer.beginObject();

        if (detailed) {
            writeDetailed(writer, record);
        } else {
            writeShort(writer, record);
        }
        writer.name("wikiLink").value(configuration.getProperty("wiki.server") + record.getMeasurementLocation().getTitle());
        writer.endObject();

        writer.endObject();
    }

    protected void writeShort(JsonWriter writer, T record) throws IOException {

        writer.name("observed_properties");
        writer.beginArray();

        writeObservedProperties(writer, record);
        writer.endArray();
        writer.name("deployment").value(record.getMeasurementLocation().getDeploymentName());
        writer.name("server").value(record.getServer());

        writer.name("aspect").value(record.getMeasurementLocation().getAspect());
        writer.name("slopeAngle").value(record.getMeasurementLocation().getSlope());
        writer.name("organisation").value(record.getOrganisation());
        writer.name("sensorName").value(record.getDbTableName());

        writer.name("fromDate").value(DATE_FORMAT.format(record.getFromDate() == null ? new Date() : record.getFromDate()));
        writer.name("toDate").value(DATE_FORMAT.format(record.getToDate() == null ? new Date() : record.getToDate()));
        writer.name("sensorLink")
                .value(configuration.getProperty("metadata.server") + "metadata/measurementRecords/" + record.getDbTableName());


    }

    protected void writeDetailed(JsonWriter writer, T record) throws IOException {

        writer.name("sensorName").value(record.getDbTableName());
        writer.name("deployment").value(record.getMeasurementLocation().getDeploymentName());
        if (StringUtils.isNotEmpty(record.getSamplingFrequency())) {
            writer.name("samplingFreq").value(record.getSamplingFrequency());
        }
        if (StringUtils.isNotEmpty(record.getSerialNumber())) {
            writer.name("serialNumber").value(record.getSerialNumber());
        }
        writer.name("organisation").value(record.getOrganisation());
        writer.name("e-mail").value(record.getEmail());

//        writer.name("server").value(record.getServer());
        writer.name("observed_properties");
        writer.beginArray();
        writeObservedProperties(writer, record);
        writer.endArray();

        writer.name("aspect").value(record.getMeasurementLocation().getAspect());
        writer.name("slopeAngle").value(record.getMeasurementLocation().getSlope());
        writer.name("elevation").value(record.getMeasurementLocation().getElevation());
        writer.name("relativeCoordinates").value(record.getRelativePosition());

        writer.name("deployed").value(buildDeploymentDatesString(record.getFromDate(), record.getToDate()));

        writer.name("dataLink").value(configuration.getProperty("gsn.server"));

    }

    private void writeObservedProperties(JsonWriter writer, T record) throws IOException {
        Set<String> terms = Sets.newHashSet();
        for (ObservedProperty property : record.getObservedProperties()) {
            String term = taxonomyResolver.getTermForColumnName(property.getColumnName());
            if (StringUtils.isNotEmpty(term) && !term.equals("NA")) {
                terms.add(term);
            }
        }
        for (String term : terms) {
            writer.value(term);
        }
    }

    protected void writePoint(JsonWriter writer, Point point) throws IOException {
        writer.beginArray();
        writer.value(point.getY());
        writer.value(point.getX());
        writer.endArray();
    }

    protected String buildDeploymentDatesString(Date from, Date to) {
        StringBuilder sb = new StringBuilder();
        sb.append("from ");
        sb.append(from == null ? "Unspecified " : DATE_FORMAT.format(from));
        sb.append(" to ");
        sb.append(to == null ? "ongoing " : DATE_FORMAT.format(to));
        return sb.toString();
    }
//    protected void writeLink(JsonWriter writer)
}
