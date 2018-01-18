package ch.opentrainingcenter.business.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.opentrainingcenter.business.domain.Athlete;

public interface AthleteRepository extends JpaRepository<Athlete, Long> {

	Athlete findByEmailAndPassword(String email, String password);

	Athlete findByEmail(String username);
}
