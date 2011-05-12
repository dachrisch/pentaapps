package de.pentasys.zenal.selenium;

import com.thoughtworks.selenium.Selenium;

/**
 * hecks if an element is editable
 * 
 * @see Selenium#isEditable(String)
 * @author ext.daehmc
 * 
 */
public class EditableElementOnPageChecker extends ElementOnPageChecker {

    public EditableElementOnPageChecker(final Selenium selenium) {
        super(selenium);
    }

    @Override
    public Boolean isElementPresent(final String checkCriteria) {
        return selenium.isEditable(checkCriteria);
    }

    @Override
    public String what() {
        return "editable element";
    }

}
