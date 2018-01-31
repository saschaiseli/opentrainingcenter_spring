package ch.opentrainingcenter.business.repositories;

import java.time.LocalDate;

import org.springframework.stereotype.Repository;

@Repository
public interface CustomTrainingRepository {
	public void findSimpleTrainingByEmailAndPeriod(String email, LocalDate start, LocalDate now);
}
