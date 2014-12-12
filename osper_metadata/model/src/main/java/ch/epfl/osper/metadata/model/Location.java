package ch.epfl.osper.metadata.model;

/**
 * Created by kryvych on 01/12/14.
 */
public class Location {

    private String id;
    private String title;

    private Coordinate coordinate;

    private double elevation;
    private String aspect;

    public Location(String id, String title, Coordinate coordinate) {
        this.id = id;
        this.title = title;
        this.coordinate = coordinate;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public double getElevation() {
        return elevation;
    }

    public String getAspect() {
        return aspect;
    }

    public void setElevation(double elevation) {
        this.elevation = elevation;
    }

    public void setAspect(String aspect) {
        this.aspect = aspect;
    }

}
