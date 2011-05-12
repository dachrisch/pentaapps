package de.pentasys.rexx.entities;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.joda.time.DateTime;

import de.pentasys.rexx.builder.ExpenseBuilder;
import de.pentasys.rexx.entities.expenses.Expense;
import de.pentasys.zenal.builder.TimespanDateTime;

public class RexxTrip implements Comparable<RexxTrip> {

    private final DateTime from;
    private final String reason;
    private final DateTime till;
    private final List<Expense> costs = new ArrayList<Expense>();
    private final TripCities tripCities;

    public RexxTrip(final TripCities tripCities, final TimespanDateTime timeSpan, final String reason) {
        this.tripCities = tripCities;
        from = timeSpan.getFrom();
        till = timeSpan.getTill();
        this.reason = reason;
    }

    public DateTime getFrom() {
        return from;
    }

    public DateTime getTill() {
        return till;
    }

    public String getReason() {
        return reason;
    }

    public ExpenseBuilder with() {
        return new ExpenseBuilder(costs, from, till);
    }

    public List<Expense> getCosts() {
        return costs;
    }

    @Override
    public int compareTo(final RexxTrip other) {
        return CompareToBuilder.reflectionCompare(this, other);
    }

    public String getLeavingCity() {
        return tripCities.getLeavingCity();
    }

    public String getArrivalCity() {
        return tripCities.getArrivalCity();
    }
}
