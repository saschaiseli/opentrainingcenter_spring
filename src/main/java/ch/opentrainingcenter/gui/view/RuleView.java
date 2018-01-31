package ch.opentrainingcenter.gui.view;

import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import ch.opentrainingcenter.business.service.goal.GoalService;
import ch.opentrainingcenter.gui.dialog.RuleDialog;
import ch.opentrainingcenter.gui.model.GRule;

@Secured({ "ROLE_ADMIN" })
@SpringView(name = RuleView.VIEW_NAME)
public class RuleView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;

	public static final String VIEW_NAME = "Ziele";

	@Autowired
	private GoalService goalService;

	private String email;

	private Grid<GRule> grid;

	@PostConstruct
	public void init() {
		addComponent(new Label("Beschreibung"));

		email = SecurityContextHolder.getContext().getAuthentication().getName();
		grid = new Grid<>();

		reloadGrid();

		grid.addColumn(GRule::getValue).setId("value").setCaption("Wert");
		grid.addColumn(GRule::getUnit).setId("unit").setCaption("Einheit");
		grid.addColumn(GRule::getSection).setId("section").setCaption("Periode");
		// final Button btn = buildButton(VaadinIcons.CLOSE, r -> deleteRule(r));

		grid.addComponentColumn(this::buildEditButton);
		grid.addComponentColumn(this::buildDeleteButton);

		grid.setSizeFull();

		grid.setSelectionMode(SelectionMode.SINGLE);

		grid.addSelectionListener(event -> {
			final Set<GRule> selected = event.getAllSelectedItems();
			final GRule sel = selected.iterator().next();
			Notification.show(sel + " items selected: ID " + sel.getId());
		});

		addComponent(grid);
		final Button addButton = new Button("Add");
		addComponent(addButton);
		addButton.addClickListener(event -> {
			final RuleDialog dialog = new RuleDialog("Neues Ziel definieren", goalService, email);
			dialog.addCloseListener(closeEvent -> {
				reloadGrid();
			});
			UI.getCurrent().addWindow(dialog);
		});
	}

	private Button buildEditButton(final GRule rule) {
		final Button button = new Button(VaadinIcons.EDIT);
		button.addStyleName(ValoTheme.BUTTON_SMALL);
		button.addClickListener(e -> {
			final RuleDialog dialog = new RuleDialog("Ziel editieren", goalService, email);
			dialog.updateModel(rule);
			dialog.addCloseListener(closeEvent -> {
				reloadGrid();
			});
			UI.getCurrent().addWindow(dialog);
		});
		return button;
	}

	private Button buildDeleteButton(final GRule rule) {
		final Button button = new Button(VaadinIcons.CLOSE);
		button.addStyleName(ValoTheme.BUTTON_SMALL);
		button.addClickListener(e -> deleteRule(rule));
		return button;
	}

	private void deleteRule(final GRule rule) {
		goalService.deleteRule(rule.getId());
		reloadGrid();
	}

	private void reloadGrid() {
		final List<GRule> rules = goalService.getRulesByUser(email);
		grid.setItems(rules);
	}
}
