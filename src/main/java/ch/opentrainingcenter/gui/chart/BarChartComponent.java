package ch.opentrainingcenter.gui.chart;

import java.awt.Color;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

import com.byteowls.vaadin.chartjs.config.BarChartConfig;
import com.byteowls.vaadin.chartjs.data.BarDataset;
import com.byteowls.vaadin.chartjs.options.InteractionMode;
import com.byteowls.vaadin.chartjs.options.Position;
import com.byteowls.vaadin.chartjs.options.scale.Axis;
import com.byteowls.vaadin.chartjs.options.scale.CategoryScale;
import com.byteowls.vaadin.chartjs.options.scale.LinearScale;
import com.byteowls.vaadin.chartjs.options.scale.Scales;
import com.byteowls.vaadin.chartjs.options.types.BarChartOptions;
import com.byteowls.vaadin.chartjs.utils.ColorUtils;

public class BarChartComponent {

	private final BarChartConfig barConfig = new BarChartConfig();

	public BarDataset addLine(final Map<Integer, Double> data, final String label, final Position position,
			final Color borderColor, final Color backColor) {
		final DoubleSummaryStatistics statistics = getStatistics(data.values().stream());
		final int min = 0;
		final int max = (int) (statistics.getMax() * 1.05d);
		final BarDataset lds = new BarDataset();
		final List<Integer> sortedKeys = data.keySet().stream().sorted().collect(Collectors.toList());

		final Scales<BarChartOptions> scales = barConfig.data().addDataset(lds.label(label).yAxisID(label))//
				.and().options().responsive(true).title().and().tooltips().mode(InteractionMode.INDEX).intersect(false)
				.and().hover().mode(InteractionMode.NEAREST).intersect(true).and().scales()
				.add(Axis.Y, new LinearScale().display(true).scaleLabel().display(true).labelString("").and().ticks()
						.suggestedMin(min).suggestedMax(max).and().position(position).id(label));
		if (barConfig.data().getLabels() == null) {
			barConfig.data().labels(
					sortedKeys.stream().map(String::valueOf).collect(Collectors.toList()).toArray(new String[1]));
			scales.add(Axis.X, new CategoryScale().display(true).scaleLabel().display(true) //
					.and().position(Position.TOP));
		}
		scales.and().done();
		sortedKeys.forEach(x -> lds.addData(data.get(x)));
		lds.borderColor(getColor(borderColor));
		lds.backgroundColor(getColor(backColor));
		// lds.pointRadius(0).pointHoverRadius(5);
		barConfig.options().elements().line().borderWidth(1);
		return lds;
	}

	private DoubleSummaryStatistics getStatistics(final Stream<? extends Number> data) {
		final DoubleStream stream = data.mapToDouble(Number::doubleValue);
		final DoubleSummaryStatistics stat = stream.summaryStatistics();
		return stat;
	}

	public BarChartConfig getConfig() {
		return barConfig;
	}

	public static String getColor(final Color color) {
		return ColorUtils.toRgb(new int[] { color.getRed(), color.getGreen(), color.getBlue() });
	}
}
