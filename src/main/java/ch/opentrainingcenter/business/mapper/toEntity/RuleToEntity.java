package ch.opentrainingcenter.business.mapper.toEntity;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import ch.opentrainingcenter.business.domain.Rule;
import ch.opentrainingcenter.business.mapper.MapToEntityObject;
import ch.opentrainingcenter.gui.model.GRule;

@Service
@Secured({ "ROLE_ADMIN" })
public class RuleToEntity implements MapToEntityObject<Rule, GRule> {

	@Override
	public Rule convert(final GRule gObject) {
		final Rule rule = new Rule();
		// if (gObject.getId() > 0) {
		rule.setId(gObject.getId());
		// }
		rule.setValue(gObject.getValue());
		rule.setSection(gObject.getSection());
		rule.setUnit(gObject.getUnit());
		return rule;
	}

}
