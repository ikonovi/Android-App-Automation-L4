package ik.appium.driver;

import ik.properties.PropertyFile;
import ik.secretcloset.exceptions.ApplicationException;
import ik.secretcloset.exceptions.ReadAppPropertyException;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import static io.appium.java_client.remote.MobileCapabilityType.DEVICE_NAME;
import static io.appium.java_client.remote.MobileCapabilityType.PLATFORM_VERSION;
import static org.openqa.selenium.remote.CapabilityType.PLATFORM_NAME;

@Log4j2
public class AndroidDriverFactory implements WebDriverFactory {
    public static final String WEBDRIVER_PROPERTY_FILE_PATH = "webDriver.properties";

    private final String appiumHost;
    private final String appiumPort;
    private final String capabilityPlatformName;
    private final String capabilityAndroidVersion;
    private final String capabilityDeviceName;
    private final String capabilityAppPackage;
    private final String capabilityAppActivity;

    public AndroidDriverFactory() throws ReadAppPropertyException {
        super();
        PropertyFile propertyFile = new PropertyFile();
        Properties properties;
        try {
            properties = propertyFile.readFromPath(WEBDRIVER_PROPERTY_FILE_PATH);
        } catch (IOException e) {
            throw new ReadAppPropertyException("Could not read file with properties " + WEBDRIVER_PROPERTY_FILE_PATH, e);
        }
        this.appiumHost = properties.getProperty("appiumHost");
        this.appiumPort = properties.getProperty("appiumPort");
        this.capabilityPlatformName = properties.getProperty("capabilityPlatformName");
        this.capabilityAndroidVersion = properties.getProperty("capabilityAndroidVersion");
        this.capabilityDeviceName = properties.getProperty("capabilityDeviceName");
        this.capabilityAppPackage = properties.getProperty("capabilityAppPackage");
        this.capabilityAppActivity = properties.getProperty("capabilityAppActivity");
    }

    @Override
    public AndroidDriver<MobileElement> createWebDriver() throws ApplicationException {
        URL appiumServerUrl;
        try {
            appiumServerUrl = new URL("http://" + appiumHost + ":" + appiumPort + "/wd/hub");
        } catch (MalformedURLException exception) {
            String message = "Could not create Appium server URL";
            log.error(message, exception);
            throw new ApplicationException(message, exception);
        }
        DesiredCapabilities desiredCapabilities = initDesiredCapabilities();
        log.debug("Created AndroidDriver at {}", appiumServerUrl);
        return new AndroidDriver<>(appiumServerUrl, desiredCapabilities);
    }

    /**
     * Precondition: Only one Android emulator or device is connected.
     */
    private DesiredCapabilities initDesiredCapabilities() {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(PLATFORM_NAME, capabilityPlatformName);
        desiredCapabilities.setCapability(PLATFORM_VERSION, capabilityAndroidVersion);
        desiredCapabilities.setCapability(DEVICE_NAME, capabilityDeviceName);
        desiredCapabilities.setCapability("appPackage", capabilityAppPackage);
        desiredCapabilities.setCapability("appActivity", capabilityAppActivity);
        log.debug("Created {}", desiredCapabilities);
        return desiredCapabilities;
    }
}
