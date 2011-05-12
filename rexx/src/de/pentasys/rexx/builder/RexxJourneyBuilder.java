package de.pentasys.rexx.builder;

import org.joda.time.DateTime;

import de.pentasys.rexx.entities.RexxTrip;
import de.pentasys.rexx.entities.TripCities;
import de.pentasys.zenal.builder.Project;
import de.pentasys.zenal.builder.TimespanDateTime;

public class RexxJourneyBuilder {

    public static RexxJourneyBuilder doJourney(Project project) {
        return new RexxJourneyBuilder(project);
    }

    private String arrivalCity;
    private String leavingCity;
    private DateTime tillDate;
    private DateTime startDate;
    private final Project project;
    private RexxJourney rexxJourney;

    public RexxJourneyBuilder(Project project) {
        this.project = project;
    }

    public StartCityBuilder starting(DateTime startDate) {
        this.startDate = startDate;
        return new StartCityBuilder(this);
    }

    void till(DateTime tillDate) {
        this.tillDate = tillDate;

    }

    void arrivalCity(String arrivalCity) {
        this.arrivalCity = arrivalCity;

    }

    void leavingCity(String leavingCity) {
        this.leavingCity = leavingCity;

    }

    public RexxJourney withTrip(RexxTrip rexxTrip) {
        if (null == rexxJourney) {
            rexxJourney = new RexxJourney(project, new TripCities(leavingCity, arrivalCity), new TimespanDateTime(
                    startDate, tillDate));
        }
        rexxJourney.addTrip(rexxTrip);

        return rexxJourney;
    }

}
