package ch.opentrainingcenter.business.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ch.opentrainingcenter.business.domain.Athlete;
import ch.opentrainingcenter.business.domain.CommonTransferFactory;
import ch.opentrainingcenter.business.domain.HeartRate;
import ch.opentrainingcenter.business.domain.RunData;
import ch.opentrainingcenter.business.domain.Training;
import ch.opentrainingcenter.gui.model.GSimpleTraining;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@PropertySource("classpath:application-test.properties")
@SpringBootTest(classes = { AppConfig.class })
public class TrainingRepositoryTest {

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
		athleteFromDb = athleteRepo.save(athlete);

		final RunData runData = new RunData(now, 102, 1234, 4.5);
		final HeartRate heart = new HeartRate(120, 180);
		training = CommonTransferFactory.createTraining(runData, heart);
		training.setAthlete(athleteFromDb);
		training.setTrainingEffect(5);
		training.setDateOfImport(now);
	}

	@After
	public void cleanUp() {
		repo.delete(training);
		athleteRepo.delete(athlete);
	}

	@Test
	public void test() {
		training = repo.save(training);

		final List<GSimpleTraining> tr = repo.findSimpleTrainingByEmail(EMAIL);
		assertFalse(tr.isEmpty());
	}

	@Test
	public void testfindSimpleTrainingByEmailAndStartDateFound() {
		training.setId(10);
		training = repo.save(training);
		final List<GSimpleTraining> tr = repo.findSimpleTrainingByEmailAndStartDate(EMAIL, 0);
		assertFalse("Trainingsdatum > start", tr.isEmpty());
	}

	@Test
	public void testfindSimpleTrainingByEmailAndStartDateFoundEqual() {
		training.setId(10);
		training = repo.save(training);
		final List<GSimpleTraining> tr = repo.findSimpleTrainingByEmailAndStartDate(EMAIL, 10);
		assertFalse("Trainingsdatum >= start", tr.isEmpty());
	}

	@Test
	public void testfindSimpleTrainingByEmailAndStartDateNotFound() {
		training.setId(9);
		training = repo.save(training);
		final List<GSimpleTraining> tr = repo.findSimpleTrainingByEmailAndStartDate(EMAIL, 10);
		assertTrue("Trainingsdatum < start", tr.isEmpty());
	}

	@Test
	public void testCountByAthleteEmail() {
		training = repo.save(training);

		final int size = repo.countByAthleteEmail(EMAIL);
		assertEquals(1, size);
	}
}
