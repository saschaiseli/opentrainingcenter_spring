package ch.opentrainingcenter.business.service;

import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ch.opentrainingcenter.business.domain.Athlete;
import ch.opentrainingcenter.business.domain.Training;
import ch.opentrainingcenter.business.repositories.AthleteRepo;
import ch.opentrainingcenter.business.repositories.TrainingRepo;
import ch.opentrainingcenter.business.service.fileconverter.FileConvertException;
import ch.opentrainingcenter.business.service.fileconverter.fit.FitFileConverter;

@Service
@Secured({ "ROLE_ADMIN" })
public class FileUploadHandlerService {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadHandlerService.class);

	@Autowired
	private FitFileConverter converter;

	@Autowired
	TrainingRepo repository;

	@Autowired
	AthleteRepo athleteRepository;

	public void handleFile(final Path path) throws FileConvertException {
		LOGGER.info("Handle file....");
		final long start = System.currentTimeMillis();
		final Training training = converter.convert(path);
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		final Athlete athlete = athleteRepository.findByEmail(authentication.getName());
		training.setAthlete(athlete);
		final Instant instant = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant();
		training.setDateOfImport(Date.from(instant));
		repository.save(training);
		LOGGER.info("File converted and stored in db: " + (System.currentTimeMillis() - start) + " [ms]");
	}

}
