package de.pentasys.rexx.update;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.selenium.Selenium;

import de.pentasys.rexx.entities.RexxTrip;

public class RexxTripsUpdater {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final Selenium selenium;

    public RexxTripsUpdater(final Selenium selenium) {
        this.selenium = selenium;
    }

    public void createTrips(final Set<RexxTrip> trips) {
        for (final RexxTrip rexxTrip : trips) {
            createTrip(rexxTrip);
        }
    }

    private void createTrip(final RexxTrip trip) {
        log.debug(String.format("about to create %s", trip));
        selenium.click("4");
        selenium.type("4", trip.getReason());
        selenium.type("1", trip.getLeavingCity());
        selenium.type("3", trip.getArrivalCity());
        selenium.type("7_time", trip.getFrom().toString("kk:mm"));
        selenium.type("7_date", trip.getFrom().toString("dd.MM.YYYY"));
        selenium.type("8_time", trip.getTill().toString("kk:mm"));
        selenium.type("8_date", trip.getFrom().toString("dd.MM.YYYY"));
        selenium.click("css=img[title=Speichern]");
        selenium.waitForPageToLoad("30000");
        log.info(String.format("saved %s", trip));
    }
}
