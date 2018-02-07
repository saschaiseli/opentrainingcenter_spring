package ch.opentrainingcenter.gui.component;

import java.awt.Color;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.byteowls.vaadin.chartjs.ChartJs;
import com.byteowls.vaadin.chartjs.data.LineDataset;
import com.byteowls.vaadin.chartjs.options.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import ch.opentrainingcenter.business.service.TrainingService;
import ch.opentrainingcenter.gui.chart.LineChartComponent;

@SuppressWarnings("serial")
public class ChartKachelComponentNG extends VerticalLayout {
	private final ChronoUnit unit;
	private final Label currentValue;
	private final ChartJs chart;
	private final LocalDate now;
	private final TrainingService service;
	private final String email;
	private final int size;

	public ChartKachelComponentNG(final TrainingService service, final String email, final ChronoUnit unit,
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
		now = LocalDate.now();
		final Map<Integer, Double> data = service.findByEmailAndDate(email, unit, size, now);
		final List<String> yAxis = createYAxis(data, unit);
		final LineChartComponent lcc = createLineChartComponent(data, yAxis);
		chart = new ChartJs(lcc.getConfig());

		addComponent(chart);
	}

	public void update() {
		final Map<Integer, Double> data = service.findByEmailAndDate(email, unit, size, now);
		final List<String> yAxis = createYAxis(data, unit);
		final LineChartComponent lcc = createLineChartComponent(data, yAxis);
		chart.configure(lcc.getConfig());
		chart.refreshData();
	}

	private LineChartComponent createLineChartComponent(final Map<Integer, Double> data, final List<String> yAxis) {
		final LineChartComponent lcc = new LineChartComponent();
		final LineDataset line = lcc.addLine(data, getChartLabel(unit), Position.TOP, Color.BLUE, Color.CYAN);
		line.pointRadius(2).pointHoverRadius(4).borderWidth(2).pointBorderWidth(1);
		lcc.getConfig().data().labels(yAxis.toArray(new String[1]));
		return lcc;
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
