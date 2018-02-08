package ch.opentrainingcenter.business.repositories;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ch.opentrainingcenter.business.domain.Athlete;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@PropertySource("classpath:application-test.properties")
@SpringBootTest(classes = { AppConfig.class })
public class RepoTestSetUp {

	@Autowired
	private AthleteRepository athleteRepo;

	protected Athlete athlete;

	public final String EMAIL = "junit@test.ch";
	protected Date now;

	@Before
	public void setUp() {
		now = new Date();
		athlete = new Athlete("firstName", "lastName", EMAIL, "password");
		athlete.setMaxHeartRate(195);
		athlete.setLocaleString("DE");
		athlete = athleteRepo.save(athlete);

	}

	@After
	public void tearDown() {
		athleteRepo.delete(athlete);
	}
}
