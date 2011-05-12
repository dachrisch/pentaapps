package de.pentasys.rexx.builder;

import org.joda.time.DateTime;

import de.pentasys.rexx.entities.TripCities;
import de.pentasys.zenal.builder.Project;
import de.pentasys.zenal.builder.TimespanDateTime;

public class RexxJourneyBuilder {

    public static RexxJourneyBuilder doJourney(final Project project) {
        return new RexxJourneyBuilder(project);
    }

    private String arrivalCity;
    private String leavingCity;
    private DateTime tillDate;
    private DateTime startDate;
    private final Project project;
    private RexxJourney rexxJourney;

    public RexxJourneyBuilder(final Project project) {
        this.project = project;
    }

    public StartCityBuilder starting(final DateTime startDate) {
        this.startDate = startDate;
        return new StartCityBuilder(this);
    }

    void till(final DateTime tillDate) {
        this.tillDate = tillDate;

    }

    void arrivalCity(final String arrivalCity) {
        this.arrivalCity = arrivalCity;

    }

    void leavingCity(final String leavingCity) {
        this.leavingCity = leavingCity;

    }

    public RexxJourney withTrip(final TimespanDateTime timeSpan, final String reason) {
        if (null == rexxJourney) {
            rexxJourney = new RexxJourney(project, new TripCities(leavingCity, arrivalCity), new TimespanDateTime(
                    startDate, tillDate));
        }
        rexxJourney.withTrip(timeSpan, reason);

        return rexxJourney;
    }

}
