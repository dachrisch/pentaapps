package de.pentasys.rexx.builder;

import java.util.SortedSet;
import java.util.TreeSet;

import org.joda.time.DateTime;

import de.pentasys.builder.Project;
import de.pentasys.builder.TimespanDateTime;
import de.pentasys.rexx.entities.RexxTrip;
import de.pentasys.rexx.entities.TripCities;

public class RexxJourney {

    private final DateTime journeyFrom;
    private final DateTime journeyTill;
    private final Project project;
    private final TreeSet<RexxTrip> trips = new TreeSet<RexxTrip>();
    private final TripCities tripCities;

    RexxJourney(final Project project, final TripCities tripCities, final TimespanDateTime timespanDateTime) {
        this.project = project;
        this.tripCities = tripCities;
        journeyFrom = timespanDateTime.getFrom();
        journeyTill = timespanDateTime.getTill();
    }

    public RexxJourney(final Project project, final TripCities tripCities, final TimespanDateTime timespanDateTime,
            final RexxTrip rexxTrip) {
        this.project = project;
        this.tripCities = tripCities;
        journeyFrom = timespanDateTime.getFrom();
        journeyTill = timespanDateTime.getTill();
        if (null == rexxTrip) {
            throw new IllegalArgumentException("at least one trip needed");
        }
        trips.add(rexxTrip);
    }

    public RexxJourney withTrip(final TimespanDateTime timeSpan, final String reason) {
        if(timeSpan.getFrom().isBefore(journeyFrom))
            throw new IllegalArgumentException(String.format("trip start [%s] cannot be before journey start [%s]", timeSpan.getFrom().toString(), journeyFrom.toString()));
        if(timeSpan.getTill().isAfter(journeyTill))
            throw new IllegalArgumentException(String.format("trip end [%s] cannot be after journey end [%s]", timeSpan.getFrom().toString(), journeyFrom.toString()));
        trips.add(new RexxTrip(tripCities, timeSpan, reason));
        return this;
    }

    public String getLeavingCity() {
        return tripCities.getLeavingCity();
    }

    public String getArrivalCity() {
        return tripCities.getArrivalCity();
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
