package de.pentasys.rexx.update;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.selenium.Selenium;

import de.pentasys.rexx.builder.RexxJourney;

public class RexxUpdater {

    private Logger log = LoggerFactory.getLogger(getClass());

    private final Selenium selenium;
    private final RexxTripsUpdater rexxTripsUpdater;

    public RexxUpdater(final Selenium selenium) {
        this.selenium = selenium;
        rexxTripsUpdater = new RexxTripsUpdater(selenium);
    }

    public void createJourney(final RexxJourney rexxJourney) {
        gotoSpesenPage();
        createInlandJourney(rexxJourney);
        rexxTripsUpdater.createTrips(rexxJourney.getTrips());
    }

    private void createInlandJourney(final RexxJourney rexxJourney) {
        log.debug(String.format("about to create %s", rexxJourney));
        selenium.click("css=img[title=Inlandsreise]");
        selenium.waitForPageToLoad("30000");

        selenium.select("60", String.format("label=%s", rexxJourney.getProject().getProjectId()));
        selenium.type("2_date", rexxJourney.getJourneyFrom().toString("dd.MM.YYYY"));
        selenium.type("2_time", rexxJourney.getJourneyFrom().toString("kk:mm"));
        selenium.type("5_date", rexxJourney.getJourneyTill().toString("dd.MM.YYYY"));
        selenium.type("5_time", rexxJourney.getJourneyTill().toString("kk:mm"));
        selenium.click("css=img[title=Speichern]");
        selenium.waitForPageToLoad("30000");
        log.debug(String.format("saved %s", rexxJourney));
    }

    private void gotoSpesenPage() {
        selenium.open("/");
        selenium.click("//a[4]/font[contains(text(), 'Spesen')]");
        selenium.waitForPageToLoad("30000");
    }

}
