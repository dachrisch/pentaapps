package de.pentasys.rexx.entities;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import de.pentasys.rexx.builder.CostBuilder;
import de.pentasys.rexx.entities.costs.Amount;
import de.pentasys.zenal.builder.TimespanDateTime;

public class RexxTrip {

    private final DateTime from;
    private final String reason;
    private final DateTime till;
    private List<Amount> costs = new ArrayList<Amount>();

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

    public CostBuilder with() {
        return new CostBuilder(costs);
    }

    public List<Amount> getCosts() {
        return costs;
    }
}
