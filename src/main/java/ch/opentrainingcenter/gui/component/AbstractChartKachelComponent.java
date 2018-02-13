package ch.opentrainingcenter.gui.component;

import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.byteowls.vaadin.chartjs.ChartJs;
import com.byteowls.vaadin.chartjs.config.ChartConfig;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import ch.opentrainingcenter.business.service.TrainingService;
import ch.opentrainingcenter.gui.model.GRule;

@SuppressWarnings("serial")
public abstract class AbstractChartKachelComponent extends VerticalLayout {
	final ChronoUnit unit;
	private final Label currentValue;
	private final ChartJs chart;
	private final LocalDate now;
	private final TrainingService service;
	private final String email;
	private final int size;
	private final Label currentValueLabel;

	public AbstractChartKachelComponent(final TrainingService service, final String email, final ChronoUnit unit,
			final int size) {
		this.service = service;
		this.email = email;
		this.unit = unit;
		this.size = size;

		setSizeUndefined();
		addStyleName("spark");
		setMargin(false);
		setSpacing(false);
		setDefaultComponentAlignment(Alignment.TOP_CENTER);

		currentValue = new Label("km pro " + unit);
		currentValue.setSizeUndefined();
		currentValue.addStyleName(ValoTheme.LABEL_HUGE);
		addComponent(currentValue);

		currentValueLabel = new Label();
		currentValueLabel.setSizeUndefined();
		currentValueLabel.addStyleName(ValoTheme.LABEL_SMALL);
		currentValueLabel.addStyleName(ValoTheme.LABEL_LIGHT);
		addComponent(currentValueLabel);
		now = LocalDate.now();
		final Map<Integer, Double> data = service.findByEmailAndDate(email, unit, size, now);

		final List<String> yAxis = createYAxis(data, unit);

		final Optional<GRule> rule = service.findGoal(email, unit);
		final ChartConfig chartConfig = getChartConfig(data, yAxis, currentValueLabel, rule);
		chart = new ChartJs(chartConfig);
		chart.setHeight("200px");

		addComponent(chart);
	}

	public abstract ChartConfig getChartConfig(final Map<Integer, Double> data, final List<String> yAxis,
			final Label label, final Optional<GRule> rule);

	public void update() {
		final Map<Integer, Double> data = service.findByEmailAndDate(email, unit, size, now);
		final List<String> yAxis = createYAxis(data, unit);
		final Optional<GRule> rule = service.findGoal(email, unit);
		final ChartConfig chartConfig = getChartConfig(data, yAxis, currentValueLabel, rule);
		chart.configure(chartConfig);
		chart.refreshData();
	}

	private List<String> createYAxis(final Map<Integer, Double> data, final ChronoUnit unit) {
		final Function<Integer, String> mapper = t -> {
			final String s = String.valueOf(t);
			switch (unit) {
			case WEEKS:
				return s.substring(4, s.length()) + " KW";
			case MONTHS:
				final int monat = Integer.valueOf(s.substring(4, s.length())).intValue();
				return new DateFormatSymbols().getMonths()[monat - 1];
			default:
				return s;
			}
		};
		final List<String> yAxis = data.keySet().stream().sorted().map(mapper).collect(Collectors.toList());
		return yAxis;
	}

	String getChartLabel(final ChronoUnit unit) {
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
