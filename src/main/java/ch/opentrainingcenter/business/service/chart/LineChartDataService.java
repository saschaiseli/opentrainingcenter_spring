package ch.opentrainingcenter.business.service.chart;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ch.opentrainingcenter.gui.model.GTrackPoint;

@Service
public class LineChartDataService {

	public Map<Double, Integer> getValues(final List<GTrackPoint> points,
			final Function<GTrackPoint, Integer> function) {
		return points.stream().collect(Collectors.toMap(GTrackPoint::getDistance, function, (k1, k2) -> k1));
	}
}
