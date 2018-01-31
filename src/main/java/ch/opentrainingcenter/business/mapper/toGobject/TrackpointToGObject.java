package ch.opentrainingcenter.business.mapper.toGobject;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import ch.opentrainingcenter.business.domain.Tracktrainingproperty;
import ch.opentrainingcenter.business.mapper.MapToGObject;
import ch.opentrainingcenter.gui.model.GTrackPoint;

@Service
@Secured({ "ROLE_ADMIN" })
public class TrackpointToGObject implements MapToGObject<GTrackPoint, Tracktrainingproperty> {

	@Override
	public GTrackPoint convert(final Tracktrainingproperty p) {
		return new GTrackPoint(p.getDistance(), p.getHeartBeat(), p.getAltitude(), p.getZeit(), p.getLap(),
				p.getLongitude(), p.getLatitude());
	}

}
