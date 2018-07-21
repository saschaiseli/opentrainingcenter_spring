package ch.opentrainingcenter.business.mapper.togobject;

import ch.opentrainingcenter.business.domain.Training;
import ch.opentrainingcenter.gui.model.GSimpleTraining;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TrainingToSimpleGObjectTest {

    private final TrainingToSimpleGObject converter = new TrainingToSimpleGObject();

    @Test
    public void test() {
        final Training t = new Training();
        t.setId(42);
        t.setDauer(123L);
        t.setLaengeInMeter(12345L);
        t.setAverageHeartBeat(111);
        t.setMaxHeartBeat(222);
        t.setTrainingEffect(49);

        final GSimpleTraining result = converter.convert(t);

        assertEquals(42, result.getId());
        assertEquals("00:02:03", result.getDuration());
        assertEquals(12345L, result.getLaengeInMeter());
        assertEquals(111, result.getAverageHeartBeat());
        assertEquals(222, result.getMaxHeartBeat());
        assertEquals(49, result.getTrainingEffect().intValue());
    }

}
