package ch.opentrainingcenter.business.repositories;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface CustomTrainingRepository {
    void findSimpleTrainingByEmailAndPeriod(String email, LocalDate start, LocalDate now);
}
