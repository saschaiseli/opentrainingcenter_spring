package ch.opentrainingcenter.business.service;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

@Service
@Secured({ "ROLE_ADMIN" })
public class FileUploadHandlerService {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadHandlerService.class);

	public void handleFile(final File file) {
		LOGGER.info("Handle file....");
	}

}
