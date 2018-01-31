package ch.opentrainingcenter.gui.component;

import java.awt.Color;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import com.byteowls.vaadin.chartjs.ChartJs;
import com.byteowls.vaadin.chartjs.config.ChartConfig;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import ch.opentrainingcenter.business.service.TrainingService;
import ch.opentrainingcenter.gui.model.chart.ChartJsComponent;

@SuppressWarnings("serial")
public class ChartKachelComponent extends VerticalLayout {
	private final TrainingService service;
	private final String email;
	private final ChronoUnit unit;
	private final Label currentValue;
	private final LocalDate now;
	private final ChartJs chart;
	private final ChartJsComponent chartJsComponent;
	private final int size;

	public ChartKachelComponent(final TrainingService service, final String email, final ChronoUnit unit,
			final int size) {
		this.service = service;
		this.email = email;
		this.unit = unit;
		this.size = size;

		setSizeUndefined();
		setWidth("100%");
		setMargin(false);
		setSpacing(false);
		setDefaultComponentAlignment(Alignment.TOP_CENTER);

		currentValue = new Label();
		currentValue.setSizeUndefined();
		currentValue.addStyleName(ValoTheme.LABEL_HUGE);
		addComponent(currentValue);

		final Label l = new Label("aktuell");
		l.setSizeUndefined();
		l.addStyleName(ValoTheme.LABEL_SMALL);
		l.addStyleName(ValoTheme.LABEL_LIGHT);
		addComponent(l);
		chartJsComponent = new ChartJsComponent(getChartLabel(unit), Color.BLUE, Color.CYAN);
		now = LocalDate.now();
		final Map<Integer, Double> data = service.findByEmailAndDate(email, unit, size, now);
		final ChartConfig config = chartJsComponent.createConfig(data, unit);
		chart = chartJsComponent.createChart(config);
		addComponent(chart);
	}

	public void update() {
		final Map<Integer, Double> data = service.findByEmailAndDate(email, unit, size, now);
		final ChartConfig config = chartJsComponent.createConfig(data, unit);
		chart.configure(config);
		chart.refreshData();
	}

	private String getChartLabel(final ChronoUnit unit) {
		switch (unit) {
		case WEEKS:
			return "km/Woche";
		case MONTHS:
			return "km/Monat";
		case YEARS:
			return "km/Jahr";
		default:
			throw new IllegalArgumentException("illegal unit: " + unit);
		}
	}
}
