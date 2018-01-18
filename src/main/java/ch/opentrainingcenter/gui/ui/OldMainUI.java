package ch.opentrainingcenter.gui.ui;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.vaadin.spring.security.VaadinSecurity;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import ch.opentrainingcenter.business.security.SecurityUtils;
import ch.opentrainingcenter.gui.view.AccessDeniedView;
import ch.opentrainingcenter.gui.view.AdminView;
import ch.opentrainingcenter.gui.view.ErrorView;
import ch.opentrainingcenter.gui.view.UserView;

@Theme(ValoTheme.THEME_NAME)
@SpringUI(path = "/old")
@SpringViewDisplay
public class OldMainUI extends UI implements ViewDisplay {

	private static final long serialVersionUID = 1L;

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	VaadinSecurity vaadinSecurity;

	@Autowired
	SpringViewProvider springViewProvider;

	@Autowired
	SpringNavigator springNavigator;

	Panel springViewDisplay;

	private CssLayout navigationBar;

	@PostConstruct
	public void init() {
		springNavigator.setErrorView(ErrorView.class);
		springViewProvider.setAccessDeniedViewClass(AccessDeniedView.class);
		springViewDisplay = new Panel();
		springViewDisplay.setSizeFull();
	}

	@Override
	protected void init(final VaadinRequest request) {
		getPage().setTitle("Vaadin Security Demo");

		navigationBar = new CssLayout();
		navigationBar.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

		if (SecurityUtils.isLoggedIn() && SecurityUtils.hasRole("ADMIN")) {
			navigationBar.addComponent(createNavigationButton("User View", UserView.VIEW_NAME));
			navigationBar.addComponent(createNavigationButton("Admin View", AdminView.VIEW_NAME));
			navigationBar.addComponent(new Button("Logout", e -> vaadinSecurity.logout()));
		} else {
			getUI().getPage().setLocation("/login");
		}

		final VerticalLayout root = new VerticalLayout();
		root.setSizeFull();
		root.setSpacing(true);
		root.setMargin(true);
		root.addComponent(navigationBar);
		root.addComponent(springViewDisplay);
		root.setExpandRatio(springViewDisplay, 1.0f);

		setContent(root);
	}

	// @Override
	// public void showView(final View view) {
	// springViewDisplay.setContent((Component) view);
	// }

	private Button createNavigationButton(final String caption, final String viewName) {
		final Button button = new Button(caption);
		button.addClickListener(event -> {
			getUI().getNavigator().navigateTo(viewName);
		});
		return button;
	}

	@Override
	public void showView(final View view) {
		springViewDisplay.setContent((Component) view);
	}

}
