package ik.secretcloset.screens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Log4j2
public class SelectCityScreen extends Actionable {
    private final By searchBoxBy = By.id("com.zdv.secretcloset:id/etSearchTest");
    private final By searchResultListBy = By.id("com.zdv.secretcloset:id/lvSearchList");
    private final By searchResultListItemsBy = By.xpath("//android.widget.ListView//android.widget.TextView");

    private final CitySearchBox citySearchBox;
    private CitySearchResult citySearchResult;

    public SelectCityScreen(AppiumDriver<MobileElement> driver) {
        super(driver);
        this.citySearchBox = new CitySearchBox(driver, searchBoxBy);
        waitUntilLoaded();
    }

    @Override
    protected void waitUntilLoaded() {
        log.debug("Load " + this.getClass().getSimpleName());
        wait.until(ExpectedConditions.or(
                ExpectedConditions.elementToBeClickable(searchBoxBy),
                ExpectedConditions.alertIsPresent()));
    }

    public CitySearchBox getCitySearchBox() {
        return citySearchBox;
    }

    public CitySearchResult getCitySearchResult() {
        initCitySearchResult();
        return citySearchResult;
    }

    private void initCitySearchResult() {
        this.citySearchResult = new CitySearchResult(driver, searchResultListBy, searchResultListItemsBy);
    }
}
