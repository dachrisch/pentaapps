package de.pentasys.zenal;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.joda.time.DateTime;

import de.pentasys.builder.Category;
import de.pentasys.builder.Project;
import de.pentasys.builder.TimespanDateTime;

public class ZenalEntry implements Comparable<ZenalEntry> {

    private final DateTime from;
    private final DateTime till;
    private final String description;
    private final Project project;
    private final Category category;

    public ZenalEntry(final Project project, final DateTime from, final DateTime till, final String description,
            final Category category) {
        this.project = project;
        this.from = from;
        this.till = till;
        this.description = description;
        this.category = category;
    }

    public ZenalEntry(final Project project, final TimespanDateTime timeSpan, final String description,
            final Category category) {
        this(project, timeSpan.getFrom(), timeSpan.getTill(), description, category);
    }

    public DateTime getFrom() {
        return from;
    }

    public DateTime getTill() {
        return till;
    }

    public String getDescription() {
        return description;
    }

    public String getProjectId() {
        return null == project ? null : project.getProjectId();
    }

    public String getCategory() {
        return category.getCategory();
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(final Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("ZenalEntry [from=").append(from).append(", till=").append(till).append(", description=")
                .append(description).append(", project=").append(project).append(", category=").append(category)
                .append("]");
        return builder.toString();
    }

    @Override
    public int compareTo(final ZenalEntry other) {
        return new CompareToBuilder().append(from, other.from).toComparison();
    }

}
