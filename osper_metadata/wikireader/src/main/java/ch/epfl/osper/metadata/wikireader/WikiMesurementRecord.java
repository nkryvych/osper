package ch.epfl.osper.metadata.wikireader;

import ch.epfl.osper.metadata.model.ObservedProperty;
import ch.epfl.osper.metadata.wikireader.wikimodel.WikiPage;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

        Splitter splitter = Splitter.on(',').omitEmptyStrings().trimResults();

        ArrayList<String> dbParameterNames = Lists.newArrayList(splitter.split(getPropertyValue("|DBaseParameterName")));
        ArrayList<String> measuredParameters = Lists.newArrayList(splitter.split(getPropertyValue("|MeasuredParameter")));
        if(dbParameterNames.size() < measuredParameters.size()) {
            logger.info("Cannot create observed properties (not all measured properties map to DB) for MeasurementRecord " + getTitle());
            return result;
        }

        ArrayList<String> measurementMedias = new ArrayList(Collections.nCopies(measuredParameters.size(), "NA"));
        ArrayList<String> units = new ArrayList(Collections.nCopies(measuredParameters.size(), "NA"));

        int count = 0;
        for (String media : splitter.split(getPropertyValue("|MeasurementMedia"))) {
            measurementMedias.add(count++, media);
        }

        count = 0;
        for (String media : splitter.split(getPropertyValue("|Unit"))) {
            units.add(count++, media);
        }

        for (int i = 0; i < measuredParameters.size(); i++) {
            result.add(new ObservedProperty(measuredParameters.get(i), measurementMedias.get(i)
                    , units.get(i), dbParameterNames.get(i)));
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
