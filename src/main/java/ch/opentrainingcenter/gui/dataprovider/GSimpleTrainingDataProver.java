package ch.opentrainingcenter.gui.dataprovider;

import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.vaadin.data.provider.AbstractBackEndDataProvider;
import com.vaadin.data.provider.Query;
import com.vaadin.server.SerializablePredicate;

import ch.opentrainingcenter.business.service.TrainingService;
import ch.opentrainingcenter.gui.model.GSimpleTraining;

@Component
public class GSimpleTrainingDataProver
		extends AbstractBackEndDataProvider<GSimpleTraining, SerializablePredicate<GSimpleTraining>> {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(GSimpleTrainingDataProver.class);

	@Autowired
	private TrainingService trainingService;

	@Override
	protected Stream<GSimpleTraining> fetchFromBackEnd(
			final Query<GSimpleTraining, SerializablePredicate<GSimpleTraining>> query) {
		LOGGER.info("fetchFromBackEnd");
		return trainingService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).stream();
	}

	@Override
	protected int sizeInBackEnd(final Query<GSimpleTraining, SerializablePredicate<GSimpleTraining>> query) {
		LOGGER.info("sizeInBackEnd");
		return trainingService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).size();
	}

}
