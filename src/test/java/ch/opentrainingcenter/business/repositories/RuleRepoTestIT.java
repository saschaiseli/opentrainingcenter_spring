package ch.opentrainingcenter.business.repositories;

import ch.opentrainingcenter.business.domain.Athlete;
import ch.opentrainingcenter.business.domain.Rule;
import ch.opentrainingcenter.business.domain.Section;
import ch.opentrainingcenter.business.domain.Unit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@PropertySource("classpath:application-test.properties")
@SpringBootTest(classes = {AppConfig.class})
public class RuleRepoTestIT {

    @Autowired
    private RuleRepo ruleRepo;
    @Autowired
    private AthleteRepo athleteRepo;
    private Athlete athlete;
    private final String EMAIL = "junit@test.ch";

    @Before
    public void setUp() {
        athlete = new Athlete("firstName", "lastName", EMAIL, "password");
        athlete.setMaxHeartRate(195);
        athlete.setLocaleString("DE");
        athlete = athleteRepo.save(athlete);
    }

    @After
    public void tearDown() {
        athleteRepo.delete(athlete);
    }

    @Test
    public void testSaveRule() {
        final Rule rule = new Rule();
        rule.setId(1);
        rule.setUnit(Unit.DISTANCE);
        rule.setValue(123L);
        rule.setSection(Section.PER_MONTH);
        rule.setAthlete(athlete);

        final Rule savedRule = ruleRepo.save(rule);
        assertNotNull(savedRule);

        final List<Rule> rules = ruleRepo.findByAthlete(athlete);

        assertEquals(savedRule.getId(), rules.get(0).getId());
    }
}
