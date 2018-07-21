package ch.opentrainingcenter.gui.dataprovider;

import ch.opentrainingcenter.business.service.TrainingService;
import ch.opentrainingcenter.gui.model.GSimpleTraining;
import com.vaadin.data.provider.AbstractBackEndDataProvider;
import com.vaadin.data.provider.Query;
import com.vaadin.server.SerializablePredicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class GSimpleTrainingDataProvider
        extends AbstractBackEndDataProvider<GSimpleTraining, SerializablePredicate<GSimpleTraining>> {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(GSimpleTrainingDataProvider.class);

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
        return trainingService.countByAthleteEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }

}
