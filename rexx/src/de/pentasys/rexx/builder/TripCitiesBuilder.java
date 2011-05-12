package de.pentasys.rexx.builder;

import de.pentasys.rexx.entities.RexxJourney;


public class TripCitiesBuilder {


    private final RexxJourneyBuilder rexxJourneyCreator;

    public TripCitiesBuilder(RexxJourneyBuilder rexxJourneyCreator) {
        this.rexxJourneyCreator = rexxJourneyCreator;
    }

    public RexxJourney to(String arrivalCity) {
        rexxJourneyCreator.arrivalCity(arrivalCity);
        return rexxJourneyCreator.createJourney();
    }

}
