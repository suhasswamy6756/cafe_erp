package com.cafe.erp.modules.catalogue.item.dto;

import com.cafe.erp.common.enums.FoodType;
import com.cafe.erp.common.enums.ItemType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ItemResponseDTO {

    private Long id;

    private String name;
    private String shortName;
    private String handle;
    private String description;

    private Long categoryId;

    private String posCode;
    private FoodType foodType;
    private ItemType itemType;

    private Long recipeId;

    // Pricing
    private BigDecimal basePrice;
    private BigDecimal dineInPrice;
    private Double takeawayPrice;
    private Double deliveryPrice;
    private Double aggregatorPrice;

    private String markupType;
    private Double markupValue;

    private Boolean isActive;

    private List<Long> modifierGroupIds;

    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
