package ch.opentrainingcenter.business.service.chart;

import ch.opentrainingcenter.business.util.DateUtil;
import ch.opentrainingcenter.gui.model.GSimpleTraining;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ChartDataAggregator {

    public Map<Integer, List<GSimpleTraining>> aggregateToIndex(final List<GSimpleTraining> trainings,
                                                                final ChronoUnit unit) {
        final Map<Integer, List<GSimpleTraining>> result = new HashMap<>();
        for (final GSimpleTraining training : trainings) {
            final Date date = new Date(training.getId());
            final int index = DateUtil.getIndex(date, unit);
            final List<GSimpleTraining> sup = new ArrayList<>();
            sup.add(training);
            result.merge(index, sup, (v1, v2) -> Stream.concat(v1.stream(), v2.stream()).collect(Collectors.toList()));
        }
        return result;
    }

    public Map<Integer, Double> aggregateToSum(final Map<Integer, List<GSimpleTraining>> raw,
                                               final Function<GSimpleTraining, Number> f) {
        final Map<Integer, Double> result = new HashMap<>();
        for (final Entry<Integer, List<GSimpleTraining>> entry : raw.entrySet()) {
            final Double sum = Math.round(
                    entry.getValue().stream().map(f).collect(Collectors.summingDouble(Number::doubleValue)) * 100)
                    / 100d;
            result.put(entry.getKey(), sum);
        }
        return result;
    }
}
