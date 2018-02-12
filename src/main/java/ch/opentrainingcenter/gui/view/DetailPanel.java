package ch.opentrainingcenter.gui.view;

import java.awt.Color;
import java.util.Date;

import com.byteowls.vaadin.chartjs.ChartJs;
import com.byteowls.vaadin.chartjs.options.Position;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import ch.opentrainingcenter.business.service.TrainingService;
import ch.opentrainingcenter.business.service.chart.LineChartDataService;
import ch.opentrainingcenter.business.util.DateUtil;
import ch.opentrainingcenter.gui.chart.LineChartComponent;
import ch.opentrainingcenter.gui.model.GExtendedTraining;
import ch.opentrainingcenter.gui.model.GSimpleTraining;

@SuppressWarnings("serial")
public class DetailPanel extends Panel {
	private final LineChartDataService dataService;

	private final GExtendedTraining tr;

	public DetailPanel(final GSimpleTraining training, final TrainingService service,
			final LineChartDataService dataService) {
		super(String.format("Training vom %s", DateUtil.format(new Date(training.getId()))));
		this.dataService = dataService;
		this.tr = service.findById(training.getId());
		init();
	}

	public void init() {
		final VerticalLayout root = new VerticalLayout();

		final HorizontalLayout l1 = new HorizontalLayout();

		final CssLayout grid = new CssLayout();
		grid.addStyleName("outlined");
		grid.setSizeFull();

		final GSimpleTraining st = tr.getSimpleTraininig();
		grid.addComponent(createCellContent("Distanz", st.getDistance()));
		grid.addComponent(createCellContent("Zeit", st.getDuration()));
		grid.addComponent(createCellContent("Pace", st.getPace()));
		grid.addComponent(createCellContent("max Pace", tr.getMaxSpeed()));

		grid.addComponent(createCellContent("max Herzfrequenz", String.valueOf(st.getMaxHeartBeat())));
		grid.addComponent(createCellContent("avg. Herzfrequenz", String.valueOf(st.getAverageHeartBeat())));
		grid.addComponent(createCellContent("Trainingseffekt", st.getTrainingEffect().toString()));

		grid.addComponent(createCellContent("Positive Höhenmeter", tr.getUp()));
		grid.addComponent(createCellContent("Negative Höhenmeter", tr.getDown()));
		grid.addComponent(createCellContent("Qualität GPS", tr.getQuality()));

		l1.addComponent(grid);
		root.addComponent(l1);

		final HorizontalLayout l2 = new HorizontalLayout();
		l2.setSizeFull();
		l2.setMargin(true);

		final Panel heartPanel = new Panel("Herzfrequenz");
		final LineChartComponent lcc = new LineChartComponent();

		lcc.addLine(dataService.getValues(tr.getTrackPoints(), p -> Double.valueOf(p.getHeartbeat())), "Herzfrequenz",
				Position.RIGHT, Color.RED, Color.RED);

		lcc.addLine(dataService.getValues(tr.getTrackPoints(), p -> Double.valueOf(p.getAltitude())), "Höhe",
				Position.LEFT, Color.BLUE, Color.BLUE);

		final ChartJs chart = new ChartJs(lcc.getConfig());
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
