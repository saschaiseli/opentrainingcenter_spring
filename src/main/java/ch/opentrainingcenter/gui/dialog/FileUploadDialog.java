package ch.opentrainingcenter.gui.dialog;

import com.vaadin.annotations.Theme;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import ch.opentrainingcenter.business.service.FileUploadHandlerService;
import ch.opentrainingcenter.gui.fileupload.FileUploadReceiver;

@Theme(ValoTheme.THEME_NAME)
public class FileUploadDialog extends Window {

	private static final long serialVersionUID = 1L;

	public FileUploadDialog(final FileUploadHandlerService uploadService) {
		super("File Upload");
		center();
		setClosable(true);
		setModal(true);
		setHeight("300px");
		setWidth("400px");

		final HorizontalLayout layout = new HorizontalLayout();

		final FileUploadReceiver uploadReceiver = new FileUploadReceiver(uploadService, this);
		final Upload upload = new Upload("Upload it here", uploadReceiver);
		upload.addSucceededListener(uploadReceiver);
		upload.setImmediateMode(false);
		layout.addComponent(upload);
		setContent(layout);
	}

}
