package ch.opentrainingcenter.business.service.fileconverter.fit;

import ch.opentrainingcenter.business.domain.LapInfo;
import ch.opentrainingcenter.business.domain.Sport;
import ch.opentrainingcenter.business.domain.Tracktrainingproperty;
import ch.opentrainingcenter.business.domain.Training;
import ch.opentrainingcenter.business.service.fileconverter.FileConvertException;
import org.junit.Before;
import org.junit.Test;

import javax.naming.NamingException;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import static org.junit.Assert.*;

public class FitFileConverterTest {
    private static final String FOLDER = "src/test/resources/fit";
    private final FitFileConverter service = new FitFileConverter();

    @Before
    public void setUp() {
        Locale.setDefault(Locale.GERMAN);
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Zurich"));
    }

    @Test
    public void testIntervallTraining() throws FileNotFoundException, FileConvertException {
        final Path file = Paths.get(FOLDER, "6ACB4237.FIT");

        final Training training = service.convert(file);
        final long dauer = training.getDauer();
        assertEquals(5641, dauer);
    }

    @Test
    public void testActivityConvertMit2Runden() throws FileNotFoundException, FileConvertException {
        final Path file = Paths.get(FOLDER, "2_runden.fit");

        final Training training = service.convert(file);

        assertNotNull(training);
        assertNull("Ist null, da dieser Timestamp erst vom importer gesetzt", training.getDateOfImport());

        final List<LapInfo> lapInfos = training.getLapInfos();
        assertEquals(2, lapInfos.size());

        final LapInfo lap1 = lapInfos.get(0);
        assertEquals(0, lap1.getLap());
        assertEquals(0, lap1.getStart());
        assertEquals(5495, lap1.getEnd());
        assertEquals(1959000, lap1.getTime());
        assertEquals(DistanceHelper.calculatePace(5495, 1959, Sport.RUNNING), lap1.getPace());
        assertEquals(DistanceHelper.calculatePace(5495, 1959, Sport.BIKING), lap1.getGeschwindigkeit());

        final LapInfo lap2 = lapInfos.get(1);
        assertEquals(1, lap2.getLap());
        assertEquals(5495, lap2.getStart());
        assertEquals(10499, lap2.getEnd()); // kleiner rundungsfehler
        assertEquals(1745000, lap2.getTime());
        assertEquals(DistanceHelper.calculatePace(10500 - 5495, lap2.getTime() / 1000, Sport.RUNNING), lap2.getPace());
        assertEquals(DistanceHelper.calculatePace(10500 - 5495, lap2.getTime() / 1000, Sport.BIKING),
                lap2.getGeschwindigkeit());
    }

    @Test
    public void testRealActivityConvert() throws ParseException, FileNotFoundException, FileConvertException {
        final Training training = service.convert(Paths.get(FOLDER, "2014_09_11.fit"));

        assertNotNull(training);
        assertNull("Ist null, da dieser Timestamp erst vom importer gesetzt", training.getDateOfImport());
        assertEquals("Lauf startet um 2014-09-11 19:18:35", convertToDate("2014-09-11 19:18:35"),
                training.getDateOfStart().getTime());
        assertEquals("<TotalTimeSeconds>2003.2</TotalTimeSeconds>", 2003, training.getDauer());
        assertEquals("<DistanceMeters>5297.08</DistanceMeters>", 5297, training.getLaengeInMeter());
        assertEquals("<MaximumSpeed>5.067</MaximumSpeed>", 5.067, training.getMaxSpeed(), 0.001);
        assertEquals("AverageHeartRateBpm", 132, training.getAverageHeartBeat());
        assertEquals("MaximumHeartRateBpm", 159, training.getMaxHeartBeat());

        assertEquals("Positive Höhenmeter", 198, training.getUpMeter().intValue());
        assertEquals("Negative Höhenmeter", 195, training.getDownMeter().intValue());

        assertEquals(Sport.RUNNING, training.getSport());

        final List<Tracktrainingproperty> points = training.getTrackPoints();

        assertTrackPoint(0, "2014-09-11 19:18:35", 0.0, 558.0, 46.95485311, 7.449236233, points.get(0), training);
        assertTrackPoint(1, "2014-09-11 19:18:43", 12.57, 555.0, 46.95496534, 7.449198263, points.get(1), training);
        assertTrackPoint(2, "2014-09-11 19:18:48", 31.83, 555.0, 46.95501345, 7.448971029, points.get(2), training);
        assertTrackPoint(3, "2014-09-11 19:18:52", 45.68, 554.0, 46.95496056, 7.448805906, points.get(3), training);
        assertTrackPoint(4, "2014-09-11 19:18:55", 51.47, 554.0, 46.95492133, 7.448757458, points.get(4), training);
        // noch letzter punkt
        assertTrackPoint(301, "2014-09-11 19:51:57", 5294.86, 562.0, 46.9547371, 7.448976813,
                points.get(points.size() - 1), training);

        final List<LapInfo> lapInfos = training.getLapInfos();
        assertEquals(1, lapInfos.size());
        final LapInfo lap = lapInfos.get(0);
        assertEquals(0, lap.getStart());
        assertEquals(training, lap.getTraining());
        assertEquals((int) training.getLaengeInMeter(), lap.getEnd());
        assertEquals(2003000, lap.getTime());
        assertEquals(132, lap.getHeartBeat());
        assertEquals(DistanceHelper.calculatePace(training.getLaengeInMeter(), lap.getTime() / 1000, Sport.RUNNING),
                lap.getPace());
        assertEquals(DistanceHelper.calculatePace(training.getLaengeInMeter(), lap.getTime() / 1000, Sport.BIKING),
                lap.getGeschwindigkeit());
        assertEquals(29, training.getTrainingEffect().intValue());
    }

    private void assertTrackPoint(final int index, final String datum, final double distanz, final double hoehe,
                                  final double latitude, final double longitude, final Tracktrainingproperty point, final Training training)
            throws ParseException {
        final String punkt = "[Punkt " + index + "] ";
        final long time = convertToDate(datum);
        final long convertedTime = point.getZeit();
        assertEquals(punkt + "Distanz vom Punkt", distanz, point.getDistance(), 0.001);
        assertEquals(punkt + "Differenz muss 0 [ms] sein", 0, convertedTime - time);
        assertEquals(punkt + "Höhe des Punktes", hoehe, point.getAltitude(), 0.001);
        assertEquals(punkt + "LatitudeDegrees", latitude, point.getLatitude(), 0.00000001);
        assertEquals(punkt + "LongitudeDegrees", longitude, point.getLongitude(), 0.00000001);
    }

    private long convertToDate(final String datum) throws ParseException {
        final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.parse(datum).getTime();
    }

    @Test
    public void testGetFilePrefix() throws NamingException {
        // final ConvertFitEJB service = (ConvertFitEJB)
        // ctx.lookup("java:global/classes/ConvertFitEJB");
        System.out.println("ysdfsdfsdfsdf");
        assertEquals("fit", service.getFileSuffix());
    }

}
