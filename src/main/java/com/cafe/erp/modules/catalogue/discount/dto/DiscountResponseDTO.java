package com.cafe.erp.modules.catalogue.discount.dto;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class DiscountResponseDTO {

    private Long discountId;
    private String name;
    private String handle;
    private String description;

    private String type;
    private Double value;

    private LocalDate startDate;
    private LocalDate endDate;

    private Boolean isAutoApply;
    private Boolean isActive;

    private List<Long> itemIds;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}

