package de.pentasys.selenium.setup;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.Properties;
import java.util.Map.Entry;

import org.junit.Test;

public class SeleniumSetupTest {

    @Test
    public void assertThatFirefoxBinaryIsAtSpecifiedLocation() throws Exception {
        final Properties properties = System.getProperties();

        for (final Entry<Object, Object> property : properties.entrySet()) {
            System.out.println(property);
        }

        System.out.println("env: " + System.getenv());
        final File firefoxBinary = SeleniumSetup.locateFirefoxBinary();
        assertThat(String.format("failed to locate firefox at [%s]", firefoxBinary.getAbsolutePath()), firefoxBinary
                .exists(), is(true));
    }
}
