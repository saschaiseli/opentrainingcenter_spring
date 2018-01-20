package ch.opentrainingcenter.business.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.opentrainingcenter.business.domain.Rule;

public interface RuleRepo extends JpaRepository<Rule, Long> {

}
