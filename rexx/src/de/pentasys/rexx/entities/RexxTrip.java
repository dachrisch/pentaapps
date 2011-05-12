package de.pentasys.rexx.entities;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.joda.time.DateTime;

import de.pentasys.rexx.builder.CostBuilder;
import de.pentasys.rexx.entities.costs.Amount;
import de.pentasys.zenal.builder.TimespanDateTime;

public class RexxTrip implements Comparable<RexxTrip> {

    private final DateTime from;
    private final String reason;
    private final DateTime till;
    private final List<Amount> costs = new ArrayList<Amount>();

    public RexxTrip(final TimespanDateTime timeSpan, final String reason) {
        from = timeSpan.getFrom();
        till = timeSpan.getTill();
        this.reason = reason;
    }

    public DateTime getFrom() {
        return from;
    }

    public String getReason() {
        return reason;
    }

    public DateTime getTill() {
        return till;
    }

    public CostBuilder with() {
        return new CostBuilder(costs);
    }

    public List<Amount> getCosts() {
        return costs;
    }

    @Override
    public int compareTo(final RexxTrip other) {
        return CompareToBuilder.reflectionCompare(this, other);
    }
}
