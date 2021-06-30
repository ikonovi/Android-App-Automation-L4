package ik.secretcloset.screens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

@Log4j2
public class CitySearchResult extends Actionable {
    private By locatorList;
    private List<MobileElement> listItems;

    public CitySearchResult(AppiumDriver<MobileElement> driver, By locatorList, By locatorListItems) {
        super(driver);
        this.locatorList = locatorList;
        waitUntilLoaded();
        this.listItems = wait.until(webDriver -> driver.findElements(locatorListItems));
    }

    @Override
    protected void waitUntilLoaded() {
        wait.until(ExpectedConditions.elementToBeClickable(locatorList));
    }

    public Actionable tapFirstTopItem() {
        if(!listItems.isEmpty()) {
            MobileElement listFirstItem = listItems.get(0);
            log.debug("Click on first top item " + listFirstItem.getText());
            listFirstItem.click();
            return new HomeScreen(driver);
        } else {
            log.error("Nothing to click. Empty search result.");
            return new SelectCityScreen(driver);
        }
    }
}