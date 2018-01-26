package ch.opentrainingcenter.gui.model.chart;

import java.awt.Color;
import java.text.DateFormatSymbols;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

import com.byteowls.vaadin.chartjs.ChartJs;
import com.byteowls.vaadin.chartjs.config.ChartConfig;
import com.byteowls.vaadin.chartjs.config.LineChartConfig;
import com.byteowls.vaadin.chartjs.data.Dataset;
import com.byteowls.vaadin.chartjs.data.LineDataset;
import com.byteowls.vaadin.chartjs.utils.ColorUtils;

public class ChartJsComponent {

	private final String borderColor;
	private final String backColor;
	private final String label;
	private LineChartConfig lineConfig;

	public ChartJsComponent(final String label, final Color cBorder, final Color cBack) {
		this.label = label;
		borderColor = getColor(cBorder);
		backColor = getColor(cBack);
	}

	public LineChartConfig createConfig(final Map<Integer, Double> data, final ChronoUnit unit) {
		final List<String> sortedYAxis = createYAxis(data, unit);
		final List<Double> sortedXaxis = new ArrayList<>();
		data.keySet().stream().sorted().map(f -> sortedXaxis.add(data.get(f))).count();

		lineConfig = updateLineConfig(sortedXaxis.stream(), sortedYAxis);
		updateData(sortedXaxis, lineConfig);

		return lineConfig;
	}

	public ChartJs createChart(final ChartConfig config) {
		return new ChartJs(config);
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
			case YEARS:
				return s;
			default:
				throw new IllegalArgumentException("Wron unit: " + unit);
			}
		};
		final List<String> yAxis = data.keySet().stream().sorted().map(mapper).collect(Collectors.toList());
		return yAxis;
	}

	private LineChartConfig updateLineConfig(final Stream<Double> data, final List<String> yAxis) {
		final DoubleSummaryStatistics stat = getStatistics(data);
		final LineChartConfig lineConfig = LineChartConfigCreator.getChartConfigForDashboard(
				yAxis.toArray(new String[1]), (int) stat.getMin(), (int) stat.getMax(), label);
		return lineConfig;
	}

	private DoubleSummaryStatistics getStatistics(final Stream<Double> data) {
		final DoubleStream stream = data.mapToDouble(Double::valueOf);
		final DoubleSummaryStatistics stat = stream.summaryStatistics();
		return stat;
	}

	private void updateData(final List<Double> values, final LineChartConfig lineConfig) {
		for (final Dataset<?, ?> ds : lineConfig.data().getDatasets()) {
			final LineDataset lds = (LineDataset) ds;
			lds.dataAsList(values);
			lds.borderColor(borderColor);
			lds.backgroundColor(backColor);
		}
	}

	private String getColor(final Color color) {
		return ColorUtils.toRgb(new int[] { color.getRed(), color.getGreen(), color.getBlue() });
	}
}
