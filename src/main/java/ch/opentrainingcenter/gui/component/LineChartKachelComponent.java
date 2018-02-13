package ch.opentrainingcenter.gui.component;

import java.awt.Color;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.byteowls.vaadin.chartjs.config.ChartConfig;
import com.byteowls.vaadin.chartjs.config.LineChartConfig;
import com.byteowls.vaadin.chartjs.data.Data;
import com.byteowls.vaadin.chartjs.data.LineDataset;
import com.byteowls.vaadin.chartjs.options.Position;
import com.vaadin.ui.Label;

import ch.opentrainingcenter.business.service.TrainingService;
import ch.opentrainingcenter.gui.chart.LineChartComponent;
import ch.opentrainingcenter.gui.model.GRule;

@SuppressWarnings("serial")
public class LineChartKachelComponent extends AbstractChartKachelComponent {

	public LineChartKachelComponent(final TrainingService service, final String email, final ChronoUnit unit,
			final int size) {
		super(service, email, unit, size);
	}

	@Override
	public ChartConfig getChartConfig(final Map<Integer, Double> data, final List<String> yAxis, final Label label,
			final Optional<GRule> rule) {
		final LineChartComponent lcc = createLineChartComponent(data, yAxis);
		final Data<LineChartConfig> barData = lcc.getConfig().data();
		final List<?> datas = barData.getDatasetAtIndex(0).getData();
		label.setValue(datas.get(datas.size() - 1) + " km");

		return lcc.getConfig();
	}

	private LineChartComponent createLineChartComponent(final Map<Integer, Double> data, final List<String> yAxis) {
		final LineChartComponent lcc = new LineChartComponent();
		final LineDataset line = lcc.addLine(data, getChartLabel(unit), Position.TOP, Color.BLUE, Color.CYAN);
		line.borderWidth(2);
		lcc.getConfig().data().labels(yAxis.toArray(new String[1]));
		return lcc;
	}

}
