package de.pentasys.selenium.check;

import org.hamcrest.Matcher;

import com.thoughtworks.selenium.Selenium;

/**
 * hecks if an xpath matches a specific {@link Matcher}
 * 
 * @see Matcher#matches(Object)
 * @see Selenium#getXpathCount(String)
 * @author ext.daehmc
 * 
 */
public class XpathCountElementOnPageChecker extends ElementOnPageChecker {

    private final Matcher<Integer> matcher;

    public XpathCountElementOnPageChecker(final Selenium selenium, final Matcher<Integer> matcher) {
        super(selenium);
        this.matcher = matcher;
    }

    @Override
    public Boolean isElementPresent(final String checkCriteria) {
        return matcher.matches(selenium.getXpathCount(checkCriteria));
    }

    @Override
    public String what() {
        return "xpath count";
    }

}
