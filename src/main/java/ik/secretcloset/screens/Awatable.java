package ik.secretcloset.screens;

import ik.properties.PropertyFile;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.Properties;

@Log4j2
public abstract class Awatable extends Driverable {
    public static final String TIMEOUT_PROPERTIES_FILEPATH = "timeout.properties";
    public static final String WAIT_DEFAULT_TIMEOUT_SECONDS = "30";

    protected WebDriverWait wait;

    protected Awatable(AppiumDriver<MobileElement> driver) {
        super(driver);

        PropertyFile propertyFile = new PropertyFile();
        Properties properties = new Properties();
        try {
            properties = propertyFile.readFromPath(TIMEOUT_PROPERTIES_FILEPATH);
        } catch (IOException e) {
            log.error("Could not read file with properties " + TIMEOUT_PROPERTIES_FILEPATH, e);
        }
        String timeoutSeconds = properties.getProperty("abstractScreenWebDriverWaitTimeoutSeconds", WAIT_DEFAULT_TIMEOUT_SECONDS);

        this.wait = new WebDriverWait(driver, Integer.parseInt(timeoutSeconds));
    }
}
