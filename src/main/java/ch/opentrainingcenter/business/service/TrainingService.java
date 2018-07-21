package ch.opentrainingcenter.business.service;

import ch.opentrainingcenter.business.domain.Athlete;
import ch.opentrainingcenter.business.domain.Rule;
import ch.opentrainingcenter.business.domain.Training;
import ch.opentrainingcenter.business.mapper.togobject.RuleToGObject;
import ch.opentrainingcenter.business.mapper.togobject.TrainingToGObject;
import ch.opentrainingcenter.business.repositories.AthleteRepo;
import ch.opentrainingcenter.business.repositories.RuleRepo;
import ch.opentrainingcenter.business.repositories.TrainingRepo;
import ch.opentrainingcenter.business.service.chart.ChartDataAggregator;
import ch.opentrainingcenter.business.service.chart.ChartIndexCompleteService;
import ch.opentrainingcenter.business.util.DateUtil;
import ch.opentrainingcenter.gui.model.GExtendedTraining;
import ch.opentrainingcenter.gui.model.GRule;
import ch.opentrainingcenter.gui.model.GSimpleTraining;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Secured({"ROLE_ADMIN"})
public class TrainingService {

    private AthleteRepo athleteRepo;
    private RuleRepo ruleRepo;
    private RuleToGObject ruleMapper;
    private TrainingRepo trainingRepo;

    @Autowired
    private ChartDataAggregator aggregator;

    @Autowired
    private ChartIndexCompleteService completeService;

    @Autowired
    private TrainingToGObject mapper;

    @Autowired
    public void setAthleteRepo(final AthleteRepo athleteRepo) {
        this.athleteRepo = athleteRepo;
    }

    @Autowired
    public void setRuleRepo(final RuleRepo ruleRepo) {
        this.ruleRepo = ruleRepo;
    }

    @Autowired
    public void setRuleToGObject(final RuleToGObject ruleMapper) {
        this.ruleMapper = ruleMapper;
    }

    @Autowired
    public void setTrainingRepository(final TrainingRepo trainingRepo) {
        this.trainingRepo = trainingRepo;
    }

    public List<GSimpleTraining> findByEmail(final String email) {
        return trainingRepo.findSimpleTrainingByEmail(email);
    }

    public Map<Integer, Double> findByEmailAndDate(final String email, final ChronoUnit unit, final int size,
                                                   final LocalDate now) {
        final Athlete athlete = athleteRepo.findByEmail(email);
        final LocalDate lastDayOfWeek = DateUtil.getLastDayOfWeek(now, athlete.getLocale());
        final LocalDateTime start = LocalDateTime.of(lastDayOfWeek.minus(size, unit), LocalTime.of(0, 0));

        final LocalDateTime ldt = LocalDateTime.from(start);
        final long milliStart = ldt.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli();
        final List<GSimpleTraining> trainings = trainingRepo.findSimpleTrainingByEmailAndStartDate(email, milliStart);
        final Map<Integer, List<GSimpleTraining>> aggregateToIndex = aggregator.aggregateToIndex(trainings, unit);
        final Map<Integer, Double> raw = aggregator.aggregateToSum(aggregateToIndex,
                t -> (Math.round(t.getLaengeInMeter() * 100) / 100) / 1000d);
        return completeService.fill(raw, unit, now, size);
    }

    public void delete(final long id) {
        trainingRepo.delete(id);
    }

    public int countByAthleteEmail(final String name) {
        return trainingRepo.countByAthleteEmail(name);
    }

    public GExtendedTraining findById(final long id) {
        final Training training = trainingRepo.findTrainingById(id);
        training.getTrackPoints().stream().count();
        return mapper.convert(training);
    }

    public Optional<GRule> findGoal(final String email, final ChronoUnit unit) {
        final Athlete athlete = athleteRepo.findByEmail(email);
        final List<Rule> rules = ruleRepo.findByAthlete(athlete);
        return rules.stream()//
                .filter(r -> r.getSection().getChronoUnit().equals(unit))//
                .findFirst()//
                .map(r -> ruleMapper.convert(r));
    }
}
