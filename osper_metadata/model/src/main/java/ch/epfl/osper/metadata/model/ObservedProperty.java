package ch.epfl.osper.metadata.model;

/**
 * Created by kryvych on 01/12/14.
 */
public class ObservedProperty {

    private String name;
    private String media;
    private String unit;
    private String columName;

    public ObservedProperty(String name, String media, String unit, String columName) {
        this.name = name;
        this.media = media;
        this.unit = unit;
        this.columName = columName;
    }

    public String getName() {
        return name;
    }

    public String getMedia() {
        return media;
    }

    public String getUnit() {
        return unit;
    }

    public String getColumName() {
        return columName;
    }
}
