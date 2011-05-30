package de.pentasys.rexx.builder;

import org.joda.time.DateTime;

public class TillDateBuilder {

    private final RexxJourneyBuilder rexxJourneyCreator;

    public TillDateBuilder(final RexxJourneyBuilder rexxJourneyCreator) {
        this.rexxJourneyCreator = rexxJourneyCreator;
    }

    public TripCitiesBuilder till(final DateTime tillDate) {
        rexxJourneyCreator.till(tillDate);
        return new TripCitiesBuilder(rexxJourneyCreator);
    }

}