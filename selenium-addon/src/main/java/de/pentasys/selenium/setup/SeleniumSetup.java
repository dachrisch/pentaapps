package de.pentasys.selenium.setup;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SeleniumSetup {

    public static WebDriverBackedSelenium createSeleniumInstance(final String url) {
        final File binaryLocation = new File(System.getProperty("firefox.binary",
                "C:\\Users\\daehnc\\AppData\\Local\\Mozilla Firefox\\firefox.exe"));
        final WebDriver webDriver = new FirefoxDriver(new FirefoxBinary(binaryLocation), null);

        return new WebDriverBackedSelenium(webDriver, url);
    }
}
