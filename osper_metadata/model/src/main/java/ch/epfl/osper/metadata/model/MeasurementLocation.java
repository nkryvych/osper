package ch.epfl.osper.metadata.model;

import java.util.Collection;

/**
 * Created by kryvych on 01/12/14.
 */
public class MeasurementLocation extends Location {

    private String id;

    private String deploymentName;

    private String tableName;

    private Collection<String> observedProperties;

    public MeasurementLocation(String id, String title, Coordinates coordinates) {
        super(id, title, coordinates);
    }

    public String getDeploymentName() {
        return deploymentName;
    }

    public Collection<String> getObservedProperties() {
        return observedProperties;
    }

    public String getTableName() {
        return tableName;
    }
}
