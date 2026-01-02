package com.cafe.erp.modules.catalogue.item.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class UpdateStoreItemPriceDTO {
    private Long itemId;
    private Long locationId;
    private BigDecimal price;


}
