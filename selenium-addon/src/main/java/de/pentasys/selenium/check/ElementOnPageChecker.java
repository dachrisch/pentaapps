package de.pentasys.selenium.check;

import org.hamcrest.Matcher;

import com.thoughtworks.selenium.Selenium;

/**
 * abstract class offering {@link #isElementPresent(String)} checks to be used
 * by {@link SeleniumBase#waitFor(int, String, ElementOnPageChecker)}
 * 
 * @author ext.daehmc
 * 
 */
public abstract class ElementOnPageChecker {

    protected final Selenium selenium;

    public ElementOnPageChecker(final Selenium selenium) {
        this.selenium = selenium;
    }

    /**
     * performs the check if a element specified by <code>checkCriteria</code>
     * is present at the current {@link Selenium} page
     * 
     * @param checkCriteria
     *            - the criteria to check against. mostly this will be a
     *            {@link Selenium}-locator
     * @return true if the checks was successful, otherwise false
     * @see Selenium#isElementPresent(String)
     */
    public abstract Boolean isElementPresent(String checkCriteria);

    /**
     * identify what the actual instance is doing
     * 
     * @return a string representing the purpose of the checker instance
     */
    public abstract String what();

    public static ElementOnPageChecker title(final Selenium selenium) {
        return new TitleOnPageChecker(selenium);
    }

    public static ElementOnPageChecker present(final Selenium selenium) {
        return new ElementPresentOnPageChecker(selenium);
    }

    public static ElementOnPageChecker visible(final Selenium selenium) {
        return new VisibleElementOnPageChecker(selenium);
    }

    public static ElementOnPageChecker xpathCount(final Selenium selenium, final Matcher<Integer> matcher) {
        return new XpathCountElementOnPageChecker(selenium, matcher);
    }

    public static ElementOnPageChecker not(final ElementOnPageChecker elementPresentChecker) {
        return new InvertingElementOnPageChecker(elementPresentChecker);
    }

    public static ElementOnPageChecker editable(final Selenium selenium) {
        return new EditableElementOnPageChecker(selenium);
    }

}
