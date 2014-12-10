package ch.epfl.osper.export.json;


import ch.epfl.osper.metadata.model.MockFactory;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.Date;

public class DeploymentJSONWriterTest {

    private JSONWriter subject = new JSONWriter();

    public static final String NAME = "Deployment1";
    public static final Date FROM = new Date();
    public static final Date TO = new Date();
    public static final double LATITUDE = 0.4777;
    public static final double LONGITUDE = 9.4777;



    @Test
    public void writeWithoutMasurementLocation() throws Exception {
        String json = subject.writeDeployments(Sets.newHashSet(MockFactory.createDeployment(NAME, FROM, null, LATITUDE, LONGITUDE, false)));
        System.out.println(json);

        Date date = MockFactory.createDate("1/01/2001");
        System.out.println("date = " + date);
    }

}