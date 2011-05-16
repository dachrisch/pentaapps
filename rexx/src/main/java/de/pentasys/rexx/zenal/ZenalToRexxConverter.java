package de.pentasys.rexx.zenal;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.pentasys.rexx.entities.RexxTrip;
import de.pentasys.rexx.entities.TripCities;
import de.pentasys.zenal.ZenalEntry;
import de.pentasys.zenal.builder.Category;
import de.pentasys.zenal.builder.TimespanDateTime;

public class ZenalToRexxConverter {

    private static final Pattern CITY_PATTERN = Pattern.compile(".*?fahrt ([\\w ]+)");

    public RexxTrip convert(final ZenalEntry zenalReturnEntry, final ZenalEntry zenalArrivalEntry) {

        if (!Category.TRAVEL_START.getCategory().equals(zenalArrivalEntry.getCategory())) {
            throw new IllegalArgumentException(String.format("category of arrival entry must be [%s]: %s",
                    Category.TRAVEL_START, zenalArrivalEntry));
        }
        if (!Category.TRAVEL_END.getCategory().equals(zenalReturnEntry.getCategory())) {
            throw new IllegalArgumentException(String.format("category of return entry must be [%s]: %s",
                    Category.TRAVEL_END, zenalReturnEntry));
        }

        final Matcher arrivalMatcher = CITY_PATTERN.matcher(zenalArrivalEntry.getDescription());
        if (!arrivalMatcher.find()) {
            throw new IllegalArgumentException(String.format(
                    "description has to be in format [%s] in order to get parsed: %s", CITY_PATTERN, zenalArrivalEntry
                            .getDescription()));
        }
        final String arrivalCity = arrivalMatcher.group(1);
        final String returnCity = "";
        final TripCities tripCities = new TripCities(arrivalCity, returnCity);
        final TimespanDateTime timeSpan = new TimespanDateTime(null, null);
        final String reason = "";
        return new RexxTrip(tripCities, timeSpan, reason);
    }

}
