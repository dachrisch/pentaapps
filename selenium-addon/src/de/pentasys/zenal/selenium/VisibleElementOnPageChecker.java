package de.pentasys.zenal.selenium;

import com.thoughtworks.selenium.Selenium;

/**
 * hecks if an element is visible
 * 
 * @see Selenium#isVisible(String)
 * @author ext.daehmc
 * 
 */
class VisibleElementOnPageChecker extends ElementOnPageChecker {
    public VisibleElementOnPageChecker(final Selenium selenium) {
        super(selenium);
    }

    @Override
    public Boolean isElementPresent(final String checkCriteria) {
        return selenium.isVisible(checkCriteria);
    }

    @Override
    public String what() {
        return "visible element";
    }

}