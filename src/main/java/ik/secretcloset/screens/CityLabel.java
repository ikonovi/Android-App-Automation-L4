package ik.secretcloset.screens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.regex.Pattern;

@Log4j2
public class CityLabel extends Actionable {

    private By locator;
    private MobileElement element;

    public CityLabel(AppiumDriver<MobileElement> driver, By locator) {
        super(driver);
        this.locator = locator;
        waitUntilLoaded();
        this.element = wait.until(webDriver -> driver.findElement(locator));
    }

    @Override
    protected void waitUntilLoaded() {
        wait.until(ExpectedConditions.textMatches(locator, Pattern.compile("\\w")));
    }

    public SelectCityScreen tap() {
        super.acceptAnyAlert();
        log.debug("Tap");
        element.click();
        return new SelectCityScreen(driver);
    }

    public String getTextAfterChangeTo(String city) {
        try {
            wait.until(ExpectedConditions.textToBe(locator, city));
        } catch (TimeoutException ignored) { // not expected city displayed
        }
        element = driver.findElement(locator);
        return this.getText();
    }

    public String getText() {
        return element.getText();
    }
}
