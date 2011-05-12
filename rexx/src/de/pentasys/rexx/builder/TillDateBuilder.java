package de.pentasys.rexx.builder;

import org.joda.time.DateTime;


public class TillDateBuilder {

    private final RexxJourneyBuilder rexxJourneyCreator;

    public TillDateBuilder(RexxJourneyBuilder rexxJourneyCreator) {
        this.rexxJourneyCreator = rexxJourneyCreator;
    }

    public TripCitiesBuilder till(DateTime tillDate) {
        rexxJourneyCreator.till(tillDate);
        return new TripCitiesBuilder(rexxJourneyCreator);
    }

}