package de.pentasys.rexx.update;

import static de.pentasys.selenium.setup.SeleniumSetup.createSeleniumInstance;

import java.io.Console;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.selenium.Selenium;

import de.pentasys.rexx.builder.RexxJourney;
import de.pentasys.rexx.zenal.ZenalToRexxJourneyCreator;
import de.pentasys.selenium.check.SeleniumBase;
import de.pentasys.zenal.ZenalEntryList;
import de.pentasys.zenal.toggl.TogglRetriever;

public class RexxUpdater extends SeleniumBase {

	public static final String REXX_SERVER_COM = "https://098.rexx-server.com/";
	private static Logger log = LoggerFactory.getLogger(RexxUpdater.class);

	public static void main(final String[] args) {
		final Console console = System.console();

		log.info("*** Rexx Updater 1.0 ***");
		final String csvLocation = console.readLine("csv: ");

		final ZenalEntryList entriesFromCsv = new TogglRetriever().readEntriesFromCsv(csvLocation);

		final List<RexxJourney> journeys = new ZenalToRexxJourneyCreator(entriesFromCsv).createJourneys();

		log.info(String.format("found [%d] journeys...", journeys.size()));
		log.debug(journeys.toString());

		log.info("pre-starting selenium");
		final Selenium selenium = createSeleniumInstance(REXX_SERVER_COM);

		final RexxUpdater updater = new RexxUpdater(selenium);
		for (final RexxJourney journey : journeys) {
			log.info(String.format("updating journey [%s]", journey));
			updater.updateJourney(journey);
		}
		selenium.stop();
	}

	private final RexxTripsUpdater rexxTripsUpdater;

	public RexxUpdater(final Selenium selenium) {
		super(selenium);
		rexxTripsUpdater = new RexxTripsUpdater(selenium);
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
		selenium.open("/rexx-hr/pentasys/expenses/portal/index.php");
		selenium.waitForPageToLoad("30000");
		selenium.click("//a[4]/font[contains(text(), 'Spesen')]");
		selenium.waitForPageToLoad("30000");
	}

	public void updateJourney(final RexxJourney rexxJourney) {
		gotoSpesenPage();
		createInlandJourney(rexxJourney);
		rexxTripsUpdater.createTrips(rexxJourney.getTrips());
	}

}
