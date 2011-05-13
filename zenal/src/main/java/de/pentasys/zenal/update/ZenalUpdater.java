package de.pentasys.zenal.update;

import static de.pentasys.zenal.selenium.setup.SeleniumSetup.createSeleniumInstance;

import java.io.Console;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.SeleniumException;

import de.pentasys.zenal.ZenalEntry;
import de.pentasys.zenal.selenium.SeleniumBase;
import de.pentasys.zenal.toggl.TogglRetriever;

public class ZenalUpdater extends SeleniumBase {

    private static Logger log = LoggerFactory.getLogger(ZenalUpdater.class);

    public ZenalUpdater(final Selenium selenium) {
        super(selenium);
    }

    public void update(final ZenalEntry entry) {
        log.info(String.format("about to create %s", entry));
        selenium.open("/ZeitnachweisEditPage.aspx");
        selenium.type("txtDatum", entry.getFrom().toString("dd.MM.YYYY"));
        selenium.select("ddlProjekt", String.format("label=%s", entry.getProjectId()));

        selenium.type("txtZeitVon", entry.getFrom().toString("kk:mm"));
        selenium.type("txtZeitBis", entry.getTill().toString("kk:mm"));
        selenium.type("txtBemerkung", entry.getDescription());
        selenium.select("ddlKategorie", String.format("label=%s", entry.getCategory()));
        selenium.click("btnSpeichern");
        selenium.waitForPageToLoad("30000");
        try {
            if (!selenium.getTable("dtgResults.1.10").equals(entry.getDescription())) {
                log.info(String.format("[FAILED] something not correct while saving %s", entry));
            } else {
                log.info(String.format("[OK] saved %s", entry));
            }
        } catch (final SeleniumException e) {
            log.error(String.format("[ERROR] error while saving %s", entry));
        }
    }

    public void login(final String username, final String password) {
        log.info(String.format("about to login [%s]", username));
        selenium.open("/");
        selenium.type("txtUserName", username);
        selenium.type("txtUserPasswd", password);
        selenium.click("btnLogin");
        selenium.waitForPageToLoad("30000");
        selenium.open("/ZenalTop.aspx");
        selenium.waitForPageToLoad("30000");
        log.info(String.format("[OK] logged in [%s]", username));
    }

    public static void main(final String[] args) {
        final Console console = System.console();

        log.info("*** Zenal Updater 1.0 ***");
        final String csvLocation = console.readLine("csv: ");

        final List<ZenalEntry> entriesFromCsv = new TogglRetriever().readEntriesFromCsv(csvLocation);

        log.info(String.format("read %d entries from [%s]...now updating", entriesFromCsv.size(), csvLocation));
        log.debug(entriesFromCsv.toString());

        log.info("pre-starting selenium");
        final Selenium selenium = createSeleniumInstance("https://zenal.pentasys.de");

        final ZenalUpdater updater = new ZenalUpdater(selenium);
        final String username = console.readLine("username: ");
        final String password = new String(console.readPassword("enter your password"));

        updater.login(username, password);
        for (final ZenalEntry zenalEntry : entriesFromCsv) {
            updater.update(zenalEntry);
        }
        selenium.stop();
    }
}
