package ch.opentrainingcenter.business.service.goal;

import ch.opentrainingcenter.business.domain.Athlete;
import ch.opentrainingcenter.business.domain.Rule;
import ch.opentrainingcenter.business.domain.Section;
import ch.opentrainingcenter.business.domain.Unit;
import ch.opentrainingcenter.business.mapper.togobject.RuleToGObject;
import ch.opentrainingcenter.business.repositories.AthleteRepo;
import ch.opentrainingcenter.business.repositories.RuleRepo;
import ch.opentrainingcenter.gui.model.GRule;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GoalServiceTest {
    GoalService service;
    private RuleRepo ruleRepo;
    private AthleteRepo athleteRepo;
    private Athlete athlete;

    @Before
    public void setUp() {
        service = new GoalService();
        ruleRepo = Mockito.mock(RuleRepo.class);
        service.setRuleRepo(ruleRepo);
        athleteRepo = Mockito.mock(AthleteRepo.class);
        service.setAthleteRepository(athleteRepo);
        service.setRuleToGObject(new RuleToGObject());
        athlete = new Athlete();
    }

    @Test
    public void testGetRuleByUser() {
        final List<Rule> rules = new ArrayList<>();
        final Rule rule = new Rule();
        rule.setId(42L);
        rule.setSection(Section.PER_MONTH);
        rule.setUnit(Unit.QUANTITY);
        rule.setValue(1042L);
        rules.add(rule);
        Mockito.when(ruleRepo.findByAthlete(Mockito.any())).thenReturn(rules);
        Mockito.when(athleteRepo.findByEmail(Mockito.anyString())).thenReturn(athlete);

        final GRule grule = service.getRulesByUser("email").get(0);
        assertEquals(42, grule.getId());
        assertEquals(1042, grule.getValue());
        assertEquals(Section.PER_MONTH, grule.getSection());
        assertEquals(Unit.QUANTITY, grule.getUnit());
    }

}
