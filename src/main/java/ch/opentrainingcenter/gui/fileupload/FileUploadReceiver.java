package ch.opentrainingcenter.gui.fileupload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.Window;

import ch.opentrainingcenter.business.service.FileUploadHandlerService;

public class FileUploadReceiver implements Receiver, SucceededListener {

	private static final String ERROR = "Fehler beim upload: <br/>";

	private static final String SUCCESS = "File %s hochgeladen";

	private static final long serialVersionUID = 1L;

	private final FileUploadHandlerService uploadService;

	private File file;

	private final Window window;

	public FileUploadReceiver(final FileUploadHandlerService uploadService, final Window window) {
		this.uploadService = uploadService;
		this.window = window;
	}

	@Override
	public OutputStream receiveUpload(final String filename, final String mimeType) {
		FileOutputStream fos = null;
		try {
			final Path dir = Files.createTempDirectory("opentrainingcenter_");
			file = new File(dir.toString() + "/" + filename);
			fos = new FileOutputStream(file);
		} catch (final IOException e) {
			new Notification(ERROR, e.getMessage(), Type.ERROR_MESSAGE, true).show(Page.getCurrent());
			return null;
		}
		return fos;
	}

	@Override
	public void uploadSucceeded(final SucceededEvent event) {
		window.close();
		final String message = String.format(SUCCESS, event.getFilename());
		uploadService.handleFile(file);
		new Notification(message, Type.HUMANIZED_MESSAGE).show(Page.getCurrent());
	}

}
