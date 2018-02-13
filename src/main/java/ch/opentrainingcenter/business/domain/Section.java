package ch.opentrainingcenter.business.domain;

import java.time.temporal.ChronoUnit;

/**
 * Pro Woche, pro monat, pro jahr
 */
public enum Section {
	PER_WEEK(ChronoUnit.WEEKS), PER_MONTH(ChronoUnit.MONTHS), PER_YEAR(ChronoUnit.YEARS);

	private final ChronoUnit unit;

	private Section(final ChronoUnit unit) {
		this.unit = unit;
	}

	public ChronoUnit getChronoUnit() {
		return unit;
	}
}
