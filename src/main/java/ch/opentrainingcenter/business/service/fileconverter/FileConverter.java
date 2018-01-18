package ch.opentrainingcenter.business.service.fileconverter;

import java.nio.file.Path;

import org.springframework.stereotype.Service;

import ch.opentrainingcenter.business.domain.Training;

@Service
public interface FileConverter {

	Training convert(final Path file) throws FileConvertException;

	String getFileSuffix();
}
