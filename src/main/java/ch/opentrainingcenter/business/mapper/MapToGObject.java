package ch.opentrainingcenter.business.mapper;

import ch.opentrainingcenter.business.domain.EntityObject;
import ch.opentrainingcenter.gui.model.GObject;

@FunctionalInterface
public interface MapToGObject<E extends GObject, V extends EntityObject> {

	E convert(V entityObject);
}
