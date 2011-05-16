package de.pentasys.rexx.zenal;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.pentasys.builder.Category;
import de.pentasys.builder.TimespanDateTime;
import de.pentasys.rexx.entities.RexxTrip;
import de.pentasys.rexx.entities.TripCities;
import de.pentasys.zenal.ZenalEntry;

public class ZenalToRexxTripConverter {

    private static final Pattern CITY_PATTERN = Pattern.compile(".*?fahrt ([\\w ]+)");

    public RexxTrip convert(final ZenalEntry zenalLeavingEntry, final ZenalEntry zenalArrivalEntry) {

        final String arrivalCity = extractArrivalCity(zenalArrivalEntry);
        final String leavingCity = extractLeavingCity(zenalLeavingEntry);

        final TripCities tripCities = new TripCities(leavingCity, arrivalCity);
        final TimespanDateTime timeSpan = new TimespanDateTime(null, null);
        final String reason = "";
        return new RexxTrip(tripCities, timeSpan, reason);
    }

    public static String extractArrivalCity(final ZenalEntry zenalEntry) {
        return extractCityInfo(zenalEntry, Category.TRAVEL_START);
    }

    public static String extractLeavingCity(final ZenalEntry zenalEntry) {
        return extractCityInfo(zenalEntry, Category.TRAVEL_END);
    }

    public static String extractCityInfo(final ZenalEntry zenalEntry, final Category category) {
        if (!category.equals(zenalEntry.getCategory())) {
            throw new IllegalArgumentException(String.format("entry [%s] not of type [%s]", zenalEntry, category));
        }
        final Matcher matcher = CITY_PATTERN.matcher(zenalEntry.getDescription());
        if (!matcher.find()) {
            throw new IllegalArgumentException(String.format(
                    "description has to be in format [%s] in order to get parsed: %s", CITY_PATTERN,
                    zenalEntry.getDescription()));
        }
        final String arrivalCity = matcher.group(1);
        return arrivalCity;
    }

}
