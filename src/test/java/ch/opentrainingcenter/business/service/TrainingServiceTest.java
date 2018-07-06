package ch.opentrainingcenter.business.service;

import ch.opentrainingcenter.business.domain.Rule;
import ch.opentrainingcenter.business.domain.Section;
import ch.opentrainingcenter.business.mapper.togobject.RuleToGObject;
import ch.opentrainingcenter.business.repositories.AthleteRepo;
import ch.opentrainingcenter.business.repositories.RuleRepo;
import ch.opentrainingcenter.gui.model.GRule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
// @ActiveProfiles("test")
// @PropertySource("classpath:application-test.properties")
// @SpringBootTest(classes = { AppConfig.class })
public class TrainingServiceTest {

    // private static final int DISTANZ = 1234;
    //
    private TrainingService service;

    //
    private static final String EMAIL = "email@test.ch";

    private RuleRepo ruleRepo;

    private List<Rule> rules;

    @Before
    public void setUp() {
        service = new TrainingService();
        ruleRepo = mock(RuleRepo.class);
        service.setRuleRepo(ruleRepo);
        service.setAthleteRepo(mock(AthleteRepo.class));
        rules = new ArrayList<>();
    }

    @Test
    @WithMockUser(username = "dummy", roles = {"ADMIN"})
    public void testOneMatchingRule() {
        final Section section = Section.PER_MONTH;
        final int distance = 142;
        rules.add(createRules(section, distance));

        when(ruleRepo.findByAthlete(any())).thenReturn(rules);
        service.setRuleToGObject(new RuleToGObject());

        // when
        final Optional<GRule> result = service.findGoal(EMAIL, section.getChronoUnit());

        // then
        assertEquals(142L, result.get().getValue());
    }

    @Test
    @WithMockUser(username = "dummy", roles = {"ADMIN"})
    public void testTwoMatchingRules() {
        final Section section = Section.PER_MONTH;
        final int distance = 142;
        rules.add(createRules(section, distance));
        rules.add(createRules(section, 200));

        when(ruleRepo.findByAthlete(any())).thenReturn(rules);
        service.setRuleToGObject(new RuleToGObject());

        // when
        final Optional<GRule> result = service.findGoal(EMAIL, section.getChronoUnit());

        // then
        assertEquals(142L, result.get().getValue());
    }

    @Test
    @WithMockUser(username = "dummy", roles = {"ADMIN"})
    public void testNoMatchingRule() {
        final Section section = Section.PER_MONTH;
        final int distance = 142;
        rules.add(createRules(section, distance));
        rules.add(createRules(section, 200));

        when(ruleRepo.findByAthlete(any())).thenReturn(rules);
        service.setRuleToGObject(new RuleToGObject());

        // when
        final Optional<GRule> result = service.findGoal(EMAIL, Section.PER_WEEK.getChronoUnit());

        // then
        assertFalse(result.isPresent());
    }

    private Rule createRules(final Section section, final int distance) {
        final Rule rule = new Rule();
        rule.setSection(section);
        rule.setValue(distance);
        return rule;
    }
}
