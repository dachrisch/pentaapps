package de.pentasys.rexx.builder;

import java.util.SortedSet;
import java.util.TreeSet;

import org.joda.time.DateTime;

import de.pentasys.rexx.entities.RexxTrip;
import de.pentasys.rexx.entities.TripCities;
import de.pentasys.zenal.builder.Project;
import de.pentasys.zenal.builder.TimespanDateTime;

public class RexxJourney {

    private final String leavingCity;
    private final String arrivalCity;
    private final DateTime journeyFrom;
    private final DateTime journeyTill;
    private final Project project;
    private final TreeSet<RexxTrip> trips = new TreeSet<RexxTrip>();

    RexxJourney(final Project project, final TripCities tripCities, final TimespanDateTime timespanDateTime) {
        this.project = project;
        leavingCity = tripCities.getLeavingCity();
        arrivalCity = tripCities.getArrivalCity();
        journeyFrom = timespanDateTime.getFrom();
        journeyTill = timespanDateTime.getTill();
    }

    public RexxJourney(final Project project, final TripCities tripCities, final TimespanDateTime timespanDateTime,
            final RexxTrip rexxTrip) {
        this.project = project;
        leavingCity = tripCities.getLeavingCity();
        arrivalCity = tripCities.getArrivalCity();
        journeyFrom = timespanDateTime.getFrom();
        journeyTill = timespanDateTime.getTill();
        if (null == rexxTrip) {
            throw new IllegalArgumentException("at least one trip needed");
        }
        trips.add(rexxTrip);
    }

    public RexxJourney withTrip(final TimespanDateTime timeSpan, final String reason) {
        trips.add(new RexxTrip(timeSpan, reason));
        return this;
    }

    public String getLeavingCity() {
        return leavingCity;
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    public DateTime getJourneyFrom() {
        return journeyFrom;
    }

    public DateTime getJourneyTill() {
        return journeyTill;
    }

    public Project getProject() {
        return project;
    }

    public SortedSet<RexxTrip> getTrips() {
        return trips;
    }

}
