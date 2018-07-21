package ch.opentrainingcenter.business.repositories;

import ch.opentrainingcenter.business.domain.Training;
import ch.opentrainingcenter.gui.model.GSimpleTraining;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainingRepo extends JpaRepository<Training, Long> {

    List<GSimpleTraining> findSimpleTrainingByEmail(String email);

    List<Training> findTrainingByEmail(String email);

    List<GSimpleTraining> findSimpleTrainingByEmailAndStartDate(String email, long milliStart);

    int countByAthleteEmail(String email);

    Training findTrainingById(long id);
}
