package ch.opentrainingcenter.business.repositories;

import ch.opentrainingcenter.business.domain.Athlete;
import ch.opentrainingcenter.business.domain.Rule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RuleRepo extends JpaRepository<Rule, Long> {

    List<Rule> findByAthlete(Athlete athlete);

}
