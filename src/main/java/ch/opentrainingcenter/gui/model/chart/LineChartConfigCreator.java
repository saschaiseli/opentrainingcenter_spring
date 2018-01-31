package ch.opentrainingcenter.gui.model.chart;

import com.byteowls.vaadin.chartjs.config.LineChartConfig;
import com.byteowls.vaadin.chartjs.data.LineDataset;
import com.byteowls.vaadin.chartjs.options.InteractionMode;
import com.byteowls.vaadin.chartjs.options.Position;
import com.byteowls.vaadin.chartjs.options.scale.Axis;
import com.byteowls.vaadin.chartjs.options.scale.CategoryScale;
import com.byteowls.vaadin.chartjs.options.scale.LinearScale;

public final class LineChartConfigCreator {

	private LineChartConfigCreator() {

	}

	public static LineChartConfig getChartConfigForDashboard(final String[] ylabels, final int min, final int max,
			final String label) {
		final LineChartConfig lineConfig = new LineChartConfig();
		lineConfig.data().labels(ylabels)//
				.addDataset(new LineDataset().label(label).fill(false))//
				.and().options().responsive(true).title().and().tooltips().mode(InteractionMode.INDEX).intersect(false)
				.and().hover().mode(InteractionMode.NEAREST).intersect(true).and().scales() //
				.add(Axis.X, new CategoryScale().display(true).scaleLabel().display(true) //
						.and().position(Position.TOP))
				.add(Axis.Y, new LinearScale().display(true).scaleLabel().display(true).labelString("").and().ticks()
						.suggestedMin(min).suggestedMax(max).and().position(Position.RIGHT))
				.and().done();
		return lineConfig;
	}
}
