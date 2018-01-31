package ch.opentrainingcenter.gui.model;

public class GExtendedData {

	private final String up;
	private final String down;
	private final String quality;
	private final double speed;

	public GExtendedData(final Integer up, final Integer down, final Integer quality, final double maxSpeed) {
		this.up = up != null ? up.toString() : "-";
		this.down = down != null ? down.toString() : "-";
		this.quality = quality != null ? quality.toString() : "-";
		this.speed = maxSpeed;
	}

	public final String getUp() {
		return up;
	}

	public final String getDown() {
		return down;
	}

	public final String getQuality() {
		return quality;
	}

	public final double getSpeed() {
		return speed;
	}

}
