package ch.opentrainingcenter.gui.fileupload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.events.EventScope;

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.Window;

import ch.opentrainingcenter.business.service.FileUploadHandlerService;
import ch.opentrainingcenter.business.service.fileconverter.FileConvertException;

public class FileUploadReceiver implements Receiver, SucceededListener {

	private static final String ERROR = "Fehler beim upload: <br/>";

	private static final String ERROR_CONVERT = "Fehler beim konvertieren: <br/>";

	private static final String SUCCESS = "File %s hochgeladen";

	private static final long serialVersionUID = 1L;

	private final FileUploadHandlerService uploadService;

	private final Window window;

	private Path path;

	private final EventBus.UIEventBus eventBus;

	public FileUploadReceiver(final FileUploadHandlerService uploadService, final Window window,
			final EventBus.UIEventBus eventBus) {
		this.uploadService = uploadService;
		this.window = window;
		this.eventBus = eventBus;
	}

	@Override
	public OutputStream receiveUpload(final String filename, final String mimeType) {
		window.close();
		FileOutputStream fos = null;
		try {
			final Path dir = Files.createTempDirectory("opentrainingcenter_");
			final File file = new File(dir.toString() + "/" + filename);
			path = file.toPath();
			fos = new FileOutputStream(file);
		} catch (final IOException e) {
			new Notification(ERROR, e.getMessage(), Type.ERROR_MESSAGE, true).show(Page.getCurrent());
			return null;
		}
		return fos;
	}

	@Override
	public void uploadSucceeded(final SucceededEvent event) {
		final String message = String.format(SUCCESS, event.getFilename());
		try {
			uploadService.handleFile(path);
		} catch (final FileConvertException e) {
			new Notification(ERROR_CONVERT, e.getMessage(), Type.ERROR_MESSAGE, true).show(Page.getCurrent());
			return;
		}
		eventBus.publish(EventScope.APPLICATION, "", "Hello World from Application");
		new Notification(message, Type.HUMANIZED_MESSAGE).show(Page.getCurrent());
	}

}
