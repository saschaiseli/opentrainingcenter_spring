package ch.opentrainingcenter.gui.view;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.events.EventBusListener;

import com.vaadin.navigator.View;
import com.vaadin.server.Responsive;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import ch.opentrainingcenter.business.service.TrainingService;
import ch.opentrainingcenter.gui.component.ChartKachelComponent;

@SuppressWarnings("serial")
@Secured({ "ROLE_ADMIN" })
@SpringView(name = DashboardView.VIEW_NAME)
public class DashboardView extends VerticalLayout implements View, EventBusListener<Object> {

	@Autowired
	private TrainingService service;

	public static final String VIEW_NAME = "Dashboard";

	@Autowired
	private EventBus.UIEventBus eventBus;

	private final List<ChartKachelComponent> kacheln = new ArrayList<>();

	@PostConstruct
	public void init() {
		addStyleName(ValoTheme.PANEL_BORDERLESS);
		addComponent(new Label("Dashboard"));
		setSizeFull();
		addComponent(createKacheln());
		addStyleName("dashboard-view");
		Responsive.makeResponsive(this);
		eventBus.subscribe(this);
	}

	private Component createKacheln() {
		final String email = SecurityContextHolder.getContext().getAuthentication().getName();

		final GridLayout sparks = new GridLayout(3, 2);
		sparks.setWidth("100%");

		Responsive.makeResponsive(sparks);

		kacheln.add(new ChartKachelComponent(service, email, ChronoUnit.WEEKS, 8));
		kacheln.add(new ChartKachelComponent(service, email, ChronoUnit.MONTHS, 4));
		kacheln.add(new ChartKachelComponent(service, email, ChronoUnit.YEARS, 3));

		kacheln.forEach(k -> sparks.addComponent(k));

		return sparks;
	}

	@Override
	public void onEvent(final org.vaadin.spring.events.Event<Object> event) {
		kacheln.forEach(k -> k.update());
	}

	@PreDestroy
	void destroy() {
		eventBus.unsubscribe(this);
	}
}
