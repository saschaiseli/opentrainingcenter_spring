package ch.opentrainingcenter.business.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.opentrainingcenter.business.domain.Training;

public interface TrainingRepository extends JpaRepository<Training, Long> {

}
