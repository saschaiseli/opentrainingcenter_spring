package ch.opentrainingcenter.business.mapper.togobject;

import ch.opentrainingcenter.business.domain.Rule;
import ch.opentrainingcenter.gui.model.GRule;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

@Service
@Secured({"ROLE_ADMIN"})
public class RuleToGObject implements Converter<Rule, GRule> {

    @Override
    public GRule convert(final Rule r) {
        return new GRule(r.getId(), r.getValue(), r.getSection(), r.getUnit());
    }

}
