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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ObservedProperty that = (ObservedProperty) o;

        if (columName != null ? !columName.equals(that.columName) : that.columName != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (columName != null ? columName.hashCode() : 0);
        return result;
    }
}
