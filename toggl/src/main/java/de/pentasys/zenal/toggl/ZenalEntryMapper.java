package de.pentasys.zenal.toggl;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import de.pentasys.builder.Category;
import de.pentasys.builder.Project;
import de.pentasys.zenal.ZenalEntry;

public class ZenalEntryMapper {
    private static final DateTimeFormatter DATETIME_PATTERN = DateTimeFormat.forPattern("MM/dd/YYYY kk:mm");
    private DateTime from;
    private DateTime till;
    private String description;
    private Project project;
    private Category category;

    public void setFrom(final String from) {
        this.from = DATETIME_PATTERN.parseDateTime(from);
    }

    public void setTill(final String till) {
        this.till = DATETIME_PATTERN.parseDateTime(till);
    }

    public void setDescription(final String description) {
        this.description = description;
        if (description.toLowerCase().contains("anfahrt")) {
            category = Category.TRAVEL_START;
        } else if (description.toLowerCase().contains("rückfahrt") || description.toLowerCase().contains("rueckfahrt")) {
            category = Category.TRAVEL_END;
        } else {
            category = Category.PROJECT;
        }
    }

    public void setProject(final String project) {
        this.project = Project.valueOf(project.replaceAll("[^\\w]+", "").toUpperCase());
    }

    public ZenalEntry toZenalEntry() {
        return new ZenalEntry(project, from, till, description, category);
    }

}
