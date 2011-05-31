package de.pentasys.rexx.update;

import static de.pentasys.rexx.builder.ExpenseBuilder.taxi;
import static de.pentasys.rexx.builder.ExpenseBuilder.train;
import static de.pentasys.selenium.setup.SeleniumSetup.createSeleniumInstance;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.selenium.Selenium;

import de.pentasys.rexx.builder.RexxJourney;
import de.pentasys.rexx.entities.RexxTrip;
import de.pentasys.rexx.entities.expenses.Payment;
import de.pentasys.rexx.zenal.ZenalToRexxJourneyCreator;
import de.pentasys.zenal.ZenalEntryList;
import de.pentasys.zenal.toggl.TogglRetriever;

public class LiveUpdateTest {
	private static Logger log = LoggerFactory.getLogger(RexxUpdater.class);

	@Test
	public void testname() throws Exception {

		final String csvLocation = "C:/Users/daehnchr/Downloads/time_entries_20110530_113946.csv";

		final ZenalEntryList entriesFromCsv = new TogglRetriever().readEntriesFromCsv(csvLocation);

		final List<RexxJourney> journeys = new ZenalToRexxJourneyCreator(entriesFromCsv).createJourneys();

		log.info(String.format("found [%d] journeys...", journeys.size()));
		log.debug(journeys.toString());

		log.info("pre-starting selenium");
		final Selenium selenium = createSeleniumInstance(RexxUpdater.REXX_SERVER_COM);

		final RexxUpdater updater = new RexxUpdater(selenium);
		for (final RexxJourney journey : journeys) {
			for (RexxTrip trip : journey.getTrips()) {
				log.info("adding taxi and train costs to trip: " + trip);

				trip.with().inboundCosts(taxi(14.0, Payment.CASH)).inboundCosts(train(12.4, Payment.CREDIT));
				trip.with().outboundCosts(taxi(14.0, Payment.CASH)).outboundCosts(train(12.4, Payment.CREDIT));
			}
		}
		for (final RexxJourney journey : journeys) {
			log.info(String.format("updating journey [%s]", journey));
			updater.updateJourney(journey);
		}
		selenium.stop();
	}
}
