package ch.opentrainingcenter.gui.model;

import ch.opentrainingcenter.business.domain.Section;
import ch.opentrainingcenter.business.domain.Unit;

public class GRule implements GObject {

    private long id;

    private long value;

    private Section section;

    private Unit unit;

    public GRule(final long id, final long value, final Section section, final Unit unit) {
        this.id = id;
        this.value = value;
        this.section = section;
        this.unit = unit;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }


}
