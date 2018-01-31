package ch.opentrainingcenter.gui.dialog;

import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
public class OtcModalDialog extends Window {

	private final VerticalLayout layout;

	public OtcModalDialog(final String caption) {
		super(caption);
		center();
		setClosable(true);
		setModal(true);
		setHeight("350px");
		setWidth("450px");

		layout = new VerticalLayout();
	}

	void addComponent(final Component c) {
		layout.addComponent(c);
	}

	void setContent() {
		setContent(layout);
	}
}
