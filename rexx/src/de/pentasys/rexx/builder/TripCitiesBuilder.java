package de.pentasys.rexx.builder;

public class TripCitiesBuilder {

    private final RexxJourneyBuilder rexxJourneyCreator;

    public TripCitiesBuilder(final RexxJourneyBuilder rexxJourneyCreator) {
        this.rexxJourneyCreator = rexxJourneyCreator;
    }

    public RexxTripBuilder to(final String arrivalCity) {
        rexxJourneyCreator.arrivalCity(arrivalCity);
        return new RexxTripBuilder(rexxJourneyCreator);
    }

}
