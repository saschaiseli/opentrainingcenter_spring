package ch.opentrainingcenter.gui.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.opentrainingcenter.business.domain.TrainingType;

public class GExtendedTraining implements GObject {

	private final GSimpleTraining simpleTraininig;

	private final List<GTrackPoint> points = new ArrayList<>();

	private TrainingType type;

	private GExtendedData extended;

	public GExtendedTraining(final GSimpleTraining gst) {
		this.simpleTraininig = gst;
	}

	public void addTrackPoints(final List<GTrackPoint> points) {
		this.points.clear();
		this.points.addAll(points);
	}

	public GSimpleTraining getSimpleTraininig() {
		return simpleTraininig;
	}

	public void setTrainingType(final TrainingType type) {
		this.type = type;
	}

	public TrainingType getTrainingType() {
		return type;
	}

	public void setExtendedData(final GExtendedData extended) {
		this.extended = extended;
	}

	public String getUp() {
		return extended.getUp();
	}

	public String getDown() {
		return extended.getDown();
	}

	public String getQuality() {
		return extended.getQuality();
	}

	public String getMaxSpeed() {
		return "" + extended.getSpeed();
	}

	public List<GTrackPoint> getTrackPoints() {
		return Collections.unmodifiableList(points);
	}
}
