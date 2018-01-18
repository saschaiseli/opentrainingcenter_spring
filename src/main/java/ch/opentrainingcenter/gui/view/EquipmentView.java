package ch.opentrainingcenter.gui.view;

import javax.annotation.PostConstruct;

import org.springframework.security.access.annotation.Secured;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@Secured({ "ROLE_ADMIN" })
@SpringView(name = EquipmentView.VIEW_NAME)
public class EquipmentView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;

	public static final String VIEW_NAME = "Equipment";

	@PostConstruct
	public void init() {
		addComponent(new Label("equipment..."));
	}
}
