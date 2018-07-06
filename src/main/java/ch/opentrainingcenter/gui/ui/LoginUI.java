package ch.opentrainingcenter.gui.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.vaadin.spring.security.shared.VaadinSharedSecurity;

@SpringUI(path = "/login")
@Theme(ValoTheme.THEME_NAME)
public class LoginUI extends UI {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginUI.class);
    @Autowired
    VaadinSharedSecurity vaadinSecurity;

    private TextField userName;

    private PasswordField passwordField;

    private CheckBox rememberMe;

    private Button login;

    private Label loginFailedLabel;
    private Label loggedOutLabel;

    @Override
    protected void init(final VaadinRequest request) {
        getPage().setTitle("Vaadin Security Demo Login");

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

        final VerticalLayout loginLayout = new VerticalLayout();
        loginLayout.setSpacing(true);
        loginLayout.setSizeUndefined();

        if (request.getParameter("logout") != null) {
            loggedOutLabel = new Label("You have been logged out!");
            loggedOutLabel.addStyleName(ValoTheme.LABEL_SUCCESS);
            loggedOutLabel.setSizeUndefined();
            loginLayout.addComponent(loggedOutLabel);
            loginLayout.setComponentAlignment(loggedOutLabel, Alignment.BOTTOM_CENTER);
        }

        loginLayout.addComponent(loginFailedLabel = new Label());
        loginLayout.setComponentAlignment(loginFailedLabel, Alignment.BOTTOM_CENTER);
        loginFailedLabel.setSizeUndefined();
        loginFailedLabel.addStyleName(ValoTheme.LABEL_FAILURE);
        loginFailedLabel.setVisible(false);

        loginLayout.addComponent(loginForm);
        loginLayout.setComponentAlignment(loginForm, Alignment.TOP_CENTER);

        final VerticalLayout rootLayout = new VerticalLayout(loginLayout);
        rootLayout.setSizeFull();
        rootLayout.setComponentAlignment(loginLayout, Alignment.MIDDLE_CENTER);
        setContent(rootLayout);
        setSizeFull();
    }

    private void login() {
        try {
            vaadinSecurity.login(userName.getValue(), passwordField.getValue(), rememberMe.getValue());
        } catch (final AuthenticationException ex) {
            userName.focus();
            userName.selectAll();
            passwordField.setValue("");
            loginFailedLabel.setValue(String.format("Login failed: %s", ex.getMessage()));
            loginFailedLabel.setVisible(true);
            if (loggedOutLabel != null) {
                loggedOutLabel.setVisible(false);
            }
        } catch (final Exception ex) {
            Notification.show("An unexpected error occurred", ex.getMessage(), Notification.Type.ERROR_MESSAGE);
            LOGGER.error("Unexpected error while logging in", ex);
        } finally {
            login.setEnabled(true);
        }
    }
}
