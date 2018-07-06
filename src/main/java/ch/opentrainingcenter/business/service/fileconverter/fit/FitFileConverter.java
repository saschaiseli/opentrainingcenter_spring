package ch.opentrainingcenter.business.service.fileconverter.fit;

import ch.opentrainingcenter.business.domain.Training;
import ch.opentrainingcenter.business.service.fileconverter.FileConvertException;
import ch.opentrainingcenter.business.service.fileconverter.FileConverter;
import com.garmin.fit.Decode;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;

@Service(value = "FitFileConverter")
public class FitFileConverter implements FileConverter {

    @Override
    public Training convert(final Path file) throws FileConvertException {
        final TrainingListener listener = new TrainingListener();
        final Decode decode = new Decode();
        try {
            decode.read(new FileInputStream(file.toFile()), listener);
        } catch (final FileNotFoundException e) {
            throw new FileConvertException(e);
        }
        return listener.getTraining();
    }

    @Override
    public String getFileSuffix() {
        return "fit";
    }

}
