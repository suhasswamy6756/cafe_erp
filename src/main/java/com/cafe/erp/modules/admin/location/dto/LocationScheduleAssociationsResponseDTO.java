package com.cafe.erp.modules.admin.location.dto;


import lombok.Data;

@Data
public class LocationScheduleAssociationsResponseDTO {

    private Long id;
    private Long locationId;
    private Long scheduleId;
    private String scheduleName;
    private String scheduleHandle;
}
