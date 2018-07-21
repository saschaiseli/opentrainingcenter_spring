package ch.opentrainingcenter.business.mapper.togobject;

import ch.opentrainingcenter.business.domain.Tracktrainingproperty;
import ch.opentrainingcenter.business.domain.Training;
import ch.opentrainingcenter.gui.model.GExtendedData;
import ch.opentrainingcenter.gui.model.GExtendedTraining;
import ch.opentrainingcenter.gui.model.GSimpleTraining;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Secured({"ROLE_ADMIN"})
public class TrainingToGObject implements Converter<Training, GExtendedTraining> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingToGObject.class);
    private final TrainingToSimpleGObject mapper = new TrainingToSimpleGObject();
    private final TrackpointToGObject pointMapper = new TrackpointToGObject();

    @Override
    public GExtendedTraining convert(final Training t) {
        final Instant start = Instant.now();
        final GSimpleTraining simpleTraining = mapper.convert(t);
        final GExtendedTraining result = new GExtendedTraining(simpleTraining);
        final List<Tracktrainingproperty> trackPoints = t.getTrackPoints();
        result.addTrackPoints(trackPoints.stream().map(x -> pointMapper.convert(x)).collect(Collectors.toList()));
        result.setExtendedData(new GExtendedData(t.getUpMeter(), t.getDownMeter(), t.getGeoQuality(), t.getMaxSpeed()));
        result.setTrainingType(t.getTrainingType());

        final Instant end = Instant.now();
        final Duration timeElapsed = Duration.between(start, end);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(String.format("Convertig trackpoints : %s [ms]", timeElapsed.toMillis()));
        }
        return result;
    }

}
