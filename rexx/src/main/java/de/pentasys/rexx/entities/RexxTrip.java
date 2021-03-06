package de.pentasys.rexx.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.joda.time.DateTime;

import de.pentasys.builder.TimespanDateTime;
import de.pentasys.rexx.builder.ExpenseBuilder;
import de.pentasys.rexx.entities.expenses.Expense;

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

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, Collections.singletonList("costs"));
    }

    @Override
    public boolean equals(final Object other) {
        return EqualsBuilder.reflectionEquals(this, other, Collections.singletonList("costs"));
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
