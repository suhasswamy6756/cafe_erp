package com.cafe.erp.modules.admin.location.service;


import com.cafe.erp.modules.admin.location.dto.LocationRequestDTO;
import com.cafe.erp.modules.admin.location.dto.LocationResponseDTO;

import java.util.List;

public interface LocationService {

    LocationResponseDTO createLocation(LocationRequestDTO req);

    LocationResponseDTO updateLocation(Long id, LocationRequestDTO req);

    void deleteLocation(Long id);

    LocationResponseDTO getLocation(Long id);

    List<LocationResponseDTO> getAllLocations();
}

