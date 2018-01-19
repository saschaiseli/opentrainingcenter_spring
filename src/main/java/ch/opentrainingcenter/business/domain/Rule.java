package ch.opentrainingcenter.business.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Regeln & Ziele.
 *
 * zum Beispiel
 *
 * <pre>
 *  4 trainings pro woche
 *	30km pro Woche
 *	300km  pro Monat
 *	3000km pro jahr
 *
 * </pre>
 */
@Entity
public class Rule {

	@Id
	private long id;

	private long value;

	@Enumerated(EnumType.ORDINAL)
	private Section unit;

	@Enumerated(EnumType.ORDINAL)
	private Unit type;

	@ManyToOne
	@JoinColumn(name = "ID_FK_ATHLETE", nullable = false)
	private Athlete athlete;

	public long getId() {
		return id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public long getValue() {
		return value;
	}

	public void setValue(final long value) {
		this.value = value;
	}

	public Section getUnit() {
		return unit;
	}

	public void setUnit(final Section unit) {
		this.unit = unit;
	}

	public Unit getType() {
		return type;
	}

	public void setType(final Unit type) {
		this.type = type;
	}

	public Athlete getAthlete() {
		return athlete;
	}

	public void setAthlete(final Athlete athlete) {
		this.athlete = athlete;
	}

}
