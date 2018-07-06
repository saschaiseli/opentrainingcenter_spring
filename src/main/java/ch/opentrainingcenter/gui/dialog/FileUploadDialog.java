package ch.opentrainingcenter.gui.dialog;

import ch.opentrainingcenter.business.service.FileUploadHandlerService;
import ch.opentrainingcenter.gui.fileupload.FileUploadReceiver;
import com.vaadin.annotations.Theme;
import com.vaadin.ui.Upload;
import com.vaadin.ui.themes.ValoTheme;
import org.vaadin.spring.events.EventBus.UIEventBus;

@Theme(ValoTheme.THEME_NAME)
public class FileUploadDialog extends OtcModalDialog {

    private static final long serialVersionUID = 1L;

    public FileUploadDialog(final FileUploadHandlerService uploadService, final UIEventBus eventBus) {
        super("File Upload");

        final FileUploadReceiver uploadReceiver = new FileUploadReceiver(uploadService, this, eventBus);
        final Upload upload = new Upload("", uploadReceiver);
        upload.addSucceededListener(uploadReceiver);
        upload.setImmediateMode(false);
        upload.setCaption("Do it");
        upload.addStartedListener(event -> {
            this.close();
        });
        addComponent(upload);
        setContent();
    }

}
