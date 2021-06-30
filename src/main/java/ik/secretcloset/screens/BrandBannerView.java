package ik.secretcloset.screens;

import ik.properties.PropertyFile;
import ik.secretcloset.models.BrandBannerModel;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

@Log4j2
public class BrandBannerView {
    public static final String TIMEOUT_PROPERTIES_FILEPATH = "timeout.properties";
    public static final String WAIT_DEFAULT_TIMEOUT_SECONDS = "30";

    private final By brandNameBy = By.xpath("//*[@resource-id='com.zdv.secretcloset:id/tvBrand']");
    private final By pictureBy = By.xpath("//*[@resource-id='com.zdv.secretcloset:id/ivProductPic']");
    private final By previousPriceBy = By.xpath("//*[@resource-id='com.zdv.secretcloset:id/tvSumm']");
    private final By currentPriceBy = By.xpath("//*[@resource-id='com.zdv.secretcloset:id/tvPrice']");
    private final By discountPercentBy = By.xpath("//*[@resource-id='com.zdv.secretcloset:id/tvDiscount']");

    private final AppiumDriver<MobileElement> driver;

    private final MobileElement contextElement;
    private final Wait<WebDriver> wait;
    private BrandBannerModel brandBannerModel;

    public BrandBannerView(MobileElement contextElement, AppiumDriver<MobileElement> driver) {
        this.contextElement = contextElement;
        this.driver = driver;
        PropertyFile propertyFile = new PropertyFile();
        Properties properties = new Properties();
        try {
            properties = propertyFile.readFromPath(TIMEOUT_PROPERTIES_FILEPATH);
        } catch (IOException exception) {
            log.error("Could not read file with properties " + TIMEOUT_PROPERTIES_FILEPATH, exception);
        }
        String waitTimeoutSeconds = properties.getProperty("brandBannerScreenElementWaitTimeoutSeconds", WAIT_DEFAULT_TIMEOUT_SECONDS);
        this.wait = new WebDriverWait(driver, Integer.parseInt(waitTimeoutSeconds));
        initializeModel();
    }

    private void initializeModel() {
        Wait<WebDriver> waitFluently = new FluentWait<WebDriver>(driver)
                .pollingEvery(Duration.ofMillis(200))
                .withTimeout(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);
        brandBannerModel = BrandBannerModel.builder()
                .brandName(getElementText(contextElement, brandNameBy, waitFluently))
                .previousPrice(getElementText(contextElement, previousPriceBy, waitFluently))
                .currentPrice(getElementText(contextElement, currentPriceBy, waitFluently))
                .discountPercent(getElementText(contextElement, discountPercentBy, waitFluently))
                .build();
        log.debug("Initialized: {}", this.getBrandBannerModel());
    }

    private String getElementText(MobileElement context, By byLocator, Wait<WebDriver> wait){
        String elementText = "";
        try {
            MobileElement element = wait.until(webDriver -> context.findElement(byLocator));
            elementText = element.getText();
        } catch (TimeoutException ignore) {
            log.warn("Couldn't get text of element by locator {} in context element. Perhaps the element " +
                    "is not fully visible on the screen." , byLocator);
        }
        return elementText;
    }

    public ProductItem tap() {
        MobileElement element =  wait.until(webDriver -> contextElement.findElement(pictureBy));
        element.click();
        log.debug("Tap on brand banner");
        return new ProductItem(driver);
    }

    public void swipeToTheLeft() {
        MobileElement pictureElement = wait.until(webDriver -> contextElement.findElement(pictureBy));
        Point center = pictureElement.getCenter();
        int offsetDirectionCoefficient = -1;
        int screenBorderMargin = 10;
        int xOffset = offsetDirectionCoefficient * (center.y - screenBorderMargin);
        int yOffset = 0;
        log.debug("Swipe to the left on banner: "+ this);
        new Actions(driver)
                .clickAndHold(pictureElement)
                .moveByOffset(xOffset, yOffset)
                .release()
                .build().perform();
    }

    public BrandBannerModel getBrandBannerModel() {
        return brandBannerModel;
    }
}
