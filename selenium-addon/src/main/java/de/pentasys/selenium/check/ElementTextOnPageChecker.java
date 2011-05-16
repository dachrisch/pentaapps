/**
 * 
 */
package de.pentasys.selenium.check;

import com.thoughtworks.selenium.Selenium;

/**
 * checks if a locator refers to a specific text to match
 * 
 * @see Selenium#getText(String)
 * @author ext.daehmc
 * 
 */
public class ElementTextOnPageChecker extends ElementOnPageChecker {
    private final String activeRoleLocator;

    public ElementTextOnPageChecker(final Selenium selenium, final String activeRoleLocator) {
        super(selenium);
        this.activeRoleLocator = activeRoleLocator;
    }

    @Override
    public Boolean isElementPresent(final String checkCriteria) {
        return selenium.getText(activeRoleLocator).equals(checkCriteria);
    }

    @Override
    public String what() {
        return "user role";
    }
}