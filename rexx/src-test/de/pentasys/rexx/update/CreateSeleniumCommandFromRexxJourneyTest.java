package de.pentasys.rexx.update;

import static de.pentasys.zenal.builder.DateTimeGenerator.from;
import static de.pentasys.zenal.builder.ZenalEntryCreator.datetime;
import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import org.joda.time.DateTime;
import org.junit.Test;

import com.thoughtworks.selenium.Selenium;

import de.pentasys.rexx.builder.RexxJourney;
import de.pentasys.rexx.entities.RexxTrip;
import de.pentasys.rexx.entities.TripCities;
import de.pentasys.zenal.builder.Project;
import de.pentasys.zenal.builder.TimespanDateTime;

public class CreateSeleniumCommandFromRexxJourneyTest {

    @Test
    public void createJourneyCommands() throws Exception {
        RexxJourney rexxJourney = new RexxJourney(Project.MEDIASATURN, new TripCities("a", "b"), new TimespanDateTime(
                new DateTime(2010, 1, 1, 10, 20, 0, 0), new DateTime(2010, 1, 2, 20, 10, 0, 0)), new RexxTrip(from(
                datetime(2010, 1, 1, 7, 50)).till(datetime(2010, 1, 1, 17, 50)), "projekteinsatz"));

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

        // trip
        seleniumMock.click("4");
        seleniumMock.type("4", "projekteinsatz");
        seleniumMock.type("1", "a");
        seleniumMock.type("3", "b");
        seleniumMock.type("7_time", "07:50");
        seleniumMock.type("8_time", "17:50");
        seleniumMock.click("css=img[title=Speichern]");
        replay(seleniumMock);

        new RexxUpdater(seleniumMock).createJourney(rexxJourney);

        verify(seleniumMock);
    }
}
