package ch.opentrainingcenter.business.service.chart;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ch.opentrainingcenter.business.repositories.AppConfig;
import ch.opentrainingcenter.gui.model.GSimpleTraining;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@PropertySource("classpath:application-test.properties")
@SpringBootTest(classes = { AppConfig.class })
public class ChartDataAggregatorTest {
	@Autowired
	private ChartDataAggregator aggregator;

	private Map<Integer, List<GSimpleTraining>> raw;

	@Before
	public void setUp() {
		raw = new HashMap<>();
	}

	@Test
	public void testSumOneIndexOneTraining() {
		final List<GSimpleTraining> list = new ArrayList<>();
		final GSimpleTraining mock = mock(GSimpleTraining.class);
		list.add(mock);
		when(mock.getDistance()).thenReturn("1.236");
		raw.put(0, list);
		final Function<GSimpleTraining, Number> f = t -> Double.valueOf(t.getDistance());

		final Map<Integer, Double> result = aggregator.aggregateToSum(raw, f);

		assertEquals(1.236d, result.get(0).doubleValue(), 0.00001);
	}

	@Test
	public void testSumOneIndexTwoTraining() {
		final List<GSimpleTraining> list = new ArrayList<>();
		final GSimpleTraining mock1 = mock(GSimpleTraining.class);
		final GSimpleTraining mock2 = mock(GSimpleTraining.class);
		list.add(mock1);
		list.add(mock2);
		when(mock1.getDistance()).thenReturn("1.236");
		when(mock2.getDistance()).thenReturn("2.468");
		raw.put(0, list);
		final Map<Integer, Double> result = aggregator.aggregateToSum(raw, t -> Double.valueOf(t.getDistance()));

		assertEquals(3.704d, result.get(0).doubleValue(), 0.00001);
	}

	@Test
	public void testSumTwoIndexTwoTraining() {
		final List<GSimpleTraining> list1 = new ArrayList<>();
		final GSimpleTraining mock1 = mock(GSimpleTraining.class);
		final GSimpleTraining mock2 = mock(GSimpleTraining.class);
		list1.add(mock1);
		list1.add(mock2);
		when(mock1.getDistance()).thenReturn("1.236");
		when(mock2.getDistance()).thenReturn("2.468");

		final List<GSimpleTraining> list2 = new ArrayList<>();
		final GSimpleTraining mock3 = mock(GSimpleTraining.class);
		final GSimpleTraining mock4 = mock(GSimpleTraining.class);
		list2.add(mock3);
		list2.add(mock4);
		when(mock3.getDistance()).thenReturn("4.236");
		when(mock4.getDistance()).thenReturn("5.468");
		raw.put(0, list1);
		raw.put(1, list2);
		final Function<GSimpleTraining, Number> f = t -> Double.valueOf(t.getDistance());

		final Map<Integer, Double> result = aggregator.aggregateToSum(raw, f);

		assertEquals(3.704d, result.get(0).doubleValue(), 0.00001);
		assertEquals(9.704d, result.get(1).doubleValue(), 0.00001);
	}

	@Test
	public void testAggregateToIndexWeeks() {
		final List<GSimpleTraining> trainings = new ArrayList<>();
		trainings.add(createTraining(LocalDate.of(2017, 12, 28)));
		trainings.add(createTraining(LocalDate.of(2017, 12, 21)));
		trainings.add(createTraining(LocalDate.of(2018, 1, 12)));

		final Map<Integer, List<GSimpleTraining>> result = aggregator.aggregateToIndex(trainings, ChronoUnit.WEEKS);

		assertEquals(1, result.get(201751).size());
		assertEquals(1, result.get(201752).size());
		assertEquals(1, result.get(201802).size());
	}

	@Test
	public void testAggregateToIndexMonths() {
		final List<GSimpleTraining> trainings = new ArrayList<>();
		trainings.add(createTraining(LocalDate.of(2017, 12, 28)));
		trainings.add(createTraining(LocalDate.of(2017, 12, 21)));
		trainings.add(createTraining(LocalDate.of(2018, 1, 12)));

		final Map<Integer, List<GSimpleTraining>> result = aggregator.aggregateToIndex(trainings, ChronoUnit.MONTHS);

		assertEquals(2, result.get(201712).size());
		assertEquals(1, result.get(201801).size());
	}

	@Test
	public void testAggregateToIndexYears() {
		final List<GSimpleTraining> trainings = new ArrayList<>();
		trainings.add(createTraining(LocalDate.of(2017, 12, 28)));
		trainings.add(createTraining(LocalDate.of(2017, 12, 21)));
		trainings.add(createTraining(LocalDate.of(2018, 1, 12)));

		final Map<Integer, List<GSimpleTraining>> result = aggregator.aggregateToIndex(trainings, ChronoUnit.YEARS);

		assertEquals(2, result.get(2017).size());
		assertEquals(1, result.get(2018).size());
	}

	private GSimpleTraining createTraining(final LocalDate ld) {
		final GSimpleTraining gst = mock(GSimpleTraining.class);
		when(gst.getId()).thenReturn(Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime());
		return gst;
	}
}
