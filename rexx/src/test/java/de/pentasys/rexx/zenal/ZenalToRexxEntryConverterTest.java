package de.pentasys.rexx.zenal;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.junit.matchers.JUnitMatchers.containsString;

import org.joda.time.DateTime;
import org.junit.Test;

import de.pentasys.builder.Category;
import de.pentasys.builder.Project;
import de.pentasys.rexx.entities.RexxTrip;
import de.pentasys.zenal.ZenalEntry;

public class ZenalToRexxEntryConverterTest {

    @Test
    public void convertZenalToRexxEntry() throws Exception {
        final RexxTrip trip = new ZenalToRexxTripConverter().convert(endEntry(), startEntry());

        assertThat(trip.getArrivalCity(), is("City Arrival"));
        assertThat(trip.getLeavingCity(), is("City Leaving"));
    }

    @Test
    public void zenalEntryForLeavingHasToBeOfCategoryTravel_End() throws Exception {
        try {
            new ZenalToRexxTripConverter().convert(startEntry(), startEntry());
            fail("expected to fail, as leaving has wrong category");
        } catch (final IllegalArgumentException e) {
            assertThat(e.getMessage(),
                    containsString(String.format("not of type [%s]", Category.TRAVEL_END.toString())));
        }
    }

    @Test
    public void zenalEntryForArrivalHasToBeOfCategoryTravel_Start() throws Exception {
        try {
            new ZenalToRexxTripConverter().convert(endEntry(), endEntry());
            fail("expected to fail, as arrival has wrong category");
        } catch (final IllegalArgumentException e) {
            assertThat(e.getMessage(),
                    containsString(String.format("not of type [%s]", Category.TRAVEL_START.toString())));
        }
    }

    @Test
    public void zenalEntryFoBothHasToBeOfCategoryTravel_StartAndTravel_EndRespectively() throws Exception {
        try {
            new ZenalToRexxTripConverter().convert(startEntry(), endEntry());
            fail("expected to fail, as both has wrong category");
        } catch (final IllegalArgumentException e) {
            assertThat(e.getMessage(),
                    containsString(String.format("not of type [%s]", Category.TRAVEL_START.toString())));
        }
    }

    private ZenalEntry endEntry() {
        final ZenalEntry zenalLeavingEntry = new ZenalEntry(Project.MEDIASATURN, new DateTime(), new DateTime(),
                "Rückfahrt City Leaving", Category.TRAVEL_END);
        return zenalLeavingEntry;
    }

    private ZenalEntry startEntry() {
        final ZenalEntry zenalArrivalEntry = new ZenalEntry(Project.MEDIASATURN, new DateTime(), new DateTime(),
                "Anfahrt City Arrival", Category.TRAVEL_START);
        return zenalArrivalEntry;
    }

}
