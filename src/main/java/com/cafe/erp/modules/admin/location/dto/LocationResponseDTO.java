package com.cafe.erp.modules.admin.location.dto;


import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class LocationResponseDTO {

    private Long locationId;
    private String name;
    private String shortName;
    private String address;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String phone;
    private String email;

    private List<String> schedules;
    private List<String> taxes;
    private List<String> charges;
    private List<String> discounts;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

