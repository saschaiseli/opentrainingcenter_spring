package ch.opentrainingcenter.gui.ui;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.events.EventBus;

import com.vaadin.annotations.Theme;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.AbstractOrderedLayout;
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
import ch.opentrainingcenter.gui.view.RuleView;
import ch.opentrainingcenter.gui.view.SettingsView;

@Theme(ValoTheme.THEME_NAME)
@SpringUI(path = "/")
@SpringViewDisplay
public class MainUI extends UI implements ViewDisplay {

	private static final Logger LOGGER = LoggerFactory.getLogger(MainUI.class);

	private static final long serialVersionUID = 1L;

	private Panel contentPanel;

	@Autowired
	private SpringNavigator springNavigator;

	@Autowired
	private FileUploadHandlerService uploadService;

	@Autowired
	private EventBus.UIEventBus eventBus;

	@PostConstruct
	public void init() {
		springNavigator.setErrorView(ErrorView.class);
		contentPanel = new Panel();

	}

	@Override
	public void init(final VaadinRequest request) {
		if (SecurityUtils.isAdmin()) {
			getUI().getNavigator().navigateTo(DashboardView.VIEW_NAME);
		} else {
			LOGGER.info("not logged in");
			getUI().getPage().setLocation("/login");
		}

		final VerticalLayout rootLayout = new VerticalLayout();
		rootLayout.setSizeFull();

		createHeader(rootLayout);

		final HorizontalLayout menuAndContentLayout = new HorizontalLayout();
		menuAndContentLayout.setSizeFull();
		menuAndContentLayout.setMargin(false);

		final Panel leftMenuPanel = new Panel();
		final VerticalLayout leftMenuLayout = new VerticalLayout();
		leftMenuLayout.setSizeFull();
		createNavigationButton(leftMenuLayout, DashboardView.VIEW_NAME);
		createNavigationButton(leftMenuLayout, ActivityView.VIEW_NAME);
		createNavigationButton(leftMenuLayout, RuleView.VIEW_NAME);
		createNavigationButton(leftMenuLayout, EquipmentView.VIEW_NAME);
		createPopUpButton(leftMenuLayout, "Upload");

		leftMenuPanel.setContent(leftMenuLayout);
		menuAndContentLayout.addComponent(leftMenuPanel);
		menuAndContentLayout.addComponent(contentPanel);

		menuAndContentLayout.setExpandRatio(leftMenuPanel, 0.1f);
		menuAndContentLayout.setExpandRatio(contentPanel, 0.9f);
		rootLayout.addComponent(menuAndContentLayout);

		rootLayout.addComponent(new Label("footer"));
		rootLayout.setExpandRatio(menuAndContentLayout, 1.0f);
		final MarginInfo info = new MarginInfo(false, true, true, true);
		rootLayout.setMargin(info);
		setContent(rootLayout);

	}

	private void createHeader(final VerticalLayout root) {
		final AbsoluteLayout headerLayout = new AbsoluteLayout();
		headerLayout.setWidth("100%");
		headerLayout.setHeight("45px");

		final Button settingsButton = createNavButtonWithIcon(SettingsView.VIEW_NAME, VaadinIcons.USER);
		settingsButton.setDescription("User Einstellungen");
		headerLayout.addComponent(settingsButton, "right: 60px; top: 10px;");

		final Button logoutButton = createNavigationUIButton("Logout", "/login");
		logoutButton.setDescription("Logout");
		headerLayout.addComponent(logoutButton, "right: 0px; top: 10px;");

		headerLayout.setWidth(100, Unit.PERCENTAGE);

		root.addComponent(headerLayout);
	}

	private Button createNavigationUIButton(final String caption, final String url) {
		final Button button = new Button();
		button.addStyleName(ValoTheme.BUTTON_SMALL);
		button.setIcon(VaadinIcons.EXIT);
		button.addClickListener(event -> {
			getUI().getPage().setLocation(url);
		});
		return button;
	}

	private void createPopUpButton(final VerticalLayout layout, final String caption) {
		final Button button = new Button(caption);
		button.setWidth(layout.getWidth(), layout.getWidthUnits());
		layout.addComponent(button);
		button.addClickListener(event -> {
			final FileUploadDialog dialog = new FileUploadDialog(uploadService, eventBus);
			UI.getCurrent().addWindow(dialog);
		});
	}

	private Button createNavButtonWithIcon(final String viewName, final VaadinIcons icon) {
		final Button button = new Button();
		button.addStyleName(ValoTheme.BUTTON_SMALL);
		button.setIcon(icon);
		button.addClickListener(event -> {
			getUI().getNavigator().navigateTo(viewName);
		});
		return button;
	}

	private Button createNavigationButton(final AbstractOrderedLayout layout, final String viewName) {
		final Button button = new Button(viewName);
		button.setWidth(layout.getWidth(), layout.getWidthUnits());
		layout.addComponent(button);
		button.addClickListener(event -> {
			getUI().getNavigator().navigateTo(viewName);
		});
		return button;
	}

	@Override
	public void showView(final View view) {
		contentPanel.setContent((Component) view);
	}

}
