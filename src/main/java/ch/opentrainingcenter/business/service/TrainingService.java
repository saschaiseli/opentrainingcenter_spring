package ch.opentrainingcenter.business.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import ch.opentrainingcenter.business.domain.Athlete;
import ch.opentrainingcenter.business.domain.Training;
import ch.opentrainingcenter.business.mapper.toGobject.TrainingToGObject;
import ch.opentrainingcenter.business.repositories.AthleteRepository;
import ch.opentrainingcenter.business.repositories.TrainingRepository;
import ch.opentrainingcenter.business.service.chart.ChartDataAggregator;
import ch.opentrainingcenter.business.service.chart.ChartIndexCompleteService;
import ch.opentrainingcenter.business.util.DateUtil;
import ch.opentrainingcenter.gui.model.GExtendedTraining;
import ch.opentrainingcenter.gui.model.GSimpleTraining;

@Service
@Secured({ "ROLE_ADMIN" })
public class TrainingService {

	@Autowired
	private TrainingRepository repo;

	@Autowired
	private AthleteRepository aRepo;

	@Autowired
	private ChartDataAggregator aggregator;

	@Autowired
	private ChartIndexCompleteService completeService;

	@Autowired
	private TrainingToGObject mapper;

	public List<GSimpleTraining> findByEmail(final String email) {
		return repo.findSimpleTrainingByEmail(email);
	}

	public Map<Integer, Double> findByEmailAndDate(final String email, final ChronoUnit unit, final int size,
			final LocalDate now) {
		final Athlete athlete = aRepo.findByEmail(email);
		final LocalDate lastDayOfWeek = DateUtil.getLastDayOfWeek(now, athlete.getLocale());
		final LocalDateTime start = LocalDateTime.of(lastDayOfWeek.minus(size, unit), LocalTime.of(0, 0));

		final LocalDateTime ldt = LocalDateTime.from(start);
		final long milliStart = ldt.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli();
		final List<GSimpleTraining> trainings = repo.findSimpleTrainingByEmailAndStartDate(email, milliStart);
		final Map<Integer, List<GSimpleTraining>> aggregateToIndex = aggregator.aggregateToIndex(trainings, unit);
		final Map<Integer, Double> raw = aggregator.aggregateToSum(aggregateToIndex,
				t -> (Math.round(t.getLaengeInMeter() * 100) / 100) / 1000d);
		return completeService.fill(raw, unit, now, size);
	}

	public void delete(final long id) {
		repo.delete(id);
	}

	public int countByAthleteEmail(final String name) {
		return repo.countByAthleteEmail(name);
	}

	public GExtendedTraining findById(final long id) {
		final Training training = repo.findTrainingById(id);
		training.getTrackPoints().stream().count();
		return mapper.convert(training);
	}
}
