package ch.opentrainingcenter.business.service.goal;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ch.opentrainingcenter.business.domain.Athlete;
import ch.opentrainingcenter.business.domain.Rule;
import ch.opentrainingcenter.business.mapper.RuleToGObject;
import ch.opentrainingcenter.business.repositories.AthleteRepository;
import ch.opentrainingcenter.business.repositories.RuleRepo;
import ch.opentrainingcenter.gui.model.GRule;

@Service
@Secured({ "ROLE_ADMIN" })
public class GoalService {
	@Autowired
	private AthleteRepository athleteRepo;

	@Autowired
	private RuleRepo ruleRepo;

	@Autowired
	private RuleToGObject mapper;

	public List<GRule> getRulesByUser() {
		final String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		final Athlete athlete = athleteRepo.findByEmail(email);
		final List<Rule> rules = ruleRepo.findByAthlete(athlete);
		return rules.stream().map(t -> mapper.convert(t)).collect(Collectors.toList());
	}
}
