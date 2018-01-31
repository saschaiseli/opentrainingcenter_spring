package ch.opentrainingcenter.business.repositories;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ch.opentrainingcenter.business.domain.Athlete;
import ch.opentrainingcenter.business.domain.Rule;
import ch.opentrainingcenter.business.domain.Section;
import ch.opentrainingcenter.business.domain.Unit;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@PropertySource("classpath:application-test.properties")
@SpringBootTest(classes = { AppConfig.class })
public class RuleRepoTest {
	@Autowired
	private RuleRepo ruleRepo;

	@Autowired
	private AthleteRepository athleteRepo;

	private Athlete athlete;

	@Before
	public void setUp() {
		athlete = athleteRepo.getOne(1L);
	}

	@Test
	@Sql(scripts = "classpath:db/add-user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(scripts = { "classpath:db/delete-rules.sql",
			"classpath:db/delete-users.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void testSaveRule() {
		final Rule rule = new Rule();
		rule.setId(1);
		rule.setUnit(Unit.DISTANCE);
		rule.setValue(123L);
		rule.setSection(Section.PER_MONTH);
		rule.setAthlete(athlete);

		final Rule savedRule = ruleRepo.save(rule);

		assertNotNull(savedRule);
	}

	@Test
	@Sql(scripts = { "classpath:db/add-user.sql",
			"classpath:db/add-rule.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(scripts = { "classpath:db/delete-rules.sql",
			"classpath:db/delete-users.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void testFindByEmail() {
		final List<Rule> oneRule = ruleRepo.findByAthlete(athlete);

		assertNotNull(oneRule.get(0));
	}
}
