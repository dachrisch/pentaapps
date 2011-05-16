package de.pentasys.rexx.entities;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class TripCities {

    private final String leavingCity;
    private final String arrivalCity;

    public String getLeavingCity() {
        return leavingCity;
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    public TripCities(final String leavingCity, final String arrivalCity) {
        this.leavingCity = leavingCity;
        this.arrivalCity = arrivalCity;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(final Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
