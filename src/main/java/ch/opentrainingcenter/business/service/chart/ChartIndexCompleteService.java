package ch.opentrainingcenter.business.service.chart;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import ch.opentrainingcenter.business.util.DateUtil;

@Service
@Secured("ROLE_ADMIN")
public class ChartIndexCompleteService {

	public Map<Integer, Double> fill(final Map<Integer, Double> raw, final ChronoUnit unit, final LocalDate now,
			final int size) {
		LocalDateTime ldt = LocalDateTime.of(now, LocalTime.of(0, 0));
		final Set<Integer> validIndexes = new HashSet<>();
		for (int i = 0; i < size; i++) {
			final Date d = new Date(ldt.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli());
			final int index = DateUtil.getIndex(d, unit);
			validIndexes.add(index);
			raw.computeIfAbsent(index, k -> raw.put(k, 0d));
			ldt = ldt.minus(1, unit);
		}
		// falsche keys löschen
		raw.keySet().retainAll(validIndexes);
		return raw;
	}

}
