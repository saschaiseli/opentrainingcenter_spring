package ch.opentrainingcenter.gui.view;

import java.util.Date;

import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import ch.opentrainingcenter.business.service.TrainingService;
import ch.opentrainingcenter.business.service.fileconverter.fit.DistanceHelper;
import ch.opentrainingcenter.business.util.DateUtil;
import ch.opentrainingcenter.gui.model.GExtendedTraining;
import ch.opentrainingcenter.gui.model.GSimpleTraining;

@SuppressWarnings("serial")
public class DetailPanel extends Panel {

	private final GExtendedTraining training;

	public DetailPanel(final GSimpleTraining training, final TrainingService service) {
		super(String.format("Training vom %s", DateUtil.format(new Date(training.getId()))));
		this.training = service.findById(training.getId());
		init();
	}

	public void init() {
		final VerticalLayout layout = new VerticalLayout();

		final HorizontalLayout hl = new HorizontalLayout();

		final FormLayout layoutLeft = new FormLayout();
		layoutLeft.setMargin(false);
		layoutLeft.addStyleName("outlined");
		layoutLeft.setSizeFull();

		final TextField child1 = new TextField("Distanzleft [km]");
		child1.setWidth(100.0f, Unit.PERCENTAGE);
		child1.setEnabled(false);
		child1.setValue(
				DistanceHelper.roundDistanceFromMeterToKmMitEinheit(training.getSimpleTraininig().getLaengeInMeter()));
		layoutLeft.addComponent(child1);

		final FormLayout layoutRight = new FormLayout();
		layoutRight.setMargin(false);
		layoutRight.addStyleName("outlined");
		layoutRight.setSizeFull();

		final TextField child2 = new TextField("Distanzright [km]");
		child2.setWidth(100.0f, Unit.PERCENTAGE);
		child2.setValue(
				DistanceHelper.roundDistanceFromMeterToKmMitEinheit(training.getSimpleTraininig().getLaengeInMeter()));
		layoutRight.addComponent(child2);

		hl.addComponent(layoutLeft);
		hl.addComponent(layoutRight);
		hl.setExpandRatio(layoutLeft, 50F);
		hl.setExpandRatio(layoutRight, 50F);
		layout.addComponent(hl);
		setContent(layout);
	}
}
