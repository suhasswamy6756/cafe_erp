package com.cafe.erp.modules.admin.location.service;


import com.cafe.erp.modules.admin.location.dto.LocationScheduleRequestDTO;
import com.cafe.erp.modules.admin.location.dto.LocationScheduleResponseDTO;

import java.util.List;

public interface LocationScheduleService {

    LocationScheduleResponseDTO create(LocationScheduleRequestDTO dto);

    LocationScheduleResponseDTO update(Long id, LocationScheduleRequestDTO dto);

    void delete(Long id);

    LocationScheduleResponseDTO getById(Long id);

    List<LocationScheduleResponseDTO> getAll();
}

