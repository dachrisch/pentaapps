package de.pentasys.rexx.entities;

import org.joda.time.DateTime;

import de.pentasys.zenal.builder.TimespanDateTime;

public class RexxTrip {

    private final DateTime from;
    private final String reason;
    private final DateTime till;

    public RexxTrip(TimespanDateTime timeSpan, String reason) {
        from = timeSpan.getFrom();
        till = timeSpan.getTill();
        this.reason = reason;
    }

    public DateTime getFrom() {
        return this.from;
    }

    public String getReason() {
        return reason;
    }

    public DateTime getTill() {
        return till;
    }
}
