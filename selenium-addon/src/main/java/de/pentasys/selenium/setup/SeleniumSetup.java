package de.pentasys.selenium.setup;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SeleniumSetup {

    public static WebDriverBackedSelenium createSeleniumInstance(final String url) {
        final FirefoxBinary firefoxBinary = new FirefoxBinary(locateFirefoxBinary());
        final WebDriver webDriver = new FirefoxDriver(firefoxBinary, null);

        return new WebDriverBackedSelenium(webDriver, url);
    }

    public static File locateFirefoxBinary() {
        final File binaryLocation = new File(System.getProperty("firefox.binary"));
        return binaryLocation;
    }
}
