package ch.opentrainingcenter.business.service;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ch.opentrainingcenter.business.domain.Athlete;
import ch.opentrainingcenter.business.domain.CommonTransferFactory;
import ch.opentrainingcenter.business.domain.HeartRate;
import ch.opentrainingcenter.business.domain.RunData;
import ch.opentrainingcenter.business.domain.Training;
import ch.opentrainingcenter.business.repositories.AppConfig;
import ch.opentrainingcenter.business.repositories.AthleteRepository;
import ch.opentrainingcenter.business.repositories.TrainingRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@PropertySource("classpath:application-test.properties")
@SpringBootTest(classes = { AppConfig.class })
public class TrainingServiceTest {

	private static final int DISTANZ = 1234;

	@Autowired
	private TrainingService service;

	private static final String EMAIL = "email@test.ch";

	@Autowired
	private TrainingRepository repo;

	@Autowired
	private AthleteRepository athleteRepo;

	private Athlete athlete;

	private Date now;

	private Training training;

	@Before
	public void setUp() {
		now = new Date();
		athlete = new Athlete("firstName", "lastName", EMAIL, "password");
		athlete.setMaxHeartRate(195);
		athlete.setLocaleString("DE");
		Athlete athleteFromDb = athleteRepo.findByEmail(EMAIL);
		if (athleteFromDb == null) {
			athleteFromDb = athleteRepo.save(athlete);
		}

		final RunData runData = new RunData(now, 102, DISTANZ, 4.5);
		final HeartRate heart = new HeartRate(120, 180);
		training = CommonTransferFactory.createTraining(runData, heart);
		training.setAthlete(athleteFromDb);
		training.setTrainingEffect(5);
		training.setDateOfImport(now);
	}

	@After
	public void cleanUp() {
		repo.delete(training);
	}

	@Test
	@WithMockUser(username = "dummy", roles = { "ADMIN" })
	public void testFindByEmailAndDate_OneTraining() {
		final LocalDateTime ldNow = LocalDateTime.of(2018, 1, 22, 0, 0, 0);
		training.setId(ldNow.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli());
		repo.save(training);

		final Map<Integer, Double> result = service.findByEmailAndDate(EMAIL, ChronoUnit.MONTHS, 12,
				ldNow.toLocalDate());

		assertEquals(Double.valueOf(DISTANZ).doubleValue() / 1000, result.get(201801), 0.01);
	}

	@Test
	@WithMockUser(username = "dummy", roles = { "ADMIN" })
	public void testFindByEmailAndDate_TwoTraining() {
		final LocalDateTime ldNow = LocalDateTime.of(2018, 1, 22, 0, 0, 0);
		training.setId(ldNow.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli());
		repo.save(training);

		training.setId(ldNow.minusDays(2).toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli());
		repo.save(training);

		final Map<Integer, Double> result = service.findByEmailAndDate(EMAIL, ChronoUnit.MONTHS, 12,
				ldNow.toLocalDate());

		// assertEquals(DISTANZ, result.get(201801), 0.00001);
		assertEquals(Double.valueOf(2 * DISTANZ).doubleValue() / 1000, result.get(201801), 0.01);
	}
}
