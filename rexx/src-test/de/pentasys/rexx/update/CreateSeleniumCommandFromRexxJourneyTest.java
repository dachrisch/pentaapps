package de.pentasys.rexx.update;

import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import org.joda.time.DateTime;
import org.junit.Test;

import com.thoughtworks.selenium.Selenium;

import de.pentasys.rexx.entities.RexxJourney;
import de.pentasys.rexx.entities.TripCities;
import de.pentasys.zenal.builder.Project;
import de.pentasys.zenal.builder.TimespanDateTime;

public class CreateSeleniumCommandFromRexxJourneyTest {

    @Test
    public void createJourneyCommands() throws Exception {
        RexxJourney rexxJourney = new RexxJourney(Project.MEDIASATURN, new TripCities("a", "b"), new TimespanDateTime(
                new DateTime(2010,1,1,10,20,0,0), new DateTime(2010,1,2,20,10,0,0)));

        final Selenium seleniumMock = createStrictMock(Selenium.class);
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
        replay(seleniumMock);

        new RexxUpdater(seleniumMock).createJourney(rexxJourney);

        verify(seleniumMock);
    }
}