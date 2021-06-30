package ik.secretcloset.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
//@NoArgsConstructor
public class ProductItemModel {
    private String brandName;
    // header section
    private String headerDiscountPercent;
    private String headerPreviousPrice;
    private String headerCurrentPrice;
    // seller section
    private String sellerName;
    private String sellerCity;
    // item detail section
    private String itemId;
    private String previousPrice;
    private String currentPrice;
    private String discountPercent;
}
