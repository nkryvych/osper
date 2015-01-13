package ch.epfl.osper.mongodb;

import ch.epfl.osper.metadata.model.MeasurementRecord;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Box;
import org.springframework.data.geo.Point;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
public class MeasurementRecordRepositoryTest {

    @Autowired
    private MeasurementRecordRepository repository;

    @Test
    public void findByservedPropertiesTest() throws Exception {
        List<MeasurementRecord> result = repository.findByObservedPropertyNames(Lists.newArrayList("Wind Speed (Westerly)"));
        assertThat(result.size(), is(8));
    }

    @Test
    public void findByMeasurementLocationPointWithinTest() throws Exception {
        List<MeasurementRecord> result = repository.findByLocationPointWithin(new Box(new Point(46.8007638888, 9.775938888), new Point(46.8007638888, 9.775938888)));
        assertThat(result.size(), is(8));
    }

    @Test
    public void findByMeasurementLocationNameTest() throws Exception {
        List<MeasurementRecord> result = repository.findByMeasurementLocationName("wan2");
        assertThat(result.size(), is(8));
    }

}