package ch.opentrainingcenter.business.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.opentrainingcenter.business.domain.Training;
import ch.opentrainingcenter.gui.model.GSimpleTraining;

public interface TrainingRepository extends JpaRepository<Training, Long> {

	public List<GSimpleTraining> findSimpleTrainingByEmail(String email);

	public List<Training> findTrainingByEmail(String email);

	public List<GSimpleTraining> findSimpleTrainingByEmailAndStartDate(String email, long milliStart);

	public int countByAthleteEmail(String email);

	public Training findTrainingById(long id);
}
