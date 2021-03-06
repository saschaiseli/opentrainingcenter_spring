package ch.opentrainingcenter.business.service.goal;

import ch.opentrainingcenter.business.domain.Athlete;
import ch.opentrainingcenter.business.domain.Rule;
import ch.opentrainingcenter.business.mapper.toentity.RuleToEntity;
import ch.opentrainingcenter.business.mapper.togobject.RuleToGObject;
import ch.opentrainingcenter.business.repositories.AthleteRepo;
import ch.opentrainingcenter.business.repositories.RuleRepo;
import ch.opentrainingcenter.gui.model.GRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Secured({"ROLE_ADMIN"})
public class GoalService {

    @Autowired
    private AthleteRepo athleteRepo;

    @Autowired
    private RuleRepo ruleRepo;

    @Autowired
    private RuleToGObject mapToGObject;

    @Autowired
    private RuleToEntity mapToEntity;

    public GoalService() {
    }

    public List<GRule> getRulesByUser(final String email) {
        final Athlete athlete = athleteRepo.findByEmail(email);
        final List<Rule> rules = ruleRepo.findByAthlete(athlete);
        return rules.stream().map(t -> mapToGObject.convert(t)).collect(Collectors.toList());
    }

    public long saveOrUpdate(final GRule grule, final String email) {
        final Rule rule = mapToEntity.convert(grule);
        final Athlete athlete = athleteRepo.findByEmail(email);
        rule.setAthlete(athlete);
        return ruleRepo.save(rule).getId();
    }

    public void deleteRule(final long id) {
        ruleRepo.delete(id);
    }

    /**
     * 4 Tests
     */
    protected void setRuleRepo(final RuleRepo ruleRepo) {
        this.ruleRepo = ruleRepo;
    }

    /**
     * 4 Tests
     */
    protected void setRuleToGObject(final RuleToGObject mapper) {
        this.mapToGObject = mapper;
    }

    /**
     * 4 Tests
     */
    protected void setAthleteRepository(final AthleteRepo athleteRepo) {
        this.athleteRepo = athleteRepo;
    }

}
