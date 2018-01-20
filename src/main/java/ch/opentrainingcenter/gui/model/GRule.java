package ch.opentrainingcenter.gui.model;

import ch.opentrainingcenter.business.domain.Section;
import ch.opentrainingcenter.business.domain.Unit;

public class GRule implements GObject {

	private final long id;

	private final long value;

	private final Section section;

	private final Unit unit;

	public GRule(final long id, final long value, final Section section, final Unit unit) {
		this.id = id;
		this.value = value;
		this.section = section;
		this.unit = unit;
	}

	public long getId() {
		return id;
	}

	public long getValue() {
		return value;
	}

	public Section getSection() {
		return section;
	}

	public Unit getUnit() {
		return unit;
	}

}
