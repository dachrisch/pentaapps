package de.pentasys.rexx.zenal;

import static de.pentasys.builder.DateTimeGenerator.date;
import static de.pentasys.builder.DateTimeGenerator.from;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItem;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;

import de.pentasys.builder.TimespanDateTime;
import de.pentasys.rexx.builder.RexxJourney;
import de.pentasys.rexx.entities.RexxTrip;
import de.pentasys.rexx.entities.TripCities;
import de.pentasys.zenal.ZenalEntry;
import de.pentasys.zenal.ZenalEntryList;
import de.pentasys.zenal.toggl.TogglRetriever;

public class ParseTogglCsvAndCreateRexxTrips {
    @Test
    public void filterForTrips() throws Exception {
        final ZenalEntryList zenalEntries = readFromCsv();

        final List<ZenalEntry> travelEntries = ZenalToRexxJourneyCreator.filterTripCategories(zenalEntries);

        assertThat(travelEntries.size(), is(10));
    }

    @Test
    public void filterForTimespan() throws Exception {
        final ZenalEntryList zenalEntries = readFromCsv();

        assertThat(
                ZenalToRexxJourneyCreator.filterTimespan(zenalEntries,
                        new TimespanDateTime(date(2011, 5, 8), date(2011, 5, 9))).size(), is(0));
        assertThat(
                ZenalToRexxJourneyCreator.filterTimespan(zenalEntries,
                        new TimespanDateTime(date(2011, 5, 9), date(2011, 5, 10))).size(), is(3));
        assertThat(
                ZenalToRexxJourneyCreator.filterTimespan(zenalEntries,
                        new TimespanDateTime(date(2011, 5, 9), date(2011, 5, 11))).size(), is(6));
        assertThat(
                ZenalToRexxJourneyCreator.filterTimespan(zenalEntries,
                        new TimespanDateTime(date(2011, 5, 9), new DateTime(2011, 5, 11, 18, 20, 0, 0))).size(), is(8));
        assertThat(
                ZenalToRexxJourneyCreator.filterTimespan(zenalEntries,
                        new TimespanDateTime(date(2011, 5, 13), date(2011, 5, 14))).size(), is(3));
        assertThat(
                ZenalToRexxJourneyCreator.filterTimespan(zenalEntries,
                        new TimespanDateTime(date(2011, 5, 14), date(2011, 5, 15))).size(), is(0));
    }

    @Test
    public void parseCsvAndMapToRexxTrips() throws Exception {
        final ZenalEntryList zenalEntries = readFromCsv();

        final List<ZenalEntry> travelEntries = ZenalToRexxJourneyCreator.filterTripCategories(zenalEntries);

        assertThat(travelEntries.size(), is(10));

        final List<RexxTrip> trips = new ArrayList<RexxTrip>();
        for (int i = 0; i < travelEntries.size(); i += 2) {
            final ZenalEntry arrivalEntry = travelEntries.get(i);
            final ZenalEntry leavingEntry = travelEntries.get(i + 1);
            trips.add(new ZenalToRexxTripConverter().convert(leavingEntry, arrivalEntry));
        }

        assertThat(trips.size(), is(5));
    }

    @Test
    public void parseCsvAndCreateJourneyWithTrips() throws Exception {
        final ZenalEntryList zenalEntries = readFromCsv();

        final RexxJourney journey = new ZenalToRexxJourneyCreator(zenalEntries).createJourney(from(date(2011, 5, 9))
                .till(date(2011, 5, 14)));

        assertThat(journey.getJourneyFrom(), is(new DateTime(2011, 5, 9, 7, 50, 0, 0)));
        assertThat(journey.getJourneyTill(), is(new DateTime(2011, 5, 13, 17, 15, 0, 0)));

        assertThat(journey.getArrivalCity(), is("Ingolstadt"));
        assertThat(journey.getLeavingCity(), is("MUC"));

        assertThat(journey.getTrips().size(), is(5));
        assertThat(journey.getTrips(),
                hasItem(trip(new DateTime(2011, 5, 9, 7, 50, 0, 0), new DateTime(2011, 5, 9, 19, 10, 0, 0))));
        assertThat(journey.getTrips(),
                hasItem(trip(new DateTime(2011, 5, 10, 7, 50, 0, 0), new DateTime(2011, 5, 10, 20, 15, 0, 0))));
        assertThat(journey.getTrips(),
                hasItem(trip(new DateTime(2011, 5, 11, 7, 50, 0, 0), new DateTime(2011, 5, 11, 20, 15, 0, 0))));
        assertThat(journey.getTrips(),
                hasItem(trip(new DateTime(2011, 5, 12, 7, 50, 0, 0), new DateTime(2011, 5, 12, 20, 15, 0, 0))));
        assertThat(journey.getTrips(),
                hasItem(trip(new DateTime(2011, 5, 13, 7, 50, 0, 0), new DateTime(2011, 5, 13, 17, 15, 0, 0))));
    }

    protected RexxTrip trip(final DateTime from, final DateTime till) {
        return new RexxTrip(new TripCities("MUC", "Ingolstadt"), new TimespanDateTime(from, till),
                "Projekteinsatz [P080811.MED]");
    }

    private ZenalEntryList readFromCsv() {
        final ZenalEntryList zenalEntries = new TogglRetriever()
                .readEntriesFromCsv("src/test/resources/whole_week_report.csv");

        assertThat(zenalEntries.size(), is(15));
        return zenalEntries;
    }
}
