package ch.opentrainingcenter.gui.component;

import java.awt.Color;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

import com.byteowls.vaadin.chartjs.config.BarChartConfig;
import com.byteowls.vaadin.chartjs.config.ChartConfig;
import com.byteowls.vaadin.chartjs.data.BarDataset;
import com.byteowls.vaadin.chartjs.data.Data;
import com.byteowls.vaadin.chartjs.options.Position;
import com.vaadin.ui.Label;

import ch.opentrainingcenter.business.service.TrainingService;
import ch.opentrainingcenter.gui.chart.BarChartComponent;

@SuppressWarnings("serial")
public class BarChartKachelComponent extends AbstractChartKachelComponent {

	public BarChartKachelComponent(final TrainingService service, final String email, final ChronoUnit unit,
			final int size) {
		super(service, email, unit, size);
	}

	@Override
	public ChartConfig getChartConfig(final Map<Integer, Double> data, final List<String> yAxis, final Label label) {
		final BarChartComponent lcc = createBarChartComponent(data, yAxis);
		final Data<BarChartConfig> barData = lcc.getConfig().data();
		final List<?> datas = barData.getDatasetAtIndex(0).getData();
		label.setValue(datas.get(datas.size() - 1) + " km");

		return lcc.getConfig();
	}

	private BarChartComponent createBarChartComponent(final Map<Integer, Double> data, final List<String> yAxis) {
		final BarChartComponent lcc = new BarChartComponent();
		final BarDataset line = lcc.addLine(data, getChartLabel(unit), Position.TOP, Color.BLUE, Color.CYAN);
		line.borderWidth(2);
		lcc.getConfig().data().labels(yAxis.toArray(new String[1]));
		return lcc;
	}

}
