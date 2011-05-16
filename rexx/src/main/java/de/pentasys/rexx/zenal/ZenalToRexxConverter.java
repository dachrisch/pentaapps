package de.pentasys.rexx.zenal;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.pentasys.builder.Category;
import de.pentasys.builder.TimespanDateTime;
import de.pentasys.rexx.entities.RexxTrip;
import de.pentasys.rexx.entities.TripCities;
import de.pentasys.zenal.ZenalEntry;

public class ZenalToRexxConverter {

    private static final Pattern CITY_PATTERN = Pattern.compile(".*?fahrt ([\\w ]+)");

    public RexxTrip convert(final ZenalEntry zenalLeavingEntry, final ZenalEntry zenalArrivalEntry) {

        final String arrivalCity = extractCityInfo(zenalArrivalEntry, Category.TRAVEL_START);
        final String leavingCity = extractCityInfo(zenalLeavingEntry, Category.TRAVEL_END);
        
        final TripCities tripCities = new TripCities(leavingCity, arrivalCity);
        final TimespanDateTime timeSpan = new TimespanDateTime(null, null);
        final String reason = "";
        return new RexxTrip(tripCities, timeSpan, reason);
    }

    private String extractCityInfo(final ZenalEntry zenalArrivalEntry, Category category) {
        if (!category.getCategory().equals(zenalArrivalEntry.getCategory())) {
            throw new IllegalArgumentException(
                    String.format("entry [%s] not of type [%s]", zenalArrivalEntry, category));
        }
        Matcher matcher = CITY_PATTERN.matcher(zenalArrivalEntry.getDescription());
        if (!matcher.find()) {
            throw new IllegalArgumentException(String.format(
                    "description has to be in format [%s] in order to get parsed: %s", CITY_PATTERN,
                    zenalArrivalEntry.getDescription()));
        }
        final String arrivalCity = matcher.group(1);
        return arrivalCity;
    }

}
