package ch.opentrainingcenter.business.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.opentrainingcenter.business.domain.Athlete;
import ch.opentrainingcenter.business.domain.Rule;

public interface RuleRepo extends JpaRepository<Rule, Long> {

	List<Rule> findByAthlete(Athlete athlete);

}
