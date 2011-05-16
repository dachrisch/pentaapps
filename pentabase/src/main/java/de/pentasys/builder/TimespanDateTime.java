package de.pentasys.builder;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.joda.time.DateTime;

public class TimespanDateTime implements Comparable<TimespanDateTime> {

    public static TimespanDateTime thisWeek() {
        return weekOf(new DateTime());
    }

    public static TimespanDateTime weekOf(final DateTime date) {
        final DateTime firstOfWeek = date.minusDays(date.getDayOfWeek() - 1).withTime(0, 0, 0, 0);
        final DateTime lastOfWeek = date.plusDays(7 - date.getDayOfWeek()).withTime(23, 59, 59, 999);
        return new TimespanDateTime(firstOfWeek, lastOfWeek);
    }

    private final DateTime from;
    private final DateTime till;

    public TimespanDateTime(final DateTime from, final DateTime till) {
        this.from = from;
        this.till = till;
    }

    public DateTime getFrom() {
        return from;
    }

    public DateTime getTill() {
        return till;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int compareTo(TimespanDateTime other) {
        return CompareToBuilder.reflectionCompare(this, other);
    }
}
