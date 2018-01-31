package ch.opentrainingcenter.gui.component;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class KachelComponent extends VerticalLayout {

	public KachelComponent(final String value, final Component component) {
		setSizeUndefined();
		setWidth("100%");
		setMargin(false);
		setSpacing(false);
		setDefaultComponentAlignment(Alignment.TOP_CENTER);

		final Label current = new Label(value);
		current.setSizeUndefined();
		current.addStyleName(ValoTheme.LABEL_HUGE);
		addComponent(current);

		final Label l = new Label("aktuell");
		l.setSizeUndefined();
		l.addStyleName(ValoTheme.LABEL_SMALL);
		l.addStyleName(ValoTheme.LABEL_LIGHT);
		addComponent(l);
		addComponent(component);
		// component.setStyleName("card");
		// setStyle();
	}

	// private void setStyle() {
	// setStyleName("drag-drop-example");
	//
	// final Page.Styles styles = Page.getCurrent().getStyles();
	//
	// styles.add(".drag-drop-example .card {" + "width: 150px;" + "height: 200px;"
	// + "border: 1px solid black;"
	// + "border-radius: 7px;" + "padding-left: 10px;" + "color: red;" +
	// "font-weight: bolder;"
	// + "font-size: 25px;" + "background-color: gainsboro;" + "}");
	// styles.add(".drag-drop-example .v-label-drag-center {border-style:
	// dashed;}");
	// styles.add(".drag-drop-example .dragged {opacity: .4;}");
	// }
}
