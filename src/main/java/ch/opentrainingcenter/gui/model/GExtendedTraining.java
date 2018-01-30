package ch.opentrainingcenter.gui.model;

import java.util.ArrayList;
import java.util.List;

public class GExtendedTraining implements GObject {

	private final GSimpleTraining simpleTraininig;

	private final List<GTrackPoint> points = new ArrayList<>();

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
}
