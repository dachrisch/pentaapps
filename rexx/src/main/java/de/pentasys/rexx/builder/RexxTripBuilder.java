package de.pentasys.rexx.builder;

import java.util.List;

import de.pentasys.builder.TimespanDateTime;
import de.pentasys.rexx.entities.RexxTrip;

public class RexxTripBuilder {

    private final RexxJourneyBuilder rexxJourneyBuilder;

    public RexxTripBuilder(final RexxJourneyBuilder rexxJourneyBuilder) {
        this.rexxJourneyBuilder = rexxJourneyBuilder;
    }

    public RexxJourney withTrip(final TimespanDateTime timeSpan, final String reason) {
        return rexxJourneyBuilder.withTrip(timeSpan, reason);
    }

    public RexxJourney withTrips(final List<RexxTrip> trips) {
        return rexxJourneyBuilder.withTrips(trips);
    }

}
