package ch.epfl.osper.metadata.model;

/**
 * Created by kryvych on 01/12/14.
 */
public class Location {

    private String id;
    private String title;

    private Coordinates coordinates;

    private double elevation;
    private String aspect;

    public Location(String id, String title, Coordinates coordinates) {
        this.id = id;
        this.title = title;
        this.coordinates = coordinates;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Coordinates getCoordinates() {
        return coordinates;
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
