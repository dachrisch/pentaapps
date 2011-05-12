package de.pentasys.rexx.update;

import java.util.SortedSet;

import com.thoughtworks.selenium.Selenium;

import de.pentasys.rexx.builder.RexxJourney;
import de.pentasys.rexx.entities.RexxTrip;

public class RexxUpdater {

    private final Selenium selenium;

    public RexxUpdater(Selenium selenium) {
        this.selenium = selenium;
    }

    public void createJourney(RexxJourney rexxJourney) {
        gotoSpesenPage();
        createInlandJourney(rexxJourney);
        SortedSet<RexxTrip> trips = rexxJourney.getTrips();
        for (RexxTrip rexxTrip : trips) {
            createTrip(rexxJourney, rexxTrip);
        }
    }

    private void createTrip(RexxJourney rexxJourney, RexxTrip trip) {
        selenium.click("4");
        selenium.type("4", trip.getReason());
        selenium.type("1", rexxJourney.getLeavingCity());
        selenium.type("3", rexxJourney.getArrivalCity());
        selenium.type("7_time", trip.getFrom().toString("kk:mm"));
        selenium.type("7_date", trip.getFrom().toString("dd.MM.YYYY"));
        selenium.type("8_time", trip.getTill().toString("kk:mm"));
        selenium.type("8_date", trip.getFrom().toString("dd.MM.YYYY"));
        selenium.click("css=img[title=Speichern]");
        selenium.waitForPageToLoad("30000");
    }

    private void createInlandJourney(RexxJourney rexxJourney) {
        selenium.click("css=img[title=Inlandsreise]");
        selenium.waitForPageToLoad("30000");

        selenium.select("60", String.format("label=%s", rexxJourney.getProject().getProjectId()));
        selenium.type("2_date", rexxJourney.getJourneyFrom().toString("dd.MM.YYYY"));
        selenium.type("2_time", rexxJourney.getJourneyFrom().toString("kk:mm"));
        selenium.type("5_date", rexxJourney.getJourneyTill().toString("dd.MM.YYYY"));
        selenium.type("5_time", rexxJourney.getJourneyTill().toString("kk:mm"));
        selenium.click("css=img[title=Speichern]");
        selenium.waitForPageToLoad("30000");
    }

    private void gotoSpesenPage() {
        selenium.open("/");
        selenium.click("//a[4]/font[contains(text(), 'Spesen')]");
        selenium.waitForPageToLoad("30000");
    }

}
