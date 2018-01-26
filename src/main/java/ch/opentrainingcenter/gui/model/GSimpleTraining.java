package ch.opentrainingcenter.gui.model;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import ch.opentrainingcenter.business.util.DistanceUtil;

public class GSimpleTraining implements GObject {
	private final long id;
	private final String date;
	private final String duration;
	private final String distance;
	private final int averageHeartBeat;
	private final int maxHeartBeat;
	private final String pace;
	private final Integer trainingEffect;

	final DateTimeFormatter f = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
	final DateTimeFormatter tf = DateTimeFormatter.ofPattern("HH:mm:ss");

	private final long laengeInMeter;

	public GSimpleTraining(final long id, final long dauer, final long laengeInMeter, final int averageHeartBeat,
			final int maxHeartBeat, final Integer trainingEffect) {

		this.id = id;
		this.laengeInMeter = laengeInMeter;
		final LocalDate ld = Instant.ofEpochMilli(id).atZone(ZoneId.systemDefault()).toLocalDate();
		date = ld.format(f);

		final LocalTime d = LocalTime.ofSecondOfDay(dauer);
		duration = d.format(tf);

		distance = DistanceUtil.roundDistanceFromMeterToKmMitEinheit(laengeInMeter);
		this.averageHeartBeat = averageHeartBeat;
		this.maxHeartBeat = maxHeartBeat;
		pace = DistanceUtil.calculatePace(laengeInMeter, dauer);
		this.trainingEffect = trainingEffect;
	}

	public long getId() {
		return id;
	}

	public String getDate() {
		return date;
	}

	public String getDuration() {
		return duration;
	}

	public String getDistance() {
		return distance;
	}

	public long getLaengeInMeter() {
		return laengeInMeter;
	}

	public int getAverageHeartBeat() {
		return averageHeartBeat;
	}

	public int getMaxHeartBeat() {
		return maxHeartBeat;
	}

	public String getPace() {
		return pace;
	}

	public Integer getTrainingEffect() {
		return trainingEffect;
	}
}
