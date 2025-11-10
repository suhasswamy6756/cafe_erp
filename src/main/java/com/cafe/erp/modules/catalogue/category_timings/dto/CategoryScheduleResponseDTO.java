package com.cafe.erp.modules.catalogue.category_timings.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class CategoryScheduleResponseDTO {

    private Long scheduleId;
    private String name;
    private String handle;
    private String description;

    private Boolean isEveryday;
    private List<Integer> dayOfWeek;

    private String startTime;
    private String endTime;

    private Boolean isActive;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

