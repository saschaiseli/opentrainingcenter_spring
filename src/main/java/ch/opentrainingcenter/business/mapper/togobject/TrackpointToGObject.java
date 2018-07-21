package ch.opentrainingcenter.business.mapper.togobject;

import ch.opentrainingcenter.business.domain.Tracktrainingproperty;
import ch.opentrainingcenter.gui.model.GTrackPoint;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

@Service
@Secured({"ROLE_ADMIN"})
public class TrackpointToGObject implements Converter<Tracktrainingproperty, GTrackPoint> {

    @Override
    public GTrackPoint convert(final Tracktrainingproperty p) {
        return new GTrackPoint(p.getDistance(), p.getHeartBeat(), p.getAltitude(), p.getZeit(), p.getLap(),
                p.getLongitude(), p.getLatitude());
    }

}
