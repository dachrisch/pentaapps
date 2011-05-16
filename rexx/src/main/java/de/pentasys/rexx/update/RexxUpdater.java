package de.pentasys.rexx.update;

import static de.pentasys.builder.TimespanDateTime.thisWeek;
import static de.pentasys.selenium.setup.SeleniumSetup.createSeleniumInstance;

import java.io.Console;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.selenium.Selenium;

import de.pentasys.rexx.builder.RexxJourney;
import de.pentasys.rexx.zenal.ZenalToRexxJourneyCreator;
import de.pentasys.zenal.ZenalEntryList;
import de.pentasys.zenal.toggl.TogglRetriever;

public class RexxUpdater {

    private static Logger log = LoggerFactory.getLogger(RexxUpdater.class);

    private final Selenium selenium;
    private final RexxTripsUpdater rexxTripsUpdater;

    public RexxUpdater(final Selenium selenium) {
        this.selenium = selenium;
        rexxTripsUpdater = new RexxTripsUpdater(selenium);
    }

    public void updateJourney(final RexxJourney rexxJourney) {
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

    private void login(String username, String password) {
        selenium.open("/");
        selenium.type("txtUserName", username);
        selenium.type("txtUserPasswd", password);
        selenium.click("btnLogin");
        selenium.waitForPageToLoad("30000");
    }

    public static void main(final String[] args) {
        final Console console = System.console();

        log.info("*** Rexx Updater 1.0 ***");
        final String csvLocation = console.readLine("csv: ");

        final ZenalEntryList entriesFromCsv = new TogglRetriever().readEntriesFromCsv(csvLocation);

        RexxJourney journey = new ZenalToRexxJourneyCreator(entriesFromCsv).createJourney(thisWeek());

        log.info(String.format("found journey from [%s] till [%s] with [%d] trips", journey.getJourneyFrom(),
                journey.getJourneyTill(), journey.getTrips().size()));
        log.debug(journey.toString());

        log.info("pre-starting selenium");
        final Selenium selenium = createSeleniumInstance("https://zenal.pentasys.de");

        final RexxUpdater updater = new RexxUpdater(selenium);
        final String username = console.readLine("username: ");
        final String password = new String(console.readPassword("enter your password"));

        updater.login(username, password);
        updater.updateJourney(journey);
        selenium.stop();
    }

}
