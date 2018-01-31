package ch.opentrainingcenter.business.mapper.toGobject;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import ch.opentrainingcenter.business.domain.Rule;
import ch.opentrainingcenter.business.mapper.MapToGObject;
import ch.opentrainingcenter.gui.model.GRule;

@Service
@Secured({ "ROLE_ADMIN" })
public class RuleToGObject implements MapToGObject<GRule, Rule> {

	@Override
	public GRule convert(final Rule r) {
		return new GRule(r.getId(), r.getValue(), r.getSection(), r.getUnit());
	}

}
