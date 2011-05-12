package de.pentasys.rexx.update;

import java.util.Set;

import com.thoughtworks.selenium.Selenium;

import de.pentasys.rexx.entities.RexxTrip;

public class RexxTripsUpdater {

    private final Selenium selenium;
    private final String leavingCity;
    private final String arrivalCity;

    public RexxTripsUpdater(final Selenium selenium, final String leavingCity, final String arrivalCity) {
        this.selenium = selenium;
        this.leavingCity = leavingCity;
        this.arrivalCity = arrivalCity;
    }

    public void createTrips(final Set<RexxTrip> trips) {
        for (final RexxTrip rexxTrip : trips) {
            createTrip(rexxTrip);
        }
    }

    private void createTrip(final RexxTrip trip) {
        selenium.click("4");
        selenium.type("4", trip.getReason());
        selenium.type("1", leavingCity);
        selenium.type("3", arrivalCity);
        selenium.type("7_time", trip.getFrom().toString("kk:mm"));
        selenium.type("7_date", trip.getFrom().toString("dd.MM.YYYY"));
        selenium.type("8_time", trip.getTill().toString("kk:mm"));
        selenium.type("8_date", trip.getFrom().toString("dd.MM.YYYY"));
        selenium.click("css=img[title=Speichern]");
        selenium.waitForPageToLoad("30000");
    }
}
