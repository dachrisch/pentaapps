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

    RexxJourney(Project project, TripCities tripCities, TimespanDateTime timespanDateTime) {
        this.project = project;
        this.leavingCity = tripCities.getLeavingCity();
        this.arrivalCity = tripCities.getArrivalCity();
        journeyFrom = timespanDateTime.getFrom();
        journeyTill = timespanDateTime.getTill();
    }

    public RexxJourney(Project project, TripCities tripCities, TimespanDateTime timespanDateTime, RexxTrip rexxTrip) {
        this.project = project;
        this.leavingCity = tripCities.getLeavingCity();
        this.arrivalCity = tripCities.getArrivalCity();
        journeyFrom = timespanDateTime.getFrom();
        journeyTill = timespanDateTime.getTill();
        addTrip(rexxTrip);
    }

    public void addTrip(RexxTrip rexxTrip) {
        trips.add(rexxTrip);
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
        return this.project;
    }

    public SortedSet<RexxTrip> getTrips() {
        return trips;
    }

}
