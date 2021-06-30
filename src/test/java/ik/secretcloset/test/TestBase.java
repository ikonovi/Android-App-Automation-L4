package ik.secretcloset.test;

import ik.appium.driver.AndroidDriverFactory;
import ik.secretcloset.exceptions.ApplicationException;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class TestBase {
    protected AppiumDriver<MobileElement> driver;

    @BeforeClass
    public void initDriver() {
        try {
            driver = new AndroidDriverFactory().createWebDriver();
        } catch (ApplicationException exception) {
            Assert.fail("Could not get Appium driver instance.", exception);
        }
    }

    @AfterClass
    public void quitDriver() {
        //driver.quit();
    }
}
