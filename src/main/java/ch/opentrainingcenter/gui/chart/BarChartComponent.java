package ch.opentrainingcenter.gui.chart;

import java.awt.Color;
import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

import com.byteowls.vaadin.chartjs.config.BarChartConfig;
import com.byteowls.vaadin.chartjs.data.BarDataset;
import com.byteowls.vaadin.chartjs.data.LineDataset;
import com.byteowls.vaadin.chartjs.options.InteractionMode;
import com.byteowls.vaadin.chartjs.options.Position;
import com.byteowls.vaadin.chartjs.options.elements.Line.JoinStyle;
import com.byteowls.vaadin.chartjs.options.scale.Axis;
import com.byteowls.vaadin.chartjs.options.scale.CategoryScale;
import com.byteowls.vaadin.chartjs.options.scale.LinearScale;
import com.byteowls.vaadin.chartjs.options.scale.Scales;
import com.byteowls.vaadin.chartjs.options.types.BarChartOptions;
import com.byteowls.vaadin.chartjs.utils.ColorUtils;

import ch.opentrainingcenter.gui.model.GRule;

public class BarChartComponent {

	private final BarChartConfig barConfig = new BarChartConfig();

	public BarDataset addLine(final Map<Integer, Double> data, final Optional<GRule> rule, final String label,
			final Position pos, final Color border, final Color back) {
		final DoubleSummaryStatistics statistics = getStatistics(data.values().stream());
		final int min = 0;
		int max = (int) (statistics.getMax() * 1.05d);
		if (rule.isPresent() && rule.get().getValue() > max) {
			max = (int) (rule.get().getValue() * 1.05);
		}

		final BarDataset lds = new BarDataset();
		final List<Integer> sortedKeys = data.keySet().stream().sorted().collect(Collectors.toList());

		final Scales<BarChartOptions> scales = barConfig.data().addDataset(lds.label(label).yAxisID(label))//
				.and().options().responsive(true).title().and().tooltips().mode(InteractionMode.INDEX).intersect(false)
				.and().hover().mode(InteractionMode.NEAREST).intersect(true).and().scales()
				.add(Axis.Y, new LinearScale().barPercentage(1).display(true).scaleLabel().display(true).labelString("")//
                        .and().ticks().suggestedMin(min).suggestedMax(max)//
                        .and().gridLines().offsetGridLines(false)//
                        .and().position(pos).id(label));

		if (barConfig.data().getLabels() == null) {
			barConfig.data().labels(
					sortedKeys.stream().map(String::valueOf).collect(Collectors.toList()).toArray(new String[1]));
			scales.add(Axis.X, new CategoryScale().barPercentage(1).display(true).gridLines().offsetGridLines(false)//
                    .and().scaleLabel().display(true) //
					.and().position(Position.TOP));
		}
		scales.and().done();
		sortedKeys.forEach(x -> lds.addData(data.get(x)));
		lds.borderColor(getColor(border));
		lds.backgroundColor(getColor(back));
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

	public LineDataset addSimpleLine(final Map<Integer, Double> lineData, final Color border, final Color back) {
		final LineDataset dataset = new LineDataset();
		dataset.type().label("Ziel").borderColor(getColor(border)).backgroundColor(getColor(back)).pointRadius(0).fill(false).borderWidth(1)
				.lineTension(1).spanGaps(true).steppedLine(true);
		dataset.dataAsList(Arrays.asList(lineData.values().toArray(new Double[1])));
		barConfig.data().addDataset(dataset).and().options()//
				.scales()//
                .add(Axis.X, new LinearScale().display(false).gridLines().drawTicks(true).offsetGridLines(true).and().categoryPercentage(1)).and()//
				.elements().line().borderJoinStyle(JoinStyle.MITER).and()
				.and().done();

		return dataset;
	}
}
