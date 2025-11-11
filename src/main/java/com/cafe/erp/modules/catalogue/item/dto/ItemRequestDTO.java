package com.cafe.erp.modules.catalogue.item.dto;

import lombok.Data;

import java.util.List;

@Data
public class ItemRequestDTO {

    private String name;
    private String shortName;
    private String handle;
    private String description;

    private Long categoryId;

    // Pricing
    private Double basePrice;
    private Double dineInPrice;
    private Double takeawayPrice;
    private Double deliveryPrice;
    private Double aggregatorPrice;

    private String markupType;       // NONE / FLAT / PERCENTAGE
    private Double markupValue;

    private Boolean isActive;

    // List of modifier groups to attach to item
    private List<Long> modifierGroupIds;
}
