package ch.opentrainingcenter.business.util;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

public class DateUtilTest {

	private LocalDate now;

	@Before
	public void setUp() {
		now = LocalDate.of(2018, 1, 24); // 24.1.2018
	}

	@Test
	public void testGetLastDayOfWeekGermany() {
		final LocalDate lastDayOfWeek = DateUtil.getLastDayOfWeek(now, Locale.GERMAN);
		assertEquals("Sunday is the last day", LocalDate.of(2018, 1, 28), lastDayOfWeek);
	}

	@Test
	public void testGetLastDayOfWeekUS() {
		final LocalDate lastDayOfWeek = DateUtil.getLastDayOfWeek(now, Locale.US);
		assertEquals("Saturday is the last day", LocalDate.of(2018, 1, 27), lastDayOfWeek);
	}

	@Test
	public void testGetIndexWeek() {
		final LocalDate ld = LocalDate.of(2018, 1, 15); // 20 Dezember 2017
		final Date date = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());

		final int index = DateUtil.getIndex(date, ChronoUnit.WEEKS);

		assertEquals(201803, index);
	}

	@Test
	public void testGetIndexWeek2017() {
		final LocalDate ld = LocalDate.of(2017, 12, 28); // 28 Dezember 2017
		final Date date = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());

		final int index = DateUtil.getIndex(date, ChronoUnit.WEEKS);

		assertEquals(201752, index);
	}

	@Test
	public void testGetIndexMonth() {
		final LocalDate ld = LocalDate.of(2018, 1, 15); // 20 Dezember 2017
		final Date date = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());

		final int index = DateUtil.getIndex(date, ChronoUnit.MONTHS);

		assertEquals(201801, index);
	}

	@Test
	public void testGetIndexMonth2017() {
		final LocalDate ld = LocalDate.of(2017, 12, 28); // 28 Dezember 2017
		final Date date = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());

		final int index = DateUtil.getIndex(date, ChronoUnit.MONTHS);

		assertEquals(201712, index);
	}

	@Test
	public void testGetIndexYear() {
		final LocalDate ld = LocalDate.of(2018, 1, 15); // 20 Dezember 2017
		final Date date = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());

		final int index = DateUtil.getIndex(date, ChronoUnit.YEARS);

		assertEquals(2018, index);
	}

	@Test
	public void testGetIndexYear2017() {
		final LocalDate ld = LocalDate.of(2017, 12, 28); // 28 Dezember 2017
		final Date date = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());

		final int index = DateUtil.getIndex(date, ChronoUnit.YEARS);

		assertEquals(2017, index);
	}
}
