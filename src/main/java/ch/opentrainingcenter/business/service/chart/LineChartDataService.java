package ch.opentrainingcenter.business.service.chart;

import ch.opentrainingcenter.gui.model.GTrackPoint;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class LineChartDataService {

    public Map<Integer, Double> getValues(final List<GTrackPoint> points,
                                          final Function<GTrackPoint, Double> function) {
        return points.stream()
                .collect(Collectors.toMap((final GTrackPoint g) -> (int) g.getDistance(), function, (k1, k2) -> k1));
    }
}
