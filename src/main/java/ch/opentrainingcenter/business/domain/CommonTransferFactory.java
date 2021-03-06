package ch.opentrainingcenter.business.domain;

import java.util.Date;

public final class CommonTransferFactory {

    private CommonTransferFactory() {
    }

    public static Weather createDefaultWeather() {
        return createWeather(9);
    }

    static Weather createWeather(final int id) {
        return new Weather(id);
    }

    public static Health createHealth(final Athlete athlete, final Integer weight, final Integer cardio, final Date dateofmeasure) {
        return new Health(athlete, weight, cardio, dateofmeasure);
    }

    public static Training createTraining(final RunData runData, final HeartRate heart, final double maximumSpeed, final String note, final Weather weather,
                                          final Route route) {
        return new Training(runData, heart, note, weather, route);
    }

    public static Training createTraining(final RunData runData, final HeartRate heart) {
        return new Training(runData, heart, "", null, null);
    }

    /**
     * @param name             eindeutiger name der route
     * @param beschreibung     beschreibung
     * @param referenzTraining geografische Referenz der Streckenpunkte
     */
    public static Route createRoute(final String name, final String beschreibung, final Training referenzTraining) {
        return new Route(name, beschreibung, referenzTraining);
    }

    /**
     * @param distance  vom Startweg
     * @param heartbeat herzschlag an diesem Punkt
     * @param altitude  Höhe in Meter über Meer
     * @param time      absolute zeit
     * @param lap       Runden nummer
     * @return {@link Tracktrainingproperty}
     */
    public static Tracktrainingproperty createTrackPointProperty(final double distance, final int heartbeat, final int altitude, final long time, final int lap,
                                                                 final Double longitude, final Double latitude) {
        return new Tracktrainingproperty(distance, heartbeat, altitude, time, lap, longitude, latitude);
    }

    /**
     * Start und End beziehen sich immer auf die kilometrierung der strecke.
     *
     * <pre>
     *
     *                           LapInfo
     * |----------------------<--------->-----|
     * 0m                   140m       180m
     *
     *                     Start       End
     *
     *
     * </pre>
     *
     * @param lap             Runde [Anzahl]
     * @param start           start in Meter [m]
     * @param end             ende der runde in [m]
     * @param time            Zeit in Millisekunden [ms]
     * @param heartBeat       Herzschlag in [Bpm]
     * @param pace            Pace in [min/km]
     * @param geschwindigkeit durchschnittliche Geschwindigkeit [km/h]
     */
    public static LapInfo createLapInfo(final int lap, final int start, final int end, final long time, final int heartBeat, final String pace,
                                        final String geschwindigkeit) {
        return new LapInfo(lap, start, end, time, heartBeat, pace, geschwindigkeit);
    }

    public static Shoe createShoe(final Athlete athlete, final String shoeName, final String image, final int preis, final Date date) {
        final Shoe shoe = new Shoe();
        shoe.setAthlete(athlete);
        shoe.setSchuhname(shoeName);
        shoe.setImageicon(image);
        shoe.setPreis(preis);
        shoe.setKaufdatum(date);
        return shoe;
    }

    public static Athlete createAthlete(final String firstName, final String lastName, final String email, final String password) {
        return new Athlete(firstName, lastName, email, password);
    }

}
