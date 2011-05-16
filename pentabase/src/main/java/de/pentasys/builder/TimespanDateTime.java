package de.pentasys.builder;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.joda.time.DateTime;

public class TimespanDateTime {

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
}
