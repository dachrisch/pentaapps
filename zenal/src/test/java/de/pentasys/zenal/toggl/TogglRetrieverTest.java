package de.pentasys.zenal.toggl;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;

import de.pentasys.zenal.ZenalEntry;
import de.pentasys.zenal.builder.Category;

public class TogglRetrieverTest {

    @Test
    public void convertCsvToZenalEntries() throws Exception {
        final List<ZenalEntry> zenalEntries = new TogglRetriever()
                .readEntriesFromCsv("src/test/java/de/pentasys/zenal/toggl/time_entries_20110510_164432.csv");

        assertThat(zenalEntries.size(), is(4));
        final ZenalEntry zenalEntry = zenalEntries.get(0);
        assertThat(zenalEntry.getDescription(), is("Anfahrt Ingolstadt"));
        assertThat(zenalEntry.getProjectId(), is("P080811.MED"));
        assertThat(zenalEntry.getFrom(), is(new DateTime(2011, 5, 10, 7, 50, 0, 0)));
        assertThat(zenalEntry.getTill(), is(new DateTime(2011, 5, 10, 9, 20, 0, 0)));
        assertThat(zenalEntry.getCategory(), is(Category.TRAVEL_START.getCategory()));

        assertThat(zenalEntries.get(1).getDescription(), is("Rückfahrt"));
    }

}
