package ch.opentrainingcenter.business.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.Date;
import java.util.Locale;

public final class DateUtil {
	private DateUtil() {

	}

	public static LocalDate getLastDayOfWeek(final LocalDate now, final Locale locale) {
		final LocalDate nextWeekEnd = LocalDate.now().with(WeekFields.of(locale).dayOfWeek(), 7);
		return nextWeekEnd;
	}

	public static int getIndex(final Date date, final ChronoUnit unit) {
		switch (unit) {
		case WEEKS:
			return getWeekNumber(date) + 100 * getYear(date);
		case MONTHS:
			return getMonth(date) + 100 * getYear(date);
		case YEARS:
			return getYear(date);
		default:
			throw new IllegalArgumentException("Wrong Chronounit: " + unit);
		}
	}

	private static int getMonth(final Date date) {
		final LocalDate ld = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return ld.getMonthValue();
	}

	private static int getWeekNumber(final Date date) {
		final LocalDate ld = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		final WeekFields weekFields = WeekFields.of(Locale.getDefault());
		return ld.get(weekFields.weekOfWeekBasedYear());
	}

	private static int getYear(final Date date) {
		final LocalDate ld = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return ld.getYear();
	}

	public static String format(final Date date) {
		final LocalDateTime ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
		return ldt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
	}
}
