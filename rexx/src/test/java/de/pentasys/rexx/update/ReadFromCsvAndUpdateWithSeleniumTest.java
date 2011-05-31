package de.pentasys.rexx.update;

import static de.pentasys.builder.DateTimeGenerator.date;
import static de.pentasys.builder.DateTimeGenerator.from;
import static de.pentasys.builder.TimespanDateTime.weekOf;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;

import com.thoughtworks.selenium.Selenium;

import de.pentasys.builder.Category;
import de.pentasys.builder.Project;
import de.pentasys.builder.TimespanDateTime;
import de.pentasys.rexx.builder.RexxJourney;
import de.pentasys.rexx.zenal.ZenalToRexxJourneyCreator;
import de.pentasys.zenal.ZenalEntry;
import de.pentasys.zenal.ZenalEntryList;
import de.pentasys.zenal.toggl.TogglRetriever;

public class ReadFromCsvAndUpdateWithSeleniumTest {

	@Test
	public void createJourneysForMoreThanOneWeek() throws Exception {
		final ZenalEntryList entryList = new ZenalEntryList();
		final DateTime arrivalDate = new DateTime(2011, 5, 16, 7, 50, 0, 0);
		final DateTime returnDate = new DateTime(2011, 5, 16, 17, 50, 0, 0);
		entryList.add(new ZenalEntry(Project.MEDIASATURN, from(arrivalDate).till(9, 15), "Anfahrt Ingolstadt",
				Category.TRAVEL_START));
		entryList.add(new ZenalEntry(Project.MEDIASATURN, from(returnDate).till(19, 15), "Rückfahrt MUC",
				Category.TRAVEL_END));

		entryList.add(new ZenalEntry(Project.MEDIASATURN, from(arrivalDate.plusDays(7)).till(9, 15),
				"Anfahrt Ingolstadt", Category.TRAVEL_START));
		entryList.add(new ZenalEntry(Project.MEDIASATURN, from(returnDate.plusDays(7)).till(19, 15), "Rückfahrt MUC",
				Category.TRAVEL_END));

		final List<RexxJourney> journeys = new ZenalToRexxJourneyCreator(entryList).createJourneys();

		assertThat(journeys.size(), is(2));
		assertThat(journeys.get(0).getTrips().size(), is(1));
		assertThat(journeys.get(1).getTrips().size(), is(1));
		assertThat(journeys.get(1).getJourneyFrom(), is(arrivalDate.plusDays(7)));
		assertThat(journeys.get(1).getJourneyTill(), is(arrivalDate.plusDays(7).withTime(19, 15, 0, 0)));
	}

	@Test
	public void createWeekTimespan() throws Exception {
		final DateTime now = new DateTime(2011, 5, 18, 0, 0, 0, 0);
		final DateTime firstOfWeek = weekOf(now).getFrom();
		final DateTime lastOfWeek = weekOf(now).getTill();

		assertThat(firstOfWeek, is(new DateTime(2011, 5, 16, 0, 0, 0, 0)));
		assertThat(lastOfWeek, is(new DateTime(2011, 5, 22, 23, 59, 59, 999)));
	}

	private void expectFirstTrip(final Selenium seleniumMock, final TimespanDateTime timespan) {
		seleniumMock.click("4");
		seleniumMock.type("4", "Projekteinsatz [P080811.MED]");
		seleniumMock.type("1", "MUC");
		seleniumMock.type("3", "Ingolstadt");
		seleniumMock.type("7_time", timespan.getFrom().toString("kk:mm"));
		seleniumMock.type("7_date", timespan.getFrom().toString("dd.MM.YYYY"));
		seleniumMock.type("8_time", timespan.getTill().toString("kk:mm"));
		seleniumMock.type("8_date", timespan.getTill().toString("dd.MM.YYYY"));
		seleniumMock.click("css=img[title=Speichern]");
		seleniumMock.waitForPageToLoad("30000");
	}

	private void expectJourney(final Selenium seleniumMock) {
		seleniumMock.open("/rexx-hr/pentasys/expenses/portal/index.php");
		seleniumMock.waitForPageToLoad("30000");
		seleniumMock.click("//a[4]/font[contains(text(), 'Spesen')]");
		seleniumMock.waitForPageToLoad("30000");
		seleniumMock.click("css=img[title=Inlandsreise]");
		seleniumMock.waitForPageToLoad("30000");
		seleniumMock.select("60", "label=P080811.MED");
		seleniumMock.type("2_time", "07:50");
		seleniumMock.type("2_date", "09.05.2011");
		seleniumMock.type("5_time", "17:15");
		seleniumMock.type("5_date", "13.05.2011");
		seleniumMock.click("css=img[title=Speichern]");
		seleniumMock.waitForPageToLoad("30000");
	}

	private void expectTrip(final Selenium seleniumMock, final TimespanDateTime timespan) {
		seleniumMock.click("//img[@title='Neuen Beleg anlegen']");
		seleniumMock.waitForPageToLoad("30000");
		seleniumMock.select("6", "label=Reise (Pauschalberechnung)");
		seleniumMock.type("4", "Projekteinsatz [P080811.MED]");
		seleniumMock.type("1", "MUC");
		seleniumMock.type("3", "Ingolstadt");
		seleniumMock.type("7_time", timespan.getFrom().toString("kk:mm"));
		seleniumMock.type("7_date", timespan.getFrom().toString("dd.MM.YYYY"));
		seleniumMock.type("8_time", timespan.getTill().toString("kk:mm"));
		seleniumMock.type("8_date", timespan.getTill().toString("dd.MM.YYYY"));
		seleniumMock.click("css=img[title=Speichern]");
		seleniumMock.waitForPageToLoad("30000");
	}

	@Test
	public void readFromCsvConvertAndCreateSeleniumCommands() throws Exception {
		final ZenalEntryList zenalEntries = new TogglRetriever()
				.readEntriesFromCsv("src/test/resources/whole_week_report.csv");

		final RexxJourney rexxJourney = new ZenalToRexxJourneyCreator(zenalEntries).createJourney(weekOf(date(2011, 5,
				12)));

		assertThat(rexxJourney.getTrips().size(), is(5));

		final Selenium seleniumMock = createMock(Selenium.class);
		expectJourney(seleniumMock);
		expectFirstTrip(seleniumMock, from(new DateTime(2011, 5, 9, 7, 50, 0, 0)).till(19, 10));
		expectTrip(seleniumMock, from(new DateTime(2011, 5, 10, 7, 50, 0, 0)).till(20, 15));
		expectTrip(seleniumMock, from(new DateTime(2011, 5, 11, 7, 50, 0, 0)).till(20, 15));
		expectTrip(seleniumMock, from(new DateTime(2011, 5, 12, 7, 50, 0, 0)).till(20, 15));
		expectTrip(seleniumMock, from(new DateTime(2011, 5, 13, 7, 50, 0, 0)).till(17, 15));

		replay(seleniumMock);
		new RexxUpdater(seleniumMock).updateJourney(rexxJourney);
		verify(seleniumMock);
	}

}
