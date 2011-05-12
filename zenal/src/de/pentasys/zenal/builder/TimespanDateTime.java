package de.pentasys.zenal.builder;

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

}
