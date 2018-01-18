package ch.opentrainingcenter.gui.view;

import javax.annotation.PostConstruct;

import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SpringView(name = LoginView.VIEW_NAME)
public class LoginView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;

	public static final String VIEW_NAME = "LoginView";

	private TextField userName;

	private PasswordField passwordField;

	private CheckBox rememberMe;

	private Button login;

	@PostConstruct
	public void init() {
		addComponent(new Label("Login...."));

		final FormLayout loginForm = new FormLayout();
		loginForm.setSizeUndefined();

		userName = new TextField("Username");
		passwordField = new PasswordField("Password");
		rememberMe = new CheckBox("Remember me");
		login = new Button("Login");
		loginForm.addComponent(userName);
		loginForm.addComponent(passwordField);
		loginForm.addComponent(rememberMe);
		loginForm.addComponent(login);
		login.addStyleName(ValoTheme.BUTTON_PRIMARY);
		login.setDisableOnClick(true);
		login.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		login.addClickListener(e -> login());

		addComponent(loginForm);
	}

	private Object login() {
		return null;
	}

	@Override
	public void enter(final ViewChangeListener.ViewChangeEvent event) {

	}
}
