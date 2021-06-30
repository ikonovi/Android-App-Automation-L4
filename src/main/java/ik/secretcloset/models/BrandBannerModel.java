package ik.secretcloset.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BrandBannerModel {
    private String brandName;
    /* field of picture omitted */
    private String currentPrice;
    private String previousPrice;
    private String discountPercent;
}

