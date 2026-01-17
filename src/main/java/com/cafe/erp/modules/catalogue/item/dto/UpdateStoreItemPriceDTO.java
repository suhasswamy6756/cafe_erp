package com.cafe.erp.modules.catalogue.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStoreItemPriceDTO {

    private Long locationId;

    private BigDecimal dineInPrice;
    private BigDecimal takeawayPrice;
    private BigDecimal deliveryPrice;
    private BigDecimal aggregatorPrice;

    private Boolean isActive;
}

