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

        if (!columName.equals(that.columName)) return false;
        if (media != null ? !media.equals(that.media) : that.media != null) return false;
        if (!name.equals(that.name)) return false;
        if (unit != null ? !unit.equals(that.unit) : that.unit != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (media != null ? media.hashCode() : 0);
        result = 31 * result + (unit != null ? unit.hashCode() : 0);
        result = 31 * result + columName.hashCode();
        return result;
    }
}
