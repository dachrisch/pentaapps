package de.pentasys.rexx.builder;



public class TripCitiesBuilder {

    private final RexxJourneyBuilder rexxJourneyCreator;

    public TripCitiesBuilder(RexxJourneyBuilder rexxJourneyCreator) {
        this.rexxJourneyCreator = rexxJourneyCreator;
    }

    public RexxTripBuilder to(String arrivalCity) {
        rexxJourneyCreator.arrivalCity(arrivalCity);
        return new RexxTripBuilder(rexxJourneyCreator);
    }

}
