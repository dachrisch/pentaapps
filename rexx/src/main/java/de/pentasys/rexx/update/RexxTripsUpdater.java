package de.pentasys.rexx.update;

import java.util.SortedSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.selenium.Selenium;

import de.pentasys.rexx.entities.RexxTrip;

public class RexxTripsUpdater {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final Selenium selenium;

	private final ExpenseUpdater expenseUpdater;

	public RexxTripsUpdater(final Selenium selenium) {
		this.selenium = selenium;
		expenseUpdater = new ExpenseUpdater(selenium);
	}

	private void createFirstTrip(final RexxTrip trip) {
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

	private void createTrip(final RexxTrip trip) {
		log.debug(String.format("about to create %s", trip));
		selenium.click("//img[@title='Neuen Beleg anlegen']");
		selenium.waitForPageToLoad("30000");
		selenium.select("6", "label=Reise (Pauschalberechnung)");
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

	public void createTrips(final SortedSet<RexxTrip> trips) {
		if (trips.size() > 0) {
			RexxTrip lastTrip = trips.last();
			createFirstTrip(lastTrip);
			expenseUpdater.createExpenses(lastTrip.getCosts());
			SortedSet<RexxTrip> remainingSet = trips.headSet(lastTrip);

			for (final RexxTrip rexxTrip : remainingSet) {
				createTrip(rexxTrip);
				expenseUpdater.createExpenses(rexxTrip.getCosts());
			}
		}
	}
}
