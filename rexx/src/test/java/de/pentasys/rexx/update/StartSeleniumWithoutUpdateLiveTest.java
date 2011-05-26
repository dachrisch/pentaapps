package de.pentasys.rexx.update;

import static de.pentasys.selenium.setup.SeleniumSetup.createSeleniumInstance;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriverBackedSelenium;

public class StartSeleniumWithoutUpdateLiveTest {
    

    @Test
    public void startSeleniumInOrderToCheckClasspath() throws Exception {
        final WebDriverBackedSelenium selenium = createSeleniumInstance("http://google.com");

        assertThat(selenium, notNullValue());

        selenium.stop();
    }
}
