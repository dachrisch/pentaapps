package de.pentasys.rexx.toggl;

import static ch.lambdaj.Lambda.having;
import static ch.lambdaj.Lambda.joinFrom;
import static ch.lambdaj.Lambda.on;
import static ch.lambdaj.Lambda.select;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.either;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import de.pentasys.builder.Category;
import de.pentasys.rexx.entities.RexxTrip;
import de.pentasys.rexx.zenal.ZenalToRexxConverter;
import de.pentasys.zenal.ZenalEntry;
import de.pentasys.zenal.ZenalEntryList;
import de.pentasys.zenal.toggl.TogglRetriever;

public class ParseTogglCsvAndCreateRexxTrips {
    @Test
    public void parseAnCsvAndFilterForTrips() throws Exception {
        final ZenalEntryList zenalEntries = new TogglRetriever()
                .readEntriesFromCsv("src/test/resources/whole_week_report.csv");

        assertThat(zenalEntries.size(), is(15));
        joinFrom(zenalEntries).getCategory();

        List<ZenalEntry> travelEntries = select(
                zenalEntries,
                having(on(ZenalEntry.class).getCategory(),
                        either(is(Category.TRAVEL_START.getCategory())).or(is(Category.TRAVEL_END.getCategory()))));

        assertThat(travelEntries.size(), is(10));
    }

    @Test
    public void parseAnCsvAndMapsToRexxTrips() throws Exception {
        final ZenalEntryList zenalEntries = new TogglRetriever()
                .readEntriesFromCsv("src/test/resources/whole_week_report.csv");

        assertThat(zenalEntries.size(), is(15));
        joinFrom(zenalEntries).getCategory();

        List<ZenalEntry> travelEntries = select(
                zenalEntries,
                having(on(ZenalEntry.class).getCategory(),
                        either(is(Category.TRAVEL_START.getCategory())).or(is(Category.TRAVEL_END.getCategory()))));

        assertThat(travelEntries.size(), is(10));

        List<RexxTrip> trips = new ArrayList<RexxTrip>();
        for (int i = 0; i < travelEntries.size(); i += 2) {
            ZenalEntry arrivalEntry = travelEntries.get(i);
            ZenalEntry leavingEntry = travelEntries.get(i + 1);
            trips.add(new ZenalToRexxConverter().convert(leavingEntry, arrivalEntry));
        }

        assertThat(trips.size(), is(5));
    }

}
