package de.pentasys.rexx.builder;

public class StartCityBuilder {

    private final RexxJourneyBuilder rexxJourneyCreator;

    public StartCityBuilder(RexxJourneyBuilder rexxJourneyCreator) {
        this.rexxJourneyCreator = rexxJourneyCreator;
    }

    public TillDateBuilder from(String leavingCity) {
        rexxJourneyCreator.leavingCity(leavingCity);
        return new TillDateBuilder(rexxJourneyCreator);
    }

}