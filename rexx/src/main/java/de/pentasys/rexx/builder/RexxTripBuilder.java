package de.pentasys.rexx.builder;

import de.pentasys.builder.TimespanDateTime;

public class RexxTripBuilder {

    private final RexxJourneyBuilder rexxJourneyBuilder;

    public RexxTripBuilder(final RexxJourneyBuilder rexxJourneyBuilder) {
        this.rexxJourneyBuilder = rexxJourneyBuilder;
    }

    public RexxJourney withTrip(final TimespanDateTime timeSpan, final String reason) {
        return rexxJourneyBuilder.withTrip(timeSpan, reason);
    }

}
