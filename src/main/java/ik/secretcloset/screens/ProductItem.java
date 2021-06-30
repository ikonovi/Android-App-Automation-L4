package ik.secretcloset.screens;

import ik.secretcloset.exceptions.ScrollToElementException;
import ik.secretcloset.models.ProductItemModel;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Log4j2
public class ProductItem extends Actionable {
    private String appIdPrefix = "com.zdv.secretcloset:id/";
    // Page Header
    private final By headerBrandNameBy = By.id(appIdPrefix + "tvItemBrand");
    private final By headerDiscountPercentBy = By.id(appIdPrefix + "tvItemDiscount");
    private final By headerPreviousPriceBy = By.id(appIdPrefix + "tvItemOriginalPrice");
    private final By headerCurrentPriceBy = By.id(appIdPrefix + "tvItemPrice");
    // Page Seller section
    private final By sellerNameValueBy = By.id(appIdPrefix + "tvItemSellerName");
    private final By sellerCityValueBy = By.id(appIdPrefix + "tvItemSellerCity");
    private final By sellerAvatarBy = By.id(appIdPrefix + "ivSellerAvatar");
    // Page Detail section
    private final By itemIdBy = By.id(appIdPrefix + "tvAdditionalId");
    private final By previousPriceBy = By.id(appIdPrefix + "tvAdditionalOriginal");
    private final By currentPriceBy = By.id(appIdPrefix + "tvAdditionalPrice");
    private final By discountPercentBy = By.id(appIdPrefix + "tvAdditionalDiscount");

    private ProductItemModel productItemModel;

    public ProductItem(AppiumDriver<MobileElement> driver) {
        super(driver);
        waitUntilLoaded();
        initializeModel();
    }

    @Override
    protected void waitUntilLoaded() {
        log.debug("Load " + this.getClass().getSimpleName());
        wait.until(ExpectedConditions.visibilityOfElementLocated(headerBrandNameBy));
    }

    public SellerScreen tapSellerAvatar() {
        WebElement sellerAvatarElem = wait.until(webDriver -> webDriver.findElement(sellerAvatarBy));
        sellerAvatarElem.click();
        log.debug("clicked seller's avatar");
        return new SellerScreen(driver);
    }

    public ProductItemModel getProductItemModel() {
        return productItemModel;
    }

    private void initializeModel() {
        productItemModel = ProductItemModel.builder()
                .brandName(getElementText(headerBrandNameBy))
                .headerDiscountPercent(getElementText(headerDiscountPercentBy))
                .headerPreviousPrice(getElementText(headerPreviousPriceBy))
                .headerCurrentPrice(getElementText(headerCurrentPriceBy))
                .sellerName(getElementText(sellerNameValueBy))
                .sellerCity(getElementText(sellerCityValueBy))
                .itemId(getElementText(itemIdBy))
                // Init model fields that can be not visible on the screen of size 1080x1794 and less.
                .previousPrice(getElementTextWithSwipe(previousPriceBy))
                .currentPrice(substringValueOfFieldInJoinedText(getElementTextWithSwipe(currentPriceBy)))
                .discountPercent(getElementTextWithSwipe(discountPercentBy))
            .build();
    }

    private String getElementText(By locator) {
        MobileElement element = wait.until(webDriver -> webDriver.findElement(locator));
        return element.getText();
    }

    /**
     * @param locator of element
     * @return text of element that initially not visible on the screen.
     */
    private String getElementTextWithSwipe(By locator) {
        String elementText = "";
        try {
            MobileElement currentPrice = swipeActions.scrollDownToElement(locator, 5, 20);
            elementText = currentPrice.getText();
        } catch (ScrollToElementException e) {
            log.error("Couldn't get text of invisible element having scroll, locator=" + locator);
        }
        return elementText;
    }

    private String substringValueOfFieldInJoinedText(String joinedFieldValue) {
        String fieldNameAndValueSeparator = ":";
        if (joinedFieldValue.contains(fieldNameAndValueSeparator)) {
            int indexOf = joinedFieldValue.indexOf(fieldNameAndValueSeparator);
            return joinedFieldValue.substring(indexOf + 1).trim();
        } else {
            return joinedFieldValue;
        }
    }
}
