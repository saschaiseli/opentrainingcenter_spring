package ch.opentrainingcenter.business.repositories.internal;

import ch.opentrainingcenter.business.repositories.CustomTrainingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDate;

public class CustomTrainingRepoImpl implements CustomTrainingRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomTrainingRepoImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public void findSimpleTrainingByEmailAndPeriod(final String email, final LocalDate start, final LocalDate now) {
        LOGGER.info("x");
        final String sql = "";
        final Query query = em.createQuery(sql);
        query.getResultList();
    }

}
