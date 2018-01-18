package ch.opentrainingcenter.gui.view;

import javax.annotation.PostConstruct;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SpringComponent
@UIScope
@SpringView
public class ErrorView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;

	@Override
	public void enter(final ViewChangeListener.ViewChangeEvent event) {
	}

	@PostConstruct
	void init() {
		final Label label = new Label("Hello, this is the 'error view' loaded if no view is matched based on URL.");
		label.addStyleName(ValoTheme.LABEL_FAILURE);
		addComponent(label);
	}
}