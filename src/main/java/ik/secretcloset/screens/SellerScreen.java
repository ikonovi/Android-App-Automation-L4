package ik.secretcloset.screens;

import ik.secretcloset.models.SellerModel;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Log4j2
public class SellerScreen extends Actionable {
    private final By idBy = By.id("com.zdv.secretcloset:id/tvItemSellerID");
    private final By nameBy = By.xpath("//*[@resource-id='com.zdv.secretcloset:id/toolbar']/android.widget.TextView");
    private final By cityBy = By.id("com.zdv.secretcloset:id/tvItemSellerCity");

    private SellerModel sellerModel;

    protected SellerScreen(AppiumDriver<MobileElement> driver) {
        super(driver);
        waitUntilLoaded();
        initializeModel();
    }

    @Override
    protected void waitUntilLoaded() {
        log.debug("Load " + this.getClass().getSimpleName());
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameBy));
    }

    public SellerModel getSellerModel() {
        return sellerModel;
    }

    private void initializeModel() {
        sellerModel = new SellerModel();

        WebElement idElement = wait.until(webDriver -> webDriver.findElement(idBy));
        sellerModel.setId(idElement.getText());

        WebElement nameElement = wait.until(webDriver -> webDriver.findElement(nameBy));
        sellerModel.setName(nameElement.getText());

        WebElement cityElement = wait.until(webDriver -> webDriver.findElement(cityBy));
        sellerModel.setCity(cityElement.getText());
    }
}
