package com.cafe.erp.modules.catalogue.discount.dto;


import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class DiscountRequestDTO {

    private String name;
    private String handle;
    private String description;

    private String type;
    private Double value;

    private LocalDate startDate;
    private LocalDate endDate;

    private Boolean isAutoApply;
    private Boolean isActive;

    private List<Long> itemIds;   // ✅ mapping ids
}

