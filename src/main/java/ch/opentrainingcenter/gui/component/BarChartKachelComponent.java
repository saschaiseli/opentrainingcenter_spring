package ch.opentrainingcenter.gui.component;

import ch.opentrainingcenter.business.service.TrainingService;
import ch.opentrainingcenter.gui.chart.BarChartComponent;
import ch.opentrainingcenter.gui.model.GRule;
import com.byteowls.vaadin.chartjs.config.BarChartConfig;
import com.byteowls.vaadin.chartjs.config.ChartConfig;
import com.byteowls.vaadin.chartjs.data.Data;
import com.byteowls.vaadin.chartjs.options.Position;
import com.vaadin.ui.Label;

import java.awt.*;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings("serial")
public class BarChartKachelComponent extends AbstractChartKachelComponent {

    public BarChartKachelComponent(final TrainingService service, final String email, final ChronoUnit unit,
                                   final int size) {
        super(service, email, unit, size);
    }

    @Override
    public ChartConfig getChartConfig(final Map<Integer, Double> data, final List<String> yAxis, final Label label,
                                      final Optional<GRule> rule) {
        final BarChartComponent bcc = createBarChartComponent(data, yAxis, rule);
        final Data<BarChartConfig> barData = bcc.getConfig().data();
        final List<?> datas = barData.getDatasetAtIndex(0).getData();
        label.setValue(datas.get(datas.size() - 1) + " km");

        return bcc.getConfig();
    }

    private BarChartComponent createBarChartComponent(final Map<Integer, Double> data, final List<String> yAxis,
                                                      final Optional<GRule> rule) {
        final BarChartComponent bcc = new BarChartComponent();
        bcc.addLine(data, rule, getChartLabel(unit), Position.TOP, Color.BLUE, Color.CYAN).borderWidth(2);
        if (rule.isPresent()) {
            final Map<Integer, Double> lineData = new HashMap<>();
            data.keySet().stream().forEach(k -> lineData.put(k, Double.valueOf(rule.get().getValue())));
            bcc.addSimpleLine(lineData, Color.RED, Color.RED).borderWidth(2);
        }
        bcc.getConfig().data().labels(yAxis.toArray(new String[1]));
        return bcc;
    }

}
