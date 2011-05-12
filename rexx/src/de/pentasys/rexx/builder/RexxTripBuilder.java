package de.pentasys.rexx.builder;

import de.pentasys.zenal.builder.TimespanDateTime;

public class RexxTripBuilder {

    private final RexxJourneyBuilder rexxJourneyBuilder;

    public RexxTripBuilder(RexxJourneyBuilder rexxJourneyBuilder) {
        this.rexxJourneyBuilder = rexxJourneyBuilder;
    }

    public RexxJourney withTrip(TimespanDateTime timeSpan, String reason) {
        return rexxJourneyBuilder.withTrip(timeSpan, reason);
    }

}
