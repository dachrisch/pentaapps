package de.pentasys.selenium.check;

/**
 * simply inverts the result of another {@link ElementOnPageChecker}
 * 
 * @author ext.daehmc
 * 
 */
public class InvertingElementOnPageChecker extends ElementOnPageChecker {

    private final ElementOnPageChecker elementPresentChecker;

    public InvertingElementOnPageChecker(final ElementOnPageChecker elementPresentChecker) {
        super(elementPresentChecker.selenium);
        this.elementPresentChecker = elementPresentChecker;
    }

    @Override
    public Boolean isElementPresent(final String checkCriteria) {
        return !elementPresentChecker.isElementPresent(checkCriteria);
    }

    @Override
    public String what() {
        return "not";
    }

}
