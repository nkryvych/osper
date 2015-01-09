//package ch.epfl.osper.metadata.model;
//
//import com.google.common.collect.Sets;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Set;
//
///**
// * Created by kryvych on 01/12/14.
// */
//public class MockFactory {
//
//    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy");
//    public static final String NAME = "Deployment1";
//    public static final Date FROM = new Date();
//    public static final Date TO = new Date();
//    public static final double LATITUDE = 46.80314;
//    public static final double LONGITUDE = 9.78171;
//
//
//    public static Deployment createDeployment(String name, Date from, Date to, double latitude, double longitude, boolean withData) {
//        if (withData) {
//            Set<MeasurementLocation> measurementLocations = Sets.newHashSet(
//                    new MeasurementLocationBuilder().setId(name + "_ML1").setWikiId(name + "_ML1").setTitle(new Coordinate(latitude + 0.0001, longitude)).setLocationName("Loc1").createMeasurementLocation(),
//                    new MeasurementLocationBuilder().setId(name + "_ML2").setWikiId(name + "_ML2").setTitle(new Coordinate(latitude + 0.0002, longitude)).setLocationName("Loc2").createMeasurementLocation()
//            );
//
//            return new Deployment(name, name, new Coordinate(latitude, longitude), from, to, measurementLocations);
//        }
//
//        return new Deployment(name, name, new Coordinate(latitude, longitude), from, to);
//    }
//
//    public static Deployment createDeployment() {
//        return createDeployment(NAME, FROM, TO, LATITUDE, LONGITUDE, false);
//    }
//
//    public static Set<Deployment> createDeployments() {
//        Deployment deployment1 = createDeployment(NAME, createDate("1/01/2001"), createDate("1/10/2002"), LATITUDE, LONGITUDE, false);
//        Deployment deployment2 = createDeployment("Deployment2", createDate("1/01/2001"), createDate("1/10/2015"), LATITUDE + 0.2, LONGITUDE + 0.1
//                , true);
//
//        return Sets.newHashSet(deployment1, deployment2);
//
//    }
//
//    public static Date createDate(String date) {
//        try {
//            return dateFormat.parse(date);
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
