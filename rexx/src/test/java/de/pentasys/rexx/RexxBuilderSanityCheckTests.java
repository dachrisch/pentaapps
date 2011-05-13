package de.pentasys.rexx;

import static de.pentasys.rexx.builder.RexxJourneyBuilder.doJourney;
import static de.pentasys.zenal.builder.DateTimeGenerator.datetime;
import static de.pentasys.zenal.builder.DateTimeGenerator.from;
import static junit.framework.Assert.fail;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.joda.time.DateTime;
import org.junit.Test;

import de.pentasys.zenal.builder.Project;
import de.pentasys.zenal.builder.TimespanDateTime;

/**
 * 
 * @author cda
 */
public class RexxBuilderSanityCheckTests {

    @Test
    public void tripsCreatedIsInsideJournedDate() {
        doJourney(Project.MEDIASATURN).starting(datetime(2011, 5, 5, 7, 50)).from("München").till(
                datetime(2011, 5, 15, 17, 50)).to("Ingolstadt").withTrip(
                from(datetime(2011, 5, 5, 7, 50)).till(datetime(2011, 5, 15, 17, 50)), "projekteinsatz");
    }

    @Test
    public void tripCreatedIsBeforeJournedDate() {
        try {
            doJourney(Project.MEDIASATURN).starting(datetime(2011, 5, 5, 7, 50)).from("München").till(
                    datetime(2011, 5, 15, 17, 50)).to("Ingolstadt").withTrip(
                    from(datetime(2011, 5, 5, 7, 40)).till(datetime(2011, 5, 15, 17, 50)), "projekteinsatz");
            fail("trip is before journey start. should have failed");
        } catch (final IllegalArgumentException e) {
            // pass
        }
    }

    @Test
    public void tripCreatedIsAfterJournedDate() {
        try {
            doJourney(Project.MEDIASATURN).starting(datetime(2011, 5, 5, 7, 50)).from("München").till(
                    datetime(2011, 5, 15, 17, 50)).to("Ingolstadt").withTrip(
                    from(datetime(2011, 5, 5, 7, 50)).till(datetime(2011, 5, 15, 17, 51)), "projekteinsatz");
            fail("trip is after journey start. should have failed");
        } catch (final IllegalArgumentException e) {
            // pass
        }
    }

    @Test
    public void tripIsValidAsStartIsBeforeEnd() {
        try {
            from(datetime(2011, 5, 5, 17, 50)).till(datetime(2011, 5, 5, 7, 50));
            fail("trips start is after its end should fail");
        } catch (final IllegalArgumentException e) {
            // pass
        }
        final TimespanDateTime timespan = from(datetime(2011, 5, 5, 7, 50)).till(datetime(2011, 5, 5, 17, 50));
        assertThat(timespan, notNullValue());
    }

    @Test
    public void journeyIsValidAsStartIsBeforeEnd() throws Exception {
        final DateTime now = new DateTime();
        try {
            doJourney(Project.MEDIASATURN).starting(now).from("a").till(now.minusMonths(2));
            fail("journey start is after its end and should have failed");
        } catch (final IllegalArgumentException e) {
            // pass
        }
    }
}
