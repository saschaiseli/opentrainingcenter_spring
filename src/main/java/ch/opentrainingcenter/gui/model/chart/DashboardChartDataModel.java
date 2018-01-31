package ch.opentrainingcenter.gui.model.chart;

import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class DashboardChartDataModel {
	private final DoubleStream xValues;
	private final Stream<String> yValues;

	public DashboardChartDataModel(final DoubleStream xValues, final Stream<String> yValues) {
		this.xValues = xValues;
		this.yValues = yValues;
	}

	public DoubleStream getxValues() {
		return xValues;
	}

	public Stream<String> getyValues() {
		return yValues;
	}

}
