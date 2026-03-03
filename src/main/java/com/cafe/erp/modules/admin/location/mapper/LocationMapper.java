package com.cafe.erp.modules.admin.location.mapper;

import com.cafe.erp.modules.admin.location.dto.LocationResponseDTO;
import com.cafe.erp.modules.admin.location.entity.Location;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class LocationMapper {

    public LocationResponseDTO toDto(Location location) {
        LocationResponseDTO dto = new LocationResponseDTO();

        dto.setLocationId(location.getLocationId());
        dto.setName(location.getName());
        dto.setShortName(location.getShortName());
        dto.setAddress(location.getAddress());
        dto.setCity(location.getCity());
        dto.setState(location.getState());
        dto.setCountry(location.getCountry());
        dto.setPostalCode(location.getPostalCode());
        dto.setPhone(location.getPhone());
        dto.setEmail(location.getEmail());
        dto.setCreatedAt(location.getCreatedAt());
        dto.setUpdatedAt(location.getUpdatedAt());

        dto.setSchedules(location.getSchedules().stream()
                .map(a -> a.getSchedule().getName())
                .collect(Collectors.toList()));

        dto.setTaxes(location.getTaxes().stream()
                .map(a -> a.getTax().getName())
                .collect(Collectors.toList()));

        dto.setCharges(location.getCharges().stream()
                .map(a -> a.getCharge().getName())
                .collect(Collectors.toList()));

        dto.setDiscounts(location.getDiscounts().stream()
                .map(a -> a.getDiscount().getName())
                .collect(Collectors.toList()));

        return dto;
    }
}

