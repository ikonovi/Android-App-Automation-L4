package ik.secretcloset.screens;

import ik.appium.screen.SwipeActions;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Log4j2
public abstract class Actionable extends Awatable {
    protected SwipeActions swipeActions;

    protected Actionable(AppiumDriver<MobileElement> driver) {
        super(driver);
        this.swipeActions = new SwipeActions(driver);
    }

    protected abstract void waitUntilLoaded();

    protected void acceptAnyAlert() {
        boolean alertIsPresent = ExpectedConditions.alertIsPresent().apply(driver) != null;
        if (alertIsPresent) {
            driver.switchTo().alert().accept();
            log.debug("Accept alert");
        }
    }
}
