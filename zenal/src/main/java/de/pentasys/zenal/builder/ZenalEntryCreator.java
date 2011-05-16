package de.pentasys.zenal.builder;

import de.pentasys.builder.Category;
import de.pentasys.builder.Project;
import de.pentasys.builder.TimespanDateTime;
import de.pentasys.zenal.ZenalEntry;
import de.pentasys.zenal.ZenalEntryList;

public class ZenalEntryCreator {

    public static ZenalEntryCreator within(final Project project) {
        return new ZenalEntryCreator(project, new ZenalEntryList());
    }

    private final ZenalEntryList zenalEntryList;
    private final Project project;

    public ZenalEntryCreator(final Project project, final ZenalEntryList zenalEntryList) {
        this.project = project;
        this.zenalEntryList = zenalEntryList;
    }

    public ZenalEntryCreator drivingTo(final String description, final TimespanDateTime timeSpan) {
        bookEntry(description, timeSpan, Category.TRAVEL_START);
        return this;
    }

    public ZenalEntryCreator drivingReturn(final String description, final TimespanDateTime timeSpan) {
        bookEntry(description, timeSpan, Category.TRAVEL_END);
        return this;
    }

    public ZenalEntryCreator working(final String description, final TimespanDateTime timeSpan) {
        bookEntry(description, timeSpan, Category.PROJECT);
        return this;
    }

    private void bookEntry(final String description, final TimespanDateTime timeSpan, final Category category) {
        zenalEntryList.add(new ZenalEntry(project, timeSpan, description, category));
    }

    public ZenalEntry last() {
        return zenalEntryList.last();
    }

    public ZenalEntryList list() {
        return zenalEntryList;
    }

}
