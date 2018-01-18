package ch.opentrainingcenter.gui.view;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import ch.opentrainingcenter.business.domain.Athlete;
import ch.opentrainingcenter.business.repositories.AthleteRepository;

@Secured({ "ROLE_ADMIN" })
@SpringView(name = AdminView.VIEW_NAME)
public class AdminView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;

	public static final String VIEW_NAME = "admin";

	@Autowired
	AthleteRepository repo;

	@PostConstruct
	public void init() {
		addComponent(new Label("Hello, this is admin view."));

		final List<Athlete> users = repo.findAll();

		final Grid<Athlete> grid = new Grid<>();
		grid.setSizeFull();
		grid.setItems(users);
		grid.addColumn(Athlete::getUsername).setCaption("Name");
		grid.addColumn(Athlete::getPassword).setCaption("Password");

		addComponent(grid);
		setExpandRatio(grid, 1f);
	}

	@Override
	public void enter(final ViewChangeListener.ViewChangeEvent event) {

	}
}
