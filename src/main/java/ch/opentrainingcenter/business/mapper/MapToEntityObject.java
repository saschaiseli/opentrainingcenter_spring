package ch.opentrainingcenter.business.mapper;

import ch.opentrainingcenter.business.domain.EntityObject;
import ch.opentrainingcenter.gui.model.GObject;

public interface MapToEntityObject<V extends EntityObject, E extends GObject> {

	V convert(E gObject);
}
