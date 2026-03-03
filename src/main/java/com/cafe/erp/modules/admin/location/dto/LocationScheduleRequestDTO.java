package com.cafe.erp.modules.admin.location.dto;


import lombok.Data;
import java.time.LocalTime;

@Data
public class LocationScheduleRequestDTO {
    private String name;
    private String handle;
    private String description;

    private boolean isEveryday = true;

    private int[] dayOfWeek;     // e.g., [0, 1, 2]

    private LocalTime startTime;
    private LocalTime endTime;

    private boolean isActive = true;
}

