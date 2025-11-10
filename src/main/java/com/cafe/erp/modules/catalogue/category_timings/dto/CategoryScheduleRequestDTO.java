package com.cafe.erp.modules.catalogue.category_timings.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoryScheduleRequestDTO {
    private String name;
    private String handle;
    private String description;
    private Boolean isEveryday;
    private List<Integer> dayOfWeek;
    private String startTime;
    private String endTime;
    private Boolean isActive;
}

