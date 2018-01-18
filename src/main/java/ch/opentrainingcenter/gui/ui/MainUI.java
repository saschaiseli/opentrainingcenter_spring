package ch.opentrainingcenter.gui.ui;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import ch.opentrainingcenter.business.security.SecurityUtils;
import ch.opentrainingcenter.business.service.FileUploadHandlerService;
import ch.opentrainingcenter.gui.dialog.FileUploadDialog;
import ch.opentrainingcenter.gui.view.ActivityView;
import ch.opentrainingcenter.gui.view.DashboardView;
import ch.opentrainingcenter.gui.view.EquipmentView;
import ch.opentrainingcenter.gui.view.ErrorView;
import ch.opentrainingcenter.gui.view.SettingsView;

@Theme(ValoTheme.THEME_NAME)
@SpringUI(path = "/")
@SpringViewDisplay
public class MainUI extends UI implements ViewDisplay {

	private static final Logger LOGGER = LoggerFactory.getLogger(MainUI.class);

	private static final long serialVersionUID = 1L;

	private Panel mainPanel;
	@Autowired
	private SpringNavigator springNavigator;

	@Autowired
	private FileUploadHandlerService uploadService;

	@PostConstruct
	public void init() {
		springNavigator.setErrorView(ErrorView.class);
		mainPanel = new Panel();

	}

	@Override
	public void init(final VaadinRequest request) {

		if (SecurityUtils.isAdmin()) {
			getUI().getNavigator().navigateTo(DashboardView.VIEW_NAME);
		} else {
			LOGGER.info("not logged in");
			getUI().getPage().setLocation("/login");
		}

		final VerticalLayout root = new VerticalLayout();
		root.setSizeFull();

		root.addComponent(new Label("My Application"));

		final HorizontalLayout menuview = new HorizontalLayout();
		menuview.setSizeFull();

		final Panel menuPanel = new Panel();
		final VerticalLayout menuLayout = new VerticalLayout();
		menuLayout.setSizeFull();
		createNavigationButton(menuLayout, DashboardView.VIEW_NAME);
		createNavigationButton(menuLayout, ActivityView.VIEW_NAME);
		createNavigationButton(menuLayout, EquipmentView.VIEW_NAME);
		createNavigationButton(menuLayout, SettingsView.VIEW_NAME);
		createPopUpButton(menuLayout, "Upload");

		menuPanel.setContent(menuLayout);
		menuview.addComponent(menuPanel);
		menuview.addComponent(mainPanel);

		menuview.setExpandRatio(menuPanel, 0.1f);
		menuview.setExpandRatio(mainPanel, 0.9f);
		root.addComponent(menuview);

		root.addComponent(new Label("footer"));
		root.setExpandRatio(menuview, 1.0f);
		setContent(root);

	}

	private void createPopUpButton(final VerticalLayout layout, final String caption) {
		final Button button = new Button(caption);
		layout.addComponent(button);
		button.addClickListener(event -> {
			final FileUploadDialog dialog = new FileUploadDialog(uploadService);
			UI.getCurrent().addWindow(dialog);
		});
	}

	private void createNavigationButton(final VerticalLayout layout, final String viewName) {
		final Button button = new Button(viewName);
		layout.addComponent(button);
		button.addClickListener(event -> {
			getUI().getNavigator().navigateTo(viewName);
		});
	}

	@Override
	public void showView(final View view) {
		mainPanel.setContent((Component) view);
	}

}
