package de.pentasys.zenal.selenium;

import com.thoughtworks.selenium.Selenium;

/**
 * checks if an element is present
 * 
 * @see Selenium#isElementPresent(String)
 * @author ext.daehmc
 * 
 */
public class ElementPresentOnPageChecker extends ElementOnPageChecker {

    public ElementPresentOnPageChecker(final Selenium selenium) {
        super(selenium);
    }

    @Override
    public Boolean isElementPresent(final String checkCriteria) {
        return selenium.isElementPresent(checkCriteria);
    }

    @Override
    public String what() {
        return "present element";
    }

}
