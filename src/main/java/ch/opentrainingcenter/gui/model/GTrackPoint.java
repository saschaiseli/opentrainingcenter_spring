package ch.opentrainingcenter.gui.model;

public class GTrackPoint implements GObject {
	private final double distance;
	private final int heartbeat;
	private final int altitude;
	private final long zeit;
	private final int lap;
	private final Double longitude;
	private final Double latitude;

	public GTrackPoint(final double distance, final int heartbeat, final int altitude, final long zeit, final int lap,
			final Double longitude, final Double latitude) {
		this.distance = distance;
		this.heartbeat = heartbeat;
		this.altitude = altitude;
		this.zeit = zeit;
		this.lap = lap;
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public final double getDistance() {
		return distance;
	}

	public final int getHeartbeat() {
		return heartbeat;
	}

	public final int getAltitude() {
		return altitude;
	}

	public final long getZeit() {
		return zeit;
	}

	public final int getLap() {
		return lap;
	}

	public final Double getLongitude() {
		return longitude;
	}

	public final Double getLatitude() {
		return latitude;
	}

}
