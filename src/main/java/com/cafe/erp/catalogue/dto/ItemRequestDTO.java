package com.cafe.erp.catalogue.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemRequestDTO {
    private String title;
    private String shortName;
    private String handle;
    private String description;
    private BigDecimal defaultPrice;
    private String foodType;
    private String itemType;
    private Boolean active;
    private Long categoryId;
}
