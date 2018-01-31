package ch.opentrainingcenter.gui.view;

import java.awt.Color;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

import com.byteowls.vaadin.chartjs.ChartJs;
import com.byteowls.vaadin.chartjs.config.LineChartConfig;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import ch.opentrainingcenter.business.service.TrainingService;
import ch.opentrainingcenter.business.service.chart.LineChartDataService;
import ch.opentrainingcenter.business.util.DateUtil;
import ch.opentrainingcenter.gui.model.GExtendedTraining;
import ch.opentrainingcenter.gui.model.GSimpleTraining;
import ch.opentrainingcenter.gui.model.chart.ChartJsComponent;

@SuppressWarnings("serial")
public class DetailPanel extends Panel {

	private final LineChartDataService dataService;

	private final GExtendedTraining training;

	public DetailPanel(final GSimpleTraining training, final TrainingService service,
			final LineChartDataService dataService) {
		super(String.format("Training vom %s", DateUtil.format(new Date(training.getId()))));
		this.dataService = dataService;
		this.training = service.findById(training.getId());
		init();
	}

	public void init() {
		final VerticalLayout root = new VerticalLayout();

		final HorizontalLayout l1 = new HorizontalLayout();

		final CssLayout grid = new CssLayout();
		grid.addStyleName("outlined");
		grid.setSizeFull();

		final GSimpleTraining st = training.getSimpleTraininig();
		grid.addComponent(createCellContent("Distanz", st.getDistance()));
		grid.addComponent(createCellContent("Zeit", st.getDuration()));
		grid.addComponent(createCellContent("Pace", st.getPace()));
		grid.addComponent(createCellContent("max Pace", training.getMaxSpeed()));

		grid.addComponent(createCellContent("max Herzfrequenz", String.valueOf(st.getMaxHeartBeat())));
		grid.addComponent(createCellContent("avg. Herzfrequenz", String.valueOf(st.getAverageHeartBeat())));
		grid.addComponent(createCellContent("Trainingseffekt", st.getTrainingEffect().toString()));

		grid.addComponent(createCellContent("Positive Höhenmeter", training.getUp()));
		grid.addComponent(createCellContent("Negative Höhenmeter", training.getDown()));
		grid.addComponent(createCellContent("Qualität GPS", training.getQuality()));

		l1.addComponent(grid);
		root.addComponent(l1);

		final HorizontalLayout l2 = new HorizontalLayout();
		l2.setSizeFull();
		l2.setMargin(true);

		final Panel heartPanel = new Panel("Herz");

		final ChartJsComponent heart = new ChartJsComponent("Schnabber", Color.GRAY, Color.GREEN);
		final Map<Double, Integer> data = dataService.getValues(training.getTrackPoints(), p -> p.getHeartbeat());
		final LineChartConfig lineChartConfig = heart.createConfig2(data, ChronoUnit.FOREVER);
		final ChartJs chart = heart.createChart(lineChartConfig);
		chart.setSizeFull();
		heartPanel.setContent(chart);
		l2.addComponent(heartPanel);
		root.addComponent(l2);
		setContent(root);
	}

	private Component createCellContent(final String label, final String value) {
		final Panel p = new Panel(label);
		final HorizontalLayout hl = new HorizontalLayout();
		hl.setMargin(true);
		hl.setWidth(180, Unit.PIXELS);
		// final Label l1 = new Label(label);
		// hl.addComponent(l1);

		final Label l2 = new Label(value);
		hl.addComponent(l2);

		p.setContent(hl);

		return p;
	}
}
