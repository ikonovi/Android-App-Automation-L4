package ik.secretcloset.screens;

import ik.secretcloset.models.BrandBannerModel;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Log4j2
public class HomeScreen extends Actionable {
    private final By titleBy = By.id("com.zdv.secretcloset:id/tvTitle");
    private final By cityBy = By.id("com.zdv.secretcloset:id/tvToolbarCity");
    private final By brandBannersBy = By.xpath("//*[@resource-id='com.zdv.secretcloset:id/rlBanner']");

    private CityLabel cityLabel;
    private List<BrandBannerView> visibleBrandBanners;


    public HomeScreen(AppiumDriver<MobileElement> driver) {
        super(driver);
        waitUntilLoaded();
        this.cityLabel = new CityLabel(driver, cityBy);
    }

    @Override
    protected void waitUntilLoaded() {
        log.debug("Load " + this.getClass().getSimpleName());
        wait.until(ExpectedConditions.textMatches(titleBy, Pattern.compile("SecretCloset")));
    }

    public CityLabel getCityLabel() {
        return this.cityLabel;
    }

    public BrandBannerView findBrandBannerWithDiscountFirst() {
        BrandBannerView brandBannerViewWithDiscount = null;
        BrandBannerModel swipedBrandBannerModel = null;
        initVisibleBrandBanners();
        do {
            for (BrandBannerView brandBannerView : visibleBrandBanners) {
                String discountPercent = brandBannerView.getBrandBannerModel().getDiscountPercent();
                if (discountPercent != null && !discountPercent.isEmpty()) {
                    brandBannerViewWithDiscount = brandBannerView;
                    log.debug("First element with discount: " + brandBannerViewWithDiscount.getBrandBannerModel());
                    break;
                }
            }
            if (brandBannerViewWithDiscount == null && !visibleBrandBanners.isEmpty()) {
                BrandBannerView lastVisibleBanner = visibleBrandBanners.get(visibleBrandBanners.size() - 1);
                BrandBannerModel lastVisibleBrandBannerModel = lastVisibleBanner.getBrandBannerModel();
                if (lastVisibleBrandBannerModel.equals(swipedBrandBannerModel)) {
                    log.warn("No elements anymore to look for banner having discount.");
                    break;
                } else {
                    swipedBrandBannerModel = lastVisibleBanner.getBrandBannerModel();
                    lastVisibleBanner.swipeToTheLeft();
                    initVisibleBrandBanners();
                }
            }
        } while (brandBannerViewWithDiscount == null);
        return brandBannerViewWithDiscount;
    }

    private void initVisibleBrandBanners() {
        List<MobileElement> brandBanners = driver.findElements(brandBannersBy);
        visibleBrandBanners = new ArrayList<>();

        for (MobileElement brandBannerMobElement : brandBanners) {
            BrandBannerView brandBannerView = new BrandBannerView(brandBannerMobElement, driver);
            BrandBannerModel brandBannerModel = brandBannerView.getBrandBannerModel();
            boolean isVisibleOnScreen = brandBannerModel.getBrandName() != null
                    && !brandBannerModel.getBrandName().isEmpty();
            if(isVisibleOnScreen) {
                visibleBrandBanners.add(brandBannerView);
            }
        }
    }
}