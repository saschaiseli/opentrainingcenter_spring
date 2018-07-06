package ch.opentrainingcenter.business.mapper.toentity;

import ch.opentrainingcenter.business.domain.Rule;
import ch.opentrainingcenter.gui.model.GRule;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

@Service
@Secured({"ROLE_ADMIN"})
public class RuleToEntity implements Converter<GRule, Rule> {

    @Override
    @Synchronized
    public Rule convert(final GRule gObject) {
        final Rule rule = new Rule();
        rule.setId(gObject.getId());
        rule.setValue(gObject.getValue());
        rule.setSection(gObject.getSection());
        rule.setUnit(gObject.getUnit());
        return rule;
    }

}
