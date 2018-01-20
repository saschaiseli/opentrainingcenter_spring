package ch.opentrainingcenter.business.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

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
@NamedQueries({ //
		@NamedQuery(name = "Rule.findByAthlete", query = "SELECT r FROM RULE r where r.athlete=?1") })
@Entity(name = "RULE")
public class Rule implements EntityObject {

	@Id
	private long id;

	private long value;

	@Enumerated(EnumType.ORDINAL)
	private Section section;

	@Enumerated(EnumType.ORDINAL)
	private Unit unit;

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

	public Section getSection() {
		return section;
	}

	public void setSection(final Section unit) {
		this.section = unit;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(final Unit type) {
		this.unit = type;
	}

	public Athlete getAthlete() {
		return athlete;
	}

	public void setAthlete(final Athlete athlete) {
		this.athlete = athlete;
	}

}
