package ch.opentrainingcenter.business.mapper.toGobject;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import ch.opentrainingcenter.business.domain.Training;
import ch.opentrainingcenter.business.mapper.MapToGObject;
import ch.opentrainingcenter.gui.model.GSimpleTraining;

@Service
@Secured({ "ROLE_ADMIN" })
public class TrainingToSimpleGObject implements MapToGObject<GSimpleTraining, Training> {

	@Override
	public GSimpleTraining convert(final Training t) {
		return new GSimpleTraining(t.getId(), t.getDauer(), t.getLaengeInMeter(), t.getAverageHeartBeat(),
				t.getMaxHeartBeat(), t.getTrainingEffect());
	}

}
