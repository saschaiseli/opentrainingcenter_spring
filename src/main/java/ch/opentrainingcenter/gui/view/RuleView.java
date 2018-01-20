package ch.opentrainingcenter.gui.view;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@Secured({ "ROLE_ADMIN" })
@SpringView(name = RuleView.VIEW_NAME)
public class RuleView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;

	public static final String VIEW_NAME = "Ziele";

	@Autowired

	@PostConstruct
	public void init() {
		addComponent(new Label("Ziele:"));
	}
}
