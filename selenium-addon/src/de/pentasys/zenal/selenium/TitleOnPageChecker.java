package de.pentasys.zenal.selenium;

import com.thoughtworks.selenium.Selenium;

/**
 * checks if page title matches
 * 
 * @see Selenium#getTitle()
 * @author ext.daehmc
 * 
 */
public class TitleOnPageChecker extends ElementOnPageChecker {

    public TitleOnPageChecker(final Selenium selenium) {
        super(selenium);
    }

    @Override
    public Boolean isElementPresent(final String checkCriteria) {
        return checkCriteria.equals(selenium.getTitle());
    }

    @Override
    public String what() {
        return "page title";
    }

}
