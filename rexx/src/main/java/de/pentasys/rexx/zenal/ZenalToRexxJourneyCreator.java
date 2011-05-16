package de.pentasys.rexx.zenal;

import static ch.lambdaj.Lambda.having;
import static ch.lambdaj.Lambda.on;
import static ch.lambdaj.Lambda.select;
import static ch.lambdaj.Lambda.selectMax;
import static ch.lambdaj.Lambda.selectMin;
import static de.pentasys.rexx.builder.RexxJourneyBuilder.doJourney;
import static de.pentasys.rexx.zenal.ZenalToRexxTripConverter.extractArrivalCity;
import static de.pentasys.rexx.zenal.ZenalToRexxTripConverter.extractLeavingCity;
import static de.pentasys.rexx.zenal.matcher.DateTimeMatcher.isAfter;
import static de.pentasys.rexx.zenal.matcher.DateTimeMatcher.isBefore;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import de.pentasys.builder.Category;
import de.pentasys.builder.TimespanDateTime;
import de.pentasys.rexx.builder.RexxJourney;
import de.pentasys.rexx.entities.RexxTrip;
import de.pentasys.rexx.entities.TripCities;
import de.pentasys.zenal.ZenalEntry;
import de.pentasys.zenal.ZenalEntryList;

public class ZenalToRexxJourneyCreator {

    private final List<ZenalEntry> zenalTripEntries;

    public ZenalToRexxJourneyCreator(final ZenalEntryList zenalEntries) {
        zenalTripEntries = filterTripCategories(zenalEntries);
    }

    @SuppressWarnings("unchecked")
    public static List<ZenalEntry> filterTripCategories(final Iterable<ZenalEntry> zenalEntries) {
        return select(zenalEntries,
                having(on(ZenalEntry.class).getCategory(), anyOf(is(Category.TRAVEL_START), is(Category.TRAVEL_END))));
    }

    @SuppressWarnings("unchecked")
    public static List<ZenalEntry> filterTimespan(final Iterable<ZenalEntry> zenalEntries,
            final TimespanDateTime timepsan) {
        return select(
                zenalEntries,
                allOf(having(on(ZenalEntry.class).getFrom(), isAfter(timepsan.getFrom())),
                        having(on(ZenalEntry.class).getTill(), isBefore(timepsan.getTill()))));
    }

    public RexxJourney createJourney(final TimespanDateTime timespan) {
        final List<ZenalEntry> tripsWithinJourney = filterTimespan(zenalTripEntries, timespan);

        if (tripsWithinJourney.size() < 2) {
            throw new IllegalArgumentException(String.format("not enought entries for [%s]: %s", timespan,
                    tripsWithinJourney));
        }

        final ZenalEntry startEntry = selectMin(tripsWithinJourney, on(ZenalEntry.class).getFrom());
        final ZenalEntry endEntry = selectMax(tripsWithinJourney, on(ZenalEntry.class).getTill());
        final DateTime startDate = startEntry.getFrom();
        final DateTime endDate = endEntry.getTill();

        final String arrivalCity = extractArrivalCity(startEntry);
        final String leavingCity = extractLeavingCity(endEntry);

        final List<RexxTrip> trips = createTrips(tripsWithinJourney);

        return doJourney(startEntry.getProject()).starting(startDate).from(leavingCity).till(endDate).to(arrivalCity)
                .withTrips(trips);
    }

    protected List<RexxTrip> createTrips(final List<ZenalEntry> tripsWithinJourney) {
        final List<RexxTrip> trips = new ArrayList<RexxTrip>();
        for (int i = 0; i < tripsWithinJourney.size(); i += 2) {
            final ZenalEntry startEntry = tripsWithinJourney.get(i);
            final ZenalEntry endEntry = tripsWithinJourney.get(i + 1);
            final String arrivalCity = extractArrivalCity(startEntry);
            final String leavingCity = extractLeavingCity(endEntry);
            trips.add(new RexxTrip(new TripCities(leavingCity, arrivalCity), new TimespanDateTime(startEntry.getFrom(),
                    endEntry.getTill()), String.format("Projekteinsatz [%s]", startEntry.getProject().getProjectId())));
        }

        return trips;
    }
}
