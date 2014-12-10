package ch.epfl.osper.geodata;

import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import org.geotools.data.collection.ListFeatureCollection;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import java.util.Date;

/**
 * Created by kryvych on 27/11/14.
 */
public class OsperFeatureTypeFactory {

    public FeatureCollection<SimpleFeatureType, SimpleFeature> buildDeploymentsMock() {
        SimpleFeatureType featureType = createDeploymentFeatureType();
        ListFeatureCollection featureCollection = new ListFeatureCollection(featureType);
        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
        SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(featureType);

        return featureCollection;

    }

    public SimpleFeatureType createDeploymentFeatureType() {
        SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
        builder.setName("deployment");
        builder.setCRS(DefaultGeographicCRS.WGS84);

        // add attributes in order
        builder.add("name", String.class);
        builder.add("type", String.class);
        builder.add("fromDate", Date.class);
        builder.add("toDate", Date.class);
        builder.add("numberOfMeasurementLocations", Integer.class);

        builder.add("coordinates", Point.class);
        // build the type
        return builder.buildFeatureType();

    }
}
