package ik.appium.screen;

import ik.secretcloset.exceptions.ScrollToElementException;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;

import static io.appium.java_client.touch.offset.PointOption.point;

@Log4j2
public class SwipeActions {
    protected AppiumDriver<MobileElement> driver;

    public SwipeActions(AppiumDriver<MobileElement> driver) {
        this.driver = driver;
    }

    public MobileElement scrollDownToElement(By by, int maxScrollTimes, int scrollLengthScreenPercent) throws ScrollToElementException {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .pollingEvery(Duration.ofMillis(50))
                .withTimeout(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class);
        for (int i = 1; i <= maxScrollTimes; i++) {
            ExpectedCondition<WebElement> isVisible = ExpectedConditions.visibilityOfElementLocated(by);
            try {
                return (MobileElement) wait.until(isVisible);
            } catch (TimeoutException e){
                swipeUp(scrollLengthScreenPercent);
            }
        }
        throw new ScrollToElementException("Could not scroll down to element with locator " + by);
    }

    /**
     *
     * @param windowHeightPercentToSwipe - percent of window height to swipe on it, in range of [1..100].
     */
    public void swipeUp(int windowHeightPercentToSwipe) {
        if(windowHeightPercentToSwipe < 1 || windowHeightPercentToSwipe > 100) {
            throw new IllegalArgumentException("Percent of swipe length on the screen must be in range of [1..100]");
        }
        Dimension windowSize = driver.manage().window().getSize();
        log.debug("windowSize = " + windowSize);
        int percentValueToMove = windowSize.height / 100 * windowHeightPercentToSwipe;
        int yFrom = (windowSize.getHeight() + percentValueToMove) / 2;
        int yTo = (windowSize.getHeight() - percentValueToMove) / 2;
        int xOffset = windowSize.getWidth() / 2;
        new TouchAction<>(driver)
                .longPress(point(xOffset, yFrom))
                .moveTo(point(xOffset, yTo))
                .release()
                .perform();
        String formattedOutput = String.format("Swipe Up from (%d, %d) to (%d, %d)", xOffset, yFrom, xOffset, yTo);
        log.debug(formattedOutput);
    }
}
