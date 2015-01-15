package ch.epfl.osper.metadata.services;

import ch.epfl.osper.metadata.model.MeasurementRecord;
import ch.epfl.osper.metadata.model.ObservedProperty;
import com.google.gson.stream.JsonWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by kryvych on 13/01/15.
 */
@Service
public class GeoJSONConverter {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public String convertMeasurementRecords(List<MeasurementRecord> records) {
        try (StringWriter writer = new StringWriter()) {
            writeRecordstoJsonStream(writer, records);
            return writer.toString();
        } catch (IOException e) {
            logger.error("Cannot write JSON! " , e);
            return "";
        }
    }

    protected void writeRecordstoJsonStream(StringWriter out, List<MeasurementRecord> records) throws IOException {
        JsonWriter writer = new JsonWriter(out);
        writer.beginObject();
        writer.name("type").value("FeatureCollection");
        writer.name("features");
        writeRecordsArray(writer, records);
        writer.endObject();
        writer.close();
    }

    protected void writeRecordsArray(JsonWriter writer, List<MeasurementRecord> records) throws IOException {
        writer.beginArray();
        for (MeasurementRecord record : records) {
            writeRecord(writer, record);
        }
        writer.endArray();
    }

    protected void writeRecord(JsonWriter writer, MeasurementRecord record) throws IOException {
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
        writer.name("id").value(record.getId());
        writer.name("title").value(record.getTitle());
        writer.name("measurementLocation").value(record.getMeasurementLocationName());
        writer.name("samplingFreq").value(record.getSamplingFrequency());
        writer.name("serialNumber").value(record.getSerialNumber());
        writer.name("server").value(record.getServer());
        writer.name("dbTableName").value(record.getDbTableName());
        writer.name("organisation").value(record.getOrganisation());
        writer.name("fromDate").value(DATE_FORMAT.format(record.getFromDate() == null ? new Date() : record.getFromDate()));
        writer.name("toDate").value(DATE_FORMAT.format(record.getToDate() == null ? new Date() : record.getToDate()));
        writer.name("aspect").value(record.getMeasurementLocation().getAspect());
        writer.name("slopeAngle").value(record.getMeasurementLocation().getSlope());
        writer.name("elevation").value(record.getMeasurementLocation().getElevation());

        //Observed properties
        writer.name("observedProperties");
        writer.beginArray();
        for (ObservedProperty observedProperty : record.getObservedProperties()) {
            writer.beginObject();
            writer.name("name").value(observedProperty.getName());
            writer.name("media").value(observedProperty.getMedia());
            writer.name("columnName").value(observedProperty.getColumnName());
            writer.endObject();
        }
        writer.endArray();
        writer.endObject();

        writer.endObject();
    }

    protected void writePoint(JsonWriter writer, Point point) throws IOException {
        writer.beginArray();
        writer.value(point.getY());
        writer.value(point.getX());
        writer.endArray();
    }
}
