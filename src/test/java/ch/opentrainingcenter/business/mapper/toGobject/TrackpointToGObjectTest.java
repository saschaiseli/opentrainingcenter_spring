package ch.opentrainingcenter.business.mapper.toGobject;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ch.opentrainingcenter.business.domain.Tracktrainingproperty;
import ch.opentrainingcenter.gui.model.GTrackPoint;

public class TrackpointToGObjectTest {

	private static final long TIME = 600L;
	private static final double LONG = 500d;
	private static final double LAT = 400d;
	private static final int LAP = 0;
	private static final int ID = 42;
	private static final int HEARTBEAT = 300;
	private static final double DIST = 200d;
	private static final int ALTITUDE = 100;

	@Test
	public void testConvert() {
		final TrackpointToGObject converter = new TrackpointToGObject();
		final Tracktrainingproperty p = new Tracktrainingproperty();
		p.setAltitude(ALTITUDE);
		p.setDistance(DIST);
		p.setHeartBeat(HEARTBEAT);
		p.setId(ID);
		p.setLap(LAP);
		p.setLatitude(LAT);
		p.setLongitude(LONG);
		p.setZeit(TIME);

		final GTrackPoint result = converter.convert(p);

		assertEquals(ALTITUDE, result.getAltitude());
		assertEquals(DIST, result.getDistance(), 0.00001);
		assertEquals(HEARTBEAT, result.getHeartbeat());
		assertEquals(LAP, result.getLap());
		assertEquals(LAT, result.getLatitude(), 0.00001);
		assertEquals(LONG, result.getLongitude(), 0.00001);
		assertEquals(TIME, result.getZeit());
	}

}
