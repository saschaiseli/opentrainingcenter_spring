package ch.opentrainingcenter.business.mapper.togobject;

import ch.opentrainingcenter.business.domain.Athlete;
import ch.opentrainingcenter.business.domain.Rule;
import ch.opentrainingcenter.business.domain.Section;
import ch.opentrainingcenter.business.domain.Unit;
import ch.opentrainingcenter.gui.model.GRule;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

public class RuleToGObjectTest {

    private final RuleToGObject convert = new RuleToGObject();
    private static final int VALUE = 142;

    @Test
    public void testConvert() {
        final Rule rule = new Rule();
        final Athlete athlete = Mockito.mock(Athlete.class);
        rule.setAthlete(athlete);
        rule.setId(42);
        rule.setSection(Section.PER_MONTH);
        rule.setValue(VALUE);
        rule.setUnit(Unit.QUANTITY);

        final GRule gObject = convert.convert(rule);

        assertEquals(42, gObject.getId());
        assertEquals(VALUE, gObject.getValue());
        assertEquals(Section.PER_MONTH, gObject.getSection());
        assertEquals(Unit.QUANTITY, gObject.getUnit());
    }
}
