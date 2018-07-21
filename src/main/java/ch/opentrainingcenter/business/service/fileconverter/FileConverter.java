package ch.opentrainingcenter.business.service.fileconverter;

import ch.opentrainingcenter.business.domain.Training;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service
public interface FileConverter {

    Training convert(final Path file) throws FileConvertException;

    String getFileSuffix();
}
