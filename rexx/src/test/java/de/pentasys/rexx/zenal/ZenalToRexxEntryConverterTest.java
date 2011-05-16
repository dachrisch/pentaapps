package de.pentasys.rexx.zenal;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.joda.time.DateTime;
import org.junit.Test;

import de.pentasys.rexx.entities.RexxTrip;
import de.pentasys.zenal.ZenalEntry;
import de.pentasys.zenal.builder.Category;
import de.pentasys.zenal.builder.Project;

public class ZenalToRexxEntryConverterTest {

    @Test
    public void convertZenalToRexxEntry() throws Exception {
        final ZenalEntry zenalArrivalEntry = new ZenalEntry(Project.MEDIASATURN, new DateTime(), new DateTime(),
                "Anfahrt City Arrival", Category.TRAVEL_START);
        final ZenalEntry zenalReturnEntry = new ZenalEntry(Project.MEDIASATURN, new DateTime(), new DateTime(),
                "Rückfahrt City Leaving", Category.TRAVEL_END);

        final RexxTrip trip = new ZenalToRexxConverter().convert(zenalArrivalEntry, zenalReturnEntry);

        assertThat(trip.getArrivalCity(), is("City Arrival"));
        assertThat(trip.getLeavingCity(), is("City Leaving"));
    }
}
