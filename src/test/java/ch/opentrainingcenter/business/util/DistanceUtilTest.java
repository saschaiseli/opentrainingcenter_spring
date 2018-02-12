package ch.opentrainingcenter.business.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DistanceUtilTest {

	@Test
	public void testConvert() {
		final String roundDistance = DistanceUtil.roundDistanceFromMeterToKmMitEinheit(10123.4567890);
		assertEquals("10.123 km", roundDistance);
	}

	@Test
	public void testConvertLessZeros() {
		final String roundDistance = DistanceUtil.roundDistanceFromMeterToKmMitEinheit(0.0);
		assertEquals("0.000 km", roundDistance);
	}

	@Test
	public void testConvertNoZeros() {
		final String roundDistance = DistanceUtil.roundDistanceFromMeterToKm(4);
		assertEquals("0.004", roundDistance);
	}

	@Test
	public void testConvertBig() {
		final String roundDistance = DistanceUtil.roundDistanceFromMeterToKmMitEinheit(10123456.7890);
		assertEquals("10123.457 km", roundDistance);
	}

	@Test
	public void testConvertSmall() {
		final String roundDistance = DistanceUtil.roundDistanceFromMeterToKmMitEinheit(12.34567890);
		assertEquals("0.012 km", roundDistance);
	}

	@Test
	public void testConvertSmallRoundUp() {
		final String roundDistance = DistanceUtil.roundDistanceFromMeterToKmMitEinheit(12.64567890);
		assertEquals("0.013 km", roundDistance);
	}

	@Test
	public void testCalculatePace() {
		final String pace = DistanceUtil.calculatePace(1000, 300);
		assertEquals("5:00", pace);
	}

	@Test
	public void testCalculatePaceSekunden() {
		final String pace = DistanceUtil.calculatePace(1000, 315);
		assertEquals("5:15", pace);
	}

	@Test
	public void testCalculatePaceFromMperSecond() {
		final String pace = DistanceUtil.calculatePace(6.32446337);
		assertEquals("2:38", pace);
	}

}
