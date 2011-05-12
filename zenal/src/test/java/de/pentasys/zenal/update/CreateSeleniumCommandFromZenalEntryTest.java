package de.pentasys.zenal.update;

import static de.pentasys.zenal.builder.DateTimeGenerator.datetime;
import static de.pentasys.zenal.builder.DateTimeGenerator.from;
import static de.pentasys.zenal.builder.ZenalEntryCreator.within;
import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import org.junit.Test;

import com.thoughtworks.selenium.Selenium;

import de.pentasys.zenal.ZenalEntry;
import de.pentasys.zenal.builder.Project;

public class CreateSeleniumCommandFromZenalEntryTest {

    @Test
    public void testConvertZenalEntryToSeleniumCommands() throws Exception {
        final ZenalEntry entry = within(Project.MEDIASATURN).drivingTo("Anfahrt",
                from(datetime(2011, 5, 10, 7, 50)).till(9, 20)).last();

        final Selenium seleniumMock = createStrictMock(Selenium.class);
        seleniumMock.open("/ZeitnachweisEditPage.aspx");

        seleniumMock.type("txtDatum", "10.05.2011");
        seleniumMock.select("ddlProjekt", String.format("label=%s", "P080811.MED"));
        seleniumMock.type("txtZeitVon", "07:50");
        seleniumMock.type("txtZeitBis", "09:20");
        seleniumMock.type("txtBemerkung", "Anfahrt");
        seleniumMock.select("ddlKategorie", "label=Reisezeit(Beginn)");
        seleniumMock.click("btnSpeichern");
        seleniumMock.waitForPageToLoad("30000");

        expect(seleniumMock.getTable("dtgResults.1.10")).andReturn("Anfahrt");

        replay(seleniumMock);

        new ZenalUpdater(seleniumMock).update(entry);

        verify(seleniumMock);
    }
}
