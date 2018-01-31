package ch.opentrainingcenter.business.service.chart;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ch.opentrainingcenter.business.repositories.AppConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@PropertySource("classpath:application-test.properties")
@SpringBootTest(classes = { AppConfig.class })
@WithMockUser(username = "dummy", roles = { "ADMIN" })
public class ChartIndexCompleteServiceTest {

	@Autowired
	private ChartIndexCompleteService service;

	private Map<Integer, Double> raw;

	@Test
	public void testFillitUp_Week() {
		raw = new HashMap<>();
		raw.put(201751, 42d);

		final LocalDate now = LocalDate.of(2018, 1, 22);

		final Map<Integer, Double> result = service.fill(raw, ChronoUnit.WEEKS, now, 6);

		assertEquals(0d, result.get(201804).doubleValue(), 0.0001);
		assertEquals(0d, result.get(201803).doubleValue(), 0.0001);
		assertEquals(0d, result.get(201802).doubleValue(), 0.0001);
		assertEquals(0d, result.get(201801).doubleValue(), 0.0001);
		assertEquals(0d, result.get(201752).doubleValue(), 0.0001);
		assertEquals(42d, result.get(201751).doubleValue(), 0.0001);
		assertEquals(6, result.size());
	}

	@Test
	public void testFillAleradyComplete_Week() {
		raw = new HashMap<>();
		raw.put(201804, 4d);
		raw.put(201803, 3d);
		raw.put(201802, 2d);
		raw.put(201801, 1d);
		raw.put(201752, 52d);
		raw.put(201751, 51d);

		final LocalDate now = LocalDate.of(2018, 1, 22);

		final Map<Integer, Double> result = service.fill(raw, ChronoUnit.WEEKS, now, 6);

		assertEquals(4d, result.get(201804).doubleValue(), 0.0001);
		assertEquals(3d, result.get(201803).doubleValue(), 0.0001);
		assertEquals(2d, result.get(201802).doubleValue(), 0.0001);
		assertEquals(1d, result.get(201801).doubleValue(), 0.0001);
		assertEquals(52d, result.get(201752).doubleValue(), 0.0001);
		assertEquals(51d, result.get(201751).doubleValue(), 0.0001);
		assertEquals(6, result.size());
	}

	@Test
	public void testFillWrong_deleteWrongValue_Week() {
		raw = new HashMap<>();
		raw.put(201748, 51d);

		final LocalDate now = LocalDate.of(2018, 1, 22);

		final Map<Integer, Double> result = service.fill(raw, ChronoUnit.WEEKS, now, 6);

		assertEquals(0d, result.get(201804).doubleValue(), 0.0001);
		assertEquals(0d, result.get(201803).doubleValue(), 0.0001);
		assertEquals(0d, result.get(201802).doubleValue(), 0.0001);
		assertEquals(0d, result.get(201801).doubleValue(), 0.0001);
		assertEquals(0d, result.get(201752).doubleValue(), 0.0001);
		assertEquals(0d, result.get(201751).doubleValue(), 0.0001);
		assertEquals(6, result.size());

		assertNull(result.get(201748));
	}

	@Test
	public void testFillitUp_Month() {
		raw = new HashMap<>();
		raw.put(201712, 42d);

		final LocalDate now = LocalDate.of(2018, 1, 22);

		final Map<Integer, Double> result = service.fill(raw, ChronoUnit.MONTHS, now, 6);

		assertEquals(0d, result.get(201801).doubleValue(), 0.0001);
		assertEquals(42d, result.get(201712).doubleValue(), 0.0001);
		assertEquals(0d, result.get(201711).doubleValue(), 0.0001);
		assertEquals(0d, result.get(201710).doubleValue(), 0.0001);
		assertEquals(0d, result.get(201709).doubleValue(), 0.0001);
		assertEquals(0d, result.get(201708).doubleValue(), 0.0001);
		assertEquals(6, result.size());
	}

	@Test
	public void testFillitUpComplete_Month() {
		raw = new HashMap<>();
		raw.put(201801, 01d);
		raw.put(201712, 12d);
		raw.put(201711, 11d);
		raw.put(201710, 10d);
		raw.put(201709, 9d);
		raw.put(201708, 8d);

		final LocalDate now = LocalDate.of(2018, 1, 22);

		final Map<Integer, Double> result = service.fill(raw, ChronoUnit.MONTHS, now, 6);

		assertEquals(01d, result.get(201801).doubleValue(), 0.0001);
		assertEquals(12d, result.get(201712).doubleValue(), 0.0001);
		assertEquals(11d, result.get(201711).doubleValue(), 0.0001);
		assertEquals(10d, result.get(201710).doubleValue(), 0.0001);
		assertEquals(9d, result.get(201709).doubleValue(), 0.0001);
		assertEquals(8d, result.get(201708).doubleValue(), 0.0001);
		assertEquals(6, result.size());
	}

	@Test
	public void testFillitUp_deleteWrong_Month() {
		raw = new HashMap<>();
		raw.put(201802, 01d);
		raw.put(201801, 01d);
		raw.put(201712, 12d);
		raw.put(201711, 11d);
		raw.put(201710, 10d);
		raw.put(201709, 9d);
		raw.put(201708, 8d);

		final LocalDate now = LocalDate.of(2018, 1, 22);

		final Map<Integer, Double> result = service.fill(raw, ChronoUnit.MONTHS, now, 6);

		assertEquals(01d, result.get(201801).doubleValue(), 0.0001);
		assertEquals(12d, result.get(201712).doubleValue(), 0.0001);
		assertEquals(11d, result.get(201711).doubleValue(), 0.0001);
		assertEquals(10d, result.get(201710).doubleValue(), 0.0001);
		assertEquals(9d, result.get(201709).doubleValue(), 0.0001);
		assertEquals(8d, result.get(201708).doubleValue(), 0.0001);
		assertEquals(6, result.size());
	}

	@Test
	public void testFillitUp_Year() {
		raw = new HashMap<>();
		raw.put(2017, 42d);

		final LocalDate now = LocalDate.of(2018, 1, 22);

		final Map<Integer, Double> result = service.fill(raw, ChronoUnit.YEARS, now, 2);

		assertEquals(0d, result.get(2018).doubleValue(), 0.0001);
		assertEquals(42d, result.get(2017).doubleValue(), 0.0001);
		assertEquals(2, result.size());
	}

	@Test
	public void testFill_Complete_Year() {
		raw = new HashMap<>();
		raw.put(2017, 42d);
		raw.put(2018, 142d);

		final LocalDate now = LocalDate.of(2018, 1, 22);

		final Map<Integer, Double> result = service.fill(raw, ChronoUnit.YEARS, now, 2);

		assertEquals(142d, result.get(2018).doubleValue(), 0.0001);
		assertEquals(42d, result.get(2017).doubleValue(), 0.0001);
		assertEquals(2, result.size());
	}

	@Test
	public void testFill_deleteWrong_Year() {
		raw = new HashMap<>();
		raw.put(2017, 42d);
		raw.put(2018, 142d);
		raw.put(2019, 142d);
		raw.put(2011, 142d);

		final LocalDate now = LocalDate.of(2018, 1, 22);

		final Map<Integer, Double> result = service.fill(raw, ChronoUnit.YEARS, now, 2);

		assertEquals(142d, result.get(2018).doubleValue(), 0.0001);
		assertEquals(42d, result.get(2017).doubleValue(), 0.0001);
		assertEquals(2, result.size());
	}
}
