package de.pentasys.rexx.builder;

public class StartCityBuilder {

    private final RexxJourneyBuilder rexxJourneyCreator;

    public StartCityBuilder(final RexxJourneyBuilder rexxJourneyCreator) {
        this.rexxJourneyCreator = rexxJourneyCreator;
    }

    public TillDateBuilder from(final String leavingCity) {
        rexxJourneyCreator.leavingCity(leavingCity);
        return new TillDateBuilder(rexxJourneyCreator);
    }

}