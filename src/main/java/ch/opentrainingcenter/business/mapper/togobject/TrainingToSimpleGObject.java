package ch.opentrainingcenter.business.mapper.togobject;

import ch.opentrainingcenter.business.domain.Training;
import ch.opentrainingcenter.gui.model.GSimpleTraining;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

@Service
@Secured({"ROLE_ADMIN"})
public class TrainingToSimpleGObject implements Converter<Training, GSimpleTraining> {

    @Override
    public GSimpleTraining convert(final Training t) {
        return new GSimpleTraining(t.getId(), t.getDauer(), t.getLaengeInMeter(), t.getAverageHeartBeat(),
                t.getMaxHeartBeat(), t.getTrainingEffect());
    }

}
