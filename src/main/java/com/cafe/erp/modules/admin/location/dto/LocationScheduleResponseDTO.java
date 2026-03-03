package com.cafe.erp.modules.admin.location.dto;


import lombok.Data;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class LocationScheduleResponseDTO {

    private Long scheduleId;
    private String name;
    private String handle;
    private String description;

    private boolean isEveryday;
    private int[] dayOfWeek;

    private LocalTime startTime;
    private LocalTime endTime;

    private boolean isActive;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
