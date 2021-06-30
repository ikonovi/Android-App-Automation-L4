package ik.secretcloset.screens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public @Log4j2
class CitySearchBox extends Actionable {
    private By locator;
    private MobileElement element;

    public CitySearchBox(AppiumDriver<MobileElement> driver, By locator) {
        super(driver);
        this.locator = locator;
        waitUntilLoaded();
        this.element = driver.findElement(locator);
    }

    @Override
    protected void waitUntilLoaded() {
        super.acceptAnyAlert();
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public SelectCityScreen type(String city) {
        log.debug("Type \"" + city + "\"");
        element.sendKeys(city);
        return new SelectCityScreen(driver);
    }
}
