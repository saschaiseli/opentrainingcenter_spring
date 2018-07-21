package ch.opentrainingcenter.business.mapper.togobject;

import ch.opentrainingcenter.business.domain.*;
import ch.opentrainingcenter.gui.model.GExtendedTraining;
import ch.opentrainingcenter.gui.model.GSimpleTraining;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TrainingToGObjectTest {

    private final TrainingToGObject converter = new TrainingToGObject();
    private final TrainingToSimpleGObject simple = new TrainingToSimpleGObject();

    @Test
    public void test() {
        final RunData runData = new RunData(new Date(), 10L, 100L, 5.5d);
        final HeartRate heart = new HeartRate(120, 180);
        final String remark = "remark";
        final Weather wetter = new Weather();
        final Route strecke = new Route();
        final Training t = new Training(runData, heart, remark, wetter, strecke);
        final List<Tracktrainingproperty> tp = new ArrayList<>();
        tp.add(new Tracktrainingproperty(10d, 20, 30, 12L, 1, 12d, 13d));
        tp.add(new Tracktrainingproperty(20d, 30, 55, 32L, 1, 12d, 13d));
        t.setTrackPoints(tp);
        t.setUpMeter(100);
        t.setDownMeter(200);
        t.setGeoQuality(42);
        t.setTrainingType(TrainingType.EXT_INTERVALL);

        final GSimpleTraining simpleTraining = simple.convert(t);

        final GExtendedTraining g = converter.convert(t);

        assertEquals("Max Speed", "5.5", g.getMaxSpeed());
        assertEquals("Down", "200", g.getDown());
        assertEquals("Up", "100", g.getUp());
        assertEquals("Quality", "42", g.getQuality());
        assertEquals("Simpletraining", simpleTraining.getId(), g.getSimpleTraininig().getId());
        assertEquals("Anzahl Points", 2, g.getTrackPoints().size());
        assertEquals("TrainingType", TrainingType.EXT_INTERVALL, g.getTrainingType());
    }

}
