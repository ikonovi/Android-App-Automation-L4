package ik.secretcloset.test;

import ik.secretcloset.models.BrandBannerModel;
import ik.secretcloset.models.ProductItemModel;
import ik.secretcloset.models.SellerModel;
import ik.secretcloset.screens.*;
import org.assertj.core.api.SoftAssertions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SecretClosetScenarioTest extends TestBase {

    private final SoftAssertions softly = new SoftAssertions();
    private final static String testCity = "Dubai";

    @Test(description = "SecretCloset test scenario")
    public void testScenario() {

        HomeScreen homeScreen = new HomeScreen(driver);
        // 1. Click on city label
        SelectCityScreen selectCityScreen = homeScreen.getCityLabel().tap();

        // 2. Type 'Los Angeles' in search field
        SelectCityScreen selectCityScreenWithSearchResult = selectCityScreen.getCitySearchBox().type(testCity);

        // 3. Click on search result 'Los Angeles, CA'
        HomeScreen homeScreenWithSelectedCity = (HomeScreen) selectCityScreenWithSearchResult.getCitySearchResult().tapFirstTopItem();

        // 4. Check 'L.Angeles' is selected as region
        String actualDisplayedCity = homeScreenWithSelectedCity.getCityLabel().getTextAfterChangeTo(testCity);
        Assert.assertEquals(actualDisplayedCity, testCity);


        // 5. Select first item with discount
        BrandBannerView brandBannerWithDiscount = homeScreenWithSelectedCity.findBrandBannerWithDiscountFirst();
        ProductItem productItemPage = brandBannerWithDiscount.tap();

        // 6. Check that selected item is displaying
        // 7. Check that old price, discount and price with discount are correct
        BrandBannerModel brandBannerModel = brandBannerWithDiscount.getBrandBannerModel();
        ProductItemModel productItemModel = productItemPage.getProductItemModel();
        softly.assertThat(productItemModel.getBrandName()).as("Brand").isEqualTo(brandBannerModel.getBrandName());
        softly.assertThat(productItemModel.getHeaderCurrentPrice()).as("Current Price").isEqualTo(brandBannerModel.getCurrentPrice());
        softly.assertThat(productItemModel.getHeaderPreviousPrice()).as("Previous Price").isEqualTo(brandBannerModel.getPreviousPrice());
        softly.assertThat(productItemModel.getHeaderDiscountPercent()).as("Discount Percent").isEqualTo(brandBannerModel.getDiscountPercent());
        softly.assertThat(productItemModel.getCurrentPrice()).as("CurrentPrice").isEqualTo(brandBannerModel.getCurrentPrice());
        softly.assertThat(productItemModel.getPreviousPrice()).as("PreviousPrice").isEqualTo(brandBannerModel.getPreviousPrice());
        softly.assertThat(productItemModel.getDiscountPercent()).as("Discount Percent").isEqualTo(brandBannerModel.getDiscountPercent());
        softly.assertAll();

        // 8. Click on seller
        SellerScreen sellerScreen = productItemPage.tapSellerAvatar();

        // 9. Check that seller information is correct (name, city)
        SellerModel sellerModel2 = sellerScreen.getSellerModel();
        ProductItemModel productItemModel2 = productItemPage.getProductItemModel();
        softly.assertThat(sellerModel2.getName()).as("Name").isEqualTo(productItemModel2.getSellerName());
        softly.assertThat(sellerModel2.getCity()).as("City").isEqualTo(productItemModel2.getSellerCity());
        softly.assertAll();
    }
}
