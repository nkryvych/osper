package ch.epfl.osper.metadata.wikireader;

import ch.epfl.osper.metadata.model.ObservedProperty;
import ch.epfl.osper.metadata.wikireader.wikimodel.WikiPage;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * Created by kryvych on 11/12/14.
 */
public class WikiMesurementRecord extends WikiPageProxy {

    static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd");

    public WikiMesurementRecord(WikiPage page) {
        super(page);
    }

    public String getSerialNo() {
        return getPropertyValue("|SerialNo");
    }

    public String getLocation() {
        return getPropertyValue("|Location");
    }

    public String getRelX() {
        return getPropertyValue("|RelX");
    }

    public String getRelY() {
        return getPropertyValue("|RelY");
    }

    public String getRelZ() {
        return getPropertyValue("|RelZ");
    }

    public Date getFromDate() {
        return parseDate("FromDate");
    }

    public Date getToDate() {
        return parseDate("ToDate");
    }

    public String getServerName() {
        return getPropertyValue("|ServerName");
    }

    public String getDBaseTableName() {
        return getPropertyValue("|DBaseTableName");
    }

    public Set<ObservedProperty> getObservedProperties() {
        HashSet<ObservedProperty> result = Sets.newHashSet();

        if (getPropertyValue("|DBaseParameterName") == null || getPropertyValue("|MeasurementMedia") == null
                || getPropertyValue("|MeasuredParameter") == null || getPropertyValue("|Unit") == null) {
            logger.info("Cannot create observed properties for MeasurementRecord " + getTitle());
            return result;
        }

        String[] dbParameterNames = getPropertyValue("|DBaseParameterName").split(",");
        String[] measurementMedias = getPropertyValue("|MeasurementMedia").split(",");
        String[] measuredParameters = getPropertyValue("|MeasuredParameter").split(",");
        String[] units = getPropertyValue("|Unit").split(", ");

        if (dbParameterNames.length !=  measurementMedias.length
                || dbParameterNames.length !=  measuredParameters.length
                || dbParameterNames.length !=  units.length) {
            logger.info("Cannot create observed properties (not all fields present) for MeasurementRecord " + getTitle());
            return result;
        }

        for (int i = 0; i < dbParameterNames.length; i++) {
            result.add(new ObservedProperty(measuredParameters[i].trim(), measurementMedias[i].trim()
                    , units[i].trim(), dbParameterNames[i].trim()));
        }

        return result;
    }

    public String getSamplingFreq() {
        return getPropertyValue("|SamplingFreq");
    }

    public String getPersonResponsible() {
        return getPropertyValue("|PersonResponsible");
    }

    public String getContactNo() {
        return getPropertyValue("|ContactNo");
    }

    public String getOrganization() {
        return getPropertyValue("|Organization");
    }

    public String getDepartment() {
        return getPropertyValue("|Department");
    }

    public String getNotes() {
        return getPropertyValue("|Notes");
    }

    protected Date parseDate(String dateType) {
        if (StringUtils.isEmpty(getPropertyValue("|" + dateType))) {
            //ToDo: decide on behavior if data is not known.
            return null;
        }

        try {
            return DATE_FORMAT.parse(getPropertyValue("|" + dateType));
        } catch (ParseException e) {
            logger.error("Cannot parse " + dateType + " of MeasurementRecord " + getTitle(), e);
            return null;
        }
    }

}
