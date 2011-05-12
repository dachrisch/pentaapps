package de.pentasys.zenal.selenium;

import static de.pentasys.zenal.selenium.ElementOnPageChecker.editable;
import static de.pentasys.zenal.selenium.ElementOnPageChecker.not;
import static de.pentasys.zenal.selenium.ElementOnPageChecker.present;
import static de.pentasys.zenal.selenium.ElementOnPageChecker.title;
import static de.pentasys.zenal.selenium.ElementOnPageChecker.visible;
import static de.pentasys.zenal.selenium.ElementOnPageChecker.xpathCount;

import org.hamcrest.Matchers;

import com.thoughtworks.selenium.Selenium;

/**
 * Base Class to access {@link Selenium}. Offers some convenient methods to wait
 * for special events
 * 
 * @author ext.daehmc
 * 
 */
public class SeleniumBase {

    public static final int DEFAULT_TIMEOUT = 20;
    private static final int POLL_INTERVAL = 100;
    protected Selenium selenium;
    private int currentTimeoutSeconds = DEFAULT_TIMEOUT;

    public void setCurrentTimeoutSeconds(final int timeoutSeconds) {
        currentTimeoutSeconds = timeoutSeconds;
    }

    public int getCurrentTimeoutSeconds() {
        return currentTimeoutSeconds;
    }

    public SeleniumBase(final Selenium selenium) {
        this.selenium = selenium;
    }

    protected void waitForTitle(final String title) {
        waitFor(currentTimeoutSeconds, title, title(selenium));
    }

    protected void waitForPresent(final String locatorString) {
        waitFor(currentTimeoutSeconds, locatorString, present(selenium));
    }

    protected void waitForVisible(final String locatorString) {
        waitFor(currentTimeoutSeconds, locatorString, visible(selenium));
    }

    protected void waitForNotVisible(final String locatorString) {
        waitFor(currentTimeoutSeconds, locatorString, not(visible(selenium)));
    }

    protected void waitForEditable(final String locatorString) {
        waitFor(currentTimeoutSeconds, locatorString, editable(selenium));
    }

    protected void waitForNotEditable(final String locatorString) {
        waitFor(currentTimeoutSeconds, locatorString, not(editable(selenium)));
    }

    protected void waitForXpathCount(final String xpath, final Integer count) {
        waitFor(currentTimeoutSeconds, xpath, xpathCount(selenium, Matchers.greaterThanOrEqualTo(count)));
    }

    /**
     * wait on the actual site until the <code>elementPresentChecker</code>
     * succeeds or <code>timeoutSeconds</code> is reached
     * 
     * @param timeoutSeconds
     *            - timeout for waiting until element should appear on site
     * @param locatorString
     *            - a {@link Selenium} locator for the element to wait for
     * @param elementPresentChecker
     *            - the checker instance to be used
     * @see ElementOnPageChecker#isElementPresent(String)
     * @see Thread#sleep(long)
     * @see #failTimeout(String, String, long)
     */
    public void waitFor(final int timeoutSeconds, final String locatorString,
            final ElementOnPageChecker elementPresentChecker) {
        final long currentTimeMillis = System.currentTimeMillis();
        for (;;) {
            if (System.currentTimeMillis() - currentTimeMillis >= timeoutSeconds * 1000) {
                failTimeout(elementPresentChecker.what(), locatorString, timeoutSeconds);
            }
            try {
                if (elementPresentChecker.isElementPresent(locatorString)) {
                    break;
                }
            } catch (final Exception e) {
            }
            try {
                Thread.sleep(POLL_INTERVAL);
            } catch (final InterruptedException e) {
                // pass
            }
        }
    }

    private void failTimeout(final String what, final String locatorString, final long timeoutSeconds) {
        throw new AssertionError(String.format("failed to locate [%s(%s)] within [%d] seconds", what, locatorString,
                timeoutSeconds));
    }
}
