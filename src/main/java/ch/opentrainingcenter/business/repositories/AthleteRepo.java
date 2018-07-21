package ch.opentrainingcenter.business.repositories;

import ch.opentrainingcenter.business.domain.Athlete;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AthleteRepo extends JpaRepository<Athlete, Long> {

    Athlete findByEmailAndPassword(String email, String password);

    Athlete findByEmail(String username);
}
