package ch.opentrainingcenter.gui.model;

import java.time.temporal.ChronoUnit;

public enum ActitvityPeriod {

	WEEK("Woche", ChronoUnit.WEEKS), MONTH("Monat", ChronoUnit.MONTHS), YEAR("Jahr", ChronoUnit.YEARS);

	private final String caption;
	private final ChronoUnit unit;

	private ActitvityPeriod(final String caption, final ChronoUnit unit) {
		this.caption = caption;
		this.unit = unit;
	}

	public String getCaption() {
		return caption;
	}

	public ChronoUnit getUnit() {
		return unit;
	}
}
