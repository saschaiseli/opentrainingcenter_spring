package ch.opentrainingcenter.business.repositories;

import java.time.LocalDate;

public interface CustomTrainingRepository {
	public void findSimpleTrainingByEmailAndPeriod(String email, LocalDate start, LocalDate now);
}
