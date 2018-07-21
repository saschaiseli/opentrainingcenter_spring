package ch.opentrainingcenter.gui.view;

import ch.opentrainingcenter.business.service.TrainingService;
import ch.opentrainingcenter.business.service.chart.LineChartDataService;
import ch.opentrainingcenter.gui.model.ActitvityPeriod;
import ch.opentrainingcenter.gui.model.GSimpleTraining;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.SerializablePredicate;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.events.EventBusListener;
import org.vaadin.teemusa.gridextensions.paging.PagedDataProvider;
import org.vaadin.teemusa.gridextensions.paging.PagingControls;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Arrays;
import java.util.Optional;

import static com.vaadin.icons.VaadinIcons.*;

@Secured({"ROLE_ADMIN"})
@SpringView(name = ActivityView.VIEW_NAME)
public class ActivityView extends VerticalLayout implements View, EventBusListener<Object> {

    private static final long serialVersionUID = 1L;

    public static final String VIEW_NAME = "Activity";

    private Grid<GSimpleTraining> grid;
    @Autowired
    private EventBus.UIEventBus eventBus;

    private final RadioButtonGroup<ActitvityPeriod> period = new RadioButtonGroup<>("",
            Arrays.asList(ActitvityPeriod.values()));

    @Autowired
    private DataProvider<GSimpleTraining, SerializablePredicate<GSimpleTraining>> dp;

    @Autowired
    private LineChartDataService dataService;

    @Autowired
    private TrainingService trainingService;

    private PagedDataProvider<GSimpleTraining, SerializablePredicate<GSimpleTraining>> pagedDP;

    private Panel detail;

    @PostConstruct
    public void init() {
        eventBus.subscribe(this);
        final HorizontalLayout hl = new HorizontalLayout();
        hl.addComponent(period);
        period.setItemCaptionGenerator(item -> item.getCaption());
        period.setEnabled(false);
        hl.setComponentAlignment(period, Alignment.MIDDLE_RIGHT);
        hl.setSizeFull();
        hl.setMargin(false);
        hl.setSpacing(false);
        period.addStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);

        addComponent(hl);

        grid = new Grid<>();
        grid.addColumn(GSimpleTraining::getDate).setCaption("Datum");
        grid.addColumn(GSimpleTraining::getDuration).setCaption("Dauer");
        grid.addColumn(GSimpleTraining::getDistance).setCaption("Distanz");
        grid.addColumn(GSimpleTraining::getPace).setCaption("Pace");
        grid.addColumn(GSimpleTraining::getAverageHeartBeat).setCaption("durchschn. Herzf.");
        grid.addColumn(GSimpleTraining::getMaxHeartBeat).setCaption("max. Herzf.");
        grid.addColumn(GSimpleTraining::getTrainingEffect).setCaption("Training Effect");

        grid.addComponentColumn(this::buildEditButton);
        grid.addComponentColumn(this::buildDeleteButton);

        grid.setSizeFull();

        grid.setSelectionMode(SelectionMode.SINGLE);
        addComponent(grid);

        pagedDP = new PagedDataProvider<>(dp);
        grid.setDataProvider(pagedDP);
        final PagingControls<GSimpleTraining> pc = pagedDP.getPagingControls();

        grid.addSelectionListener(event -> show(event.getFirstSelectedItem()));

        final HorizontalLayout pages = new HorizontalLayout();
        pages.addComponent(createPCButton(ANGLE_DOUBLE_LEFT, e -> pc.setPageNumber(0)));
        pages.addComponent(createPCButton(ANGLE_LEFT, e -> pc.previousPage()));
        pages.addComponent(createPCButton(ANGLE_RIGHT, e -> pc.nextPage()));
        pages.addComponent(createPCButton(ANGLE_DOUBLE_RIGHT, e -> pc.setPageNumber(pc.getPageCount() - 1)));

        final VerticalLayout controls = new VerticalLayout();
        controls.addComponent(pages);
        controls.setWidth("100%");
        controls.setHeightUndefined();
        controls.setComponentAlignment(pages, Alignment.BOTTOM_CENTER);
        addComponent(controls);
        setComponentAlignment(controls, Alignment.MIDDLE_CENTER);

        detail = new Panel("Detail");
        addComponent(detail);

    }

    private Object show(final Optional<GSimpleTraining> training) {
        if (training.isPresent()) {
            removeComponent(detail);
            detail = new DetailPanel(training.get(), trainingService, dataService);
            addComponent(detail);
        }
        return null;
    }

    private Button createPCButton(final VaadinIcons icon, final ClickListener listener) {
        final Button button = new Button(icon);
        button.addStyleName(ValoTheme.BUTTON_SMALL);
        button.addClickListener(listener);
        return button;
    }

    private void reloadGrid() {
        dp.refreshAll();
    }

    private Button buildEditButton(final GSimpleTraining training) {
        final Button button = new Button(VaadinIcons.EDIT);
        button.addStyleName(ValoTheme.BUTTON_SMALL);
        button.addClickListener(e -> {
        });
        return button;
    }

    private Button buildDeleteButton(final GSimpleTraining training) {
        final Button button = new Button(VaadinIcons.CLOSE);
        button.addStyleName(ValoTheme.BUTTON_SMALL);
        button.addClickListener(e -> deleteTraining(training.getId()));
        return button;
    }

    private void deleteTraining(final long id) {
        trainingService.delete(id);
        reloadGrid();
    }

    @Override
    public void onEvent(final org.vaadin.spring.events.Event<Object> event) {
        reloadGrid();
    }

    @PreDestroy
    void destroy() {
        eventBus.unsubscribe(this);
    }
}
