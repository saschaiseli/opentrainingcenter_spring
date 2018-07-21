package ch.opentrainingcenter.business.domain;

import java.util.Date;

/**
 * Info zu einem Lauf
 */
public class RunData {

    private final Date date;
    private final long timeInSeconds;
    private final long distanceInMeter;
    private final double maxSpeed;

    /**
     * @param date            Zeitpunkt des Startes in ms
     * @param timeInSeconds   Dauer des Laufes in Sekunden
     * @param distanceInMeter Distanz in meter
     */
    public RunData(final Date date, final long timeInSeconds, final long distanceInMeter, final double maxSpeed) {
        this.date = date;
        this.timeInSeconds = timeInSeconds;
        this.distanceInMeter = distanceInMeter;
        this.maxSpeed = maxSpeed;
    }

    /**
     * @return Zeitpunkt des Startes in ms
     */
    Date getDateOfStart() {
        return date;
    }

    /**
     * @return Dauer des Laufes in Sekunden
     */
    long getTimeInSeconds() {
        return timeInSeconds;
    }

    /**
     * @return Distanz in meter
     */
    long getDistanceInMeter() {
        return distanceInMeter;
    }

    /**
     * @return maximale geschwindigkeit
     */
    double getMaxSpeed() {
        return maxSpeed;
    }
}
