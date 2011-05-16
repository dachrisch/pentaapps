package de.pentasys.rexx.update;

import static de.pentasys.builder.DateTimeGenerator.datetime;
import static de.pentasys.builder.DateTimeGenerator.from;
import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import java.util.Collections;

import org.joda.time.DateTime;
import org.junit.Test;

import com.thoughtworks.selenium.Selenium;

import de.pentasys.builder.Project;
import de.pentasys.builder.TimespanDateTime;
import de.pentasys.rexx.builder.RexxJourney;
import de.pentasys.rexx.entities.RexxTrip;
import de.pentasys.rexx.entities.TripCities;
import de.pentasys.rexx.entities.expenses.Expense;
import de.pentasys.rexx.entities.expenses.Payment;
import de.pentasys.rexx.entities.expenses.TaxiExpense;
import de.pentasys.rexx.entities.expenses.TrainExpense;

public class CreateSeleniumCommandFromRexxJourneyTest {
    @Test
    public void createTaxiExpenseCommands() throws Exception {

        final TaxiExpense amount = new TaxiExpense(12.99, Payment.CASH);
        final DateTime issueDate = new DateTime();
        amount.setIssueDate(issueDate);
        final Selenium seleniumMock = createStrictMock(Selenium.class);

        seleniumMock.click("css=img[title=Neuen Beleg anlegen]");
        seleniumMock.waitForPageToLoad("30000");
        seleniumMock.select("6", "label=Taxi bis 50 km");
        seleniumMock.waitForPageToLoad("30000");
        seleniumMock.type("34_date", issueDate.toString("dd.MM.YYYY"));
        seleniumMock.type("34_time", issueDate.toString("kk:mm"));
        seleniumMock.type("9", "12.99");
        seleniumMock.select("18", "label=Bar");

        seleniumMock.click("css=img[title=Speichern]");
        seleniumMock.waitForPageToLoad("30000");
        replay(seleniumMock);

        new ExpenseUpdater(seleniumMock).createExpenses(Collections.singletonList((Expense) amount));

        verify(seleniumMock);
    }

    @Test
    public void createTrainExpenseCommands() throws Exception {

        final TrainExpense amount = new TrainExpense(14.99, Payment.CREDIT);
        final DateTime issueDate = new DateTime();
        amount.setIssueDate(issueDate);
        final Selenium seleniumMock = createStrictMock(Selenium.class);

        seleniumMock.click("css=img[title=Neuen Beleg anlegen]");
        seleniumMock.waitForPageToLoad("30000");
        seleniumMock.select("6", "label=Öffentliche Verkehrsmittel über 50 KM");
        seleniumMock.waitForPageToLoad("30000");
        seleniumMock.type("34_date", issueDate.toString("dd.MM.YYYY"));
        seleniumMock.type("34_time", issueDate.toString("kk:mm"));
        seleniumMock.type("9", "14.99");
        seleniumMock.select("18", "label=Firmenkreditkarte");

        seleniumMock.click("css=img[title=Speichern]");
        seleniumMock.waitForPageToLoad("30000");
        replay(seleniumMock);

        new ExpenseUpdater(seleniumMock).createExpenses(Collections.singletonList((Expense) amount));

        verify(seleniumMock);
    }

    @Test
    public void createTripCommands() throws Exception {
        final RexxTrip rexxTrip = new RexxTrip(new TripCities("b", "c"), from(datetime(2010, 1, 2, 10, 20)).till(
                datetime(2010, 1, 2, 20, 10)), "projekteinsatz");
        final Selenium seleniumMock = createStrictMock(Selenium.class);
        // trip
        seleniumMock.click("4");
        seleniumMock.type("4", "projekteinsatz");
        seleniumMock.type("1", "b");
        seleniumMock.type("3", "c");
        seleniumMock.type("7_time", "10:20");
        seleniumMock.type("7_date", "02.01.2010");
        seleniumMock.type("8_time", "20:10");
        seleniumMock.type("8_date", "02.01.2010");
        seleniumMock.click("css=img[title=Speichern]");
        seleniumMock.waitForPageToLoad("30000");

        replay(seleniumMock);

        new RexxTripsUpdater(seleniumMock).createTrips(Collections.singleton(rexxTrip));

        verify(seleniumMock);
    }

    @Test
    public void createJourneyCommands() throws Exception {
        final RexxJourney rexxJourney = new RexxJourney(Project.MEDIASATURN, new TripCities("a", "b"),
                new TimespanDateTime(new DateTime(2010, 1, 1, 10, 20, 0, 0), new DateTime(2010, 1, 2, 20, 10, 0, 0)),
                new RexxTrip(new TripCities(null, null), new TimespanDateTime(null, null), null));
        rexxJourney.getTrips().clear();
        final Selenium seleniumMock = createStrictMock(Selenium.class);
        // journey
        seleniumMock.open("/");
        seleniumMock.click("//a[4]/font[contains(text(), 'Spesen')]");
        seleniumMock.waitForPageToLoad("30000");
        seleniumMock.click("css=img[title=Inlandsreise]");
        seleniumMock.waitForPageToLoad("30000");
        seleniumMock.select("60", "label=P080811.MED");
        seleniumMock.type("2_date", "01.01.2010");
        seleniumMock.type("2_time", "10:20");
        seleniumMock.type("5_date", "02.01.2010");
        seleniumMock.type("5_time", "20:10");
        seleniumMock.click("css=img[title=Speichern]");
        seleniumMock.waitForPageToLoad("30000");

        replay(seleniumMock);

        new RexxUpdater(seleniumMock).createJourney(rexxJourney);

        verify(seleniumMock);
    }
}
