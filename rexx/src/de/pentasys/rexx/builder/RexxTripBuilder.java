package de.pentasys.rexx.builder;

import de.pentasys.rexx.entities.RexxTrip;
import de.pentasys.zenal.builder.TimespanDateTime;

public class RexxTripBuilder {

    private final RexxJourneyBuilder rexxJourneyBuilder;

    public RexxTripBuilder(RexxJourneyBuilder rexxJourneyBuilder) {
        this.rexxJourneyBuilder = rexxJourneyBuilder;
    }

    public RexxJourney doTrip(TimespanDateTime timeSpan, String reason) {
        return rexxJourneyBuilder.withTrip(new RexxTrip(timeSpan, reason));
    }

}
