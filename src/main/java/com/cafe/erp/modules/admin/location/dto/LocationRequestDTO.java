package com.cafe.erp.modules.admin.location.dto;


import lombok.Data;
import java.util.List;

@Data
public class LocationRequestDTO {
    private String name;
    private String shortName;
    private String address;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String phone;
    private String email;

    private List<Long> scheduleIds;
    private List<Long> taxIds;
    private List<Long> chargeIds;
    private List<Long> discountIds;
}

