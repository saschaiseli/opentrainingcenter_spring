package ch.opentrainingcenter.business.service.chart;

import java.util.stream.DoubleStream;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import ch.opentrainingcenter.gui.model.ActitvityPeriod;
import ch.opentrainingcenter.gui.model.chart.DashboardChartDataModel;

@Service
public class DashboardXYPlotService {

	public DashboardChartDataModel createChartDataMode(final ActitvityPeriod period, final int size) {
		final DoubleStream xValues = DoubleStream.empty();
		final Stream<String> yValues = Stream.empty();
		return new DashboardChartDataModel(xValues, yValues);
	}
}
