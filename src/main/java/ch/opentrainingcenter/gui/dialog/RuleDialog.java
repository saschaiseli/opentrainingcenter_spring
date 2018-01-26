package ch.opentrainingcenter.gui.dialog;

import java.util.Arrays;

import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToLongConverter;
import com.vaadin.ui.Button;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import ch.opentrainingcenter.business.domain.Section;
import ch.opentrainingcenter.business.service.goal.GoalService;
import ch.opentrainingcenter.gui.model.GRule;

public class RuleDialog extends Window {
	private static final String ERR = "Muss eine Zahl sein";
	private static final long serialVersionUID = 1L;
	private final TextField value;
	private final NativeSelect<Section> section;
	private final NativeSelect<ch.opentrainingcenter.business.domain.Unit> unit;
	private final Button save, cancel;

	private final GRule model = new GRule(-1, 0, Section.PER_MONTH,
			ch.opentrainingcenter.business.domain.Unit.DISTANCE);
	private final Binder<GRule> binder = new Binder<>();

	public RuleDialog(final String title, final GoalService goalService, final String email) {
		super(title);
		center();
		setClosable(true);
		setModal(true);
		setHeight("380px");
		setWidth("480px");

		final VerticalLayout layout = new VerticalLayout();

		setContent(layout);

		value = new TextField();
		value.setMaxLength(10);
		value.setCaption("Beschreibung kommt noch");

		section = new NativeSelect<>("Section", Arrays.asList(Section.values()));
		section.setCaption("auch hier folgt noch eine detailierte Beschreibung");
		unit = new NativeSelect<>("Einheit", Arrays.asList(ch.opentrainingcenter.business.domain.Unit.values()));
		unit.setCaption("auch hier folgt noch eine detailierte Beschreibung");

		cancel = new Button("Cancel");
		cancel.addClickListener(event -> {
			close();
		});

		save = new Button("Save");
		save.addClickListener(event -> {
			section.getSelectedItem();
			goalService.saveOrUpdate(model, email);
			close();
		});
		binder.addValueChangeListener(event -> {
			System.out.println("binder: " + binder.validate().isOk());
			System.out.println("binder isValid: " + binder.isValid());
			save.setEnabled(isValid());
		});

		// binder.bind(value, rule -> String.valueOf(model.getValue()), (rule, v) ->
		// model.setValue(Long.valueOf(v)));
		binder.forField(value).withConverter(new StringToLongConverter(ERR)).bind(GRule::getValue, GRule::setValue);
		binder.bind(section, GRule::getSection, GRule::setSection);
		binder.bind(unit, GRule::getUnit, GRule::setUnit);

		binder.setBean(model);

		layout.addComponent(value);
		layout.addComponent(section);
		layout.addComponent(unit);
		layout.addComponent(save);

	}

	private boolean isValid() {
		return value.getValue() != null && value.getValue().length() > 1 && section.getSelectedItem().isPresent()
				&& unit.getSelectedItem().isPresent();
	}

	public void updateModel(final GRule rule) {
		model.setId(rule.getId());
		model.setValue(rule.getValue());
		model.setSection(rule.getSection());
		model.setUnit(rule.getUnit());
		updateDialog();

	}

	private void updateDialog() {
		value.setValue(String.valueOf(model.getValue()));
		section.setSelectedItem(model.getSection());
		unit.setSelectedItem(model.getUnit());
	}

}
