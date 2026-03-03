package com.cafe.erp.modules.catalogue.category_timings.service;

import com.cafe.erp.modules.catalogue.category_timings.dto.CategoryScheduleRequestDTO;
import com.cafe.erp.modules.catalogue.category_timings.dto.CategoryScheduleResponseDTO;

import java.util.List;

public interface CategoryScheduleService {
    CategoryScheduleResponseDTO createSchedule(CategoryScheduleRequestDTO dto);

    List<CategoryScheduleResponseDTO> getAllSchedules();

//    List<CategoryScheduleResponseDTO> getSchedulesForCategory(Long categoryId);

    CategoryScheduleResponseDTO getScheduleById(Long scheduleId);

    CategoryScheduleResponseDTO updateSchedule(Long scheduleId, CategoryScheduleRequestDTO dto);

    void deleteSchedule(Long scheduleId);


}

