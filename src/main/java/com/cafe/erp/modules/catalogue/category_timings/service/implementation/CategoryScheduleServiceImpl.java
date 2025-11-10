package com.cafe.erp.modules.catalogue.category_timings.service.implementation;

import com.cafe.erp.common.exception.ResourceNotFoundException;
import com.cafe.erp.modules.catalogue.category_timings.dto.CategoryScheduleRequestDTO;
import com.cafe.erp.modules.catalogue.category_timings.dto.CategoryScheduleResponseDTO;
import com.cafe.erp.modules.catalogue.category_timings.entity.CategorySchedule;
import com.cafe.erp.modules.catalogue.category_timings.repository.CategoryScheduleRepository;
import com.cafe.erp.modules.catalogue.category_timings.service.CategoryScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryScheduleServiceImpl implements CategoryScheduleService {

    private final CategoryScheduleRepository scheduleRepo;

    @Override
    public CategoryScheduleResponseDTO createSchedule(CategoryScheduleRequestDTO dto) {

        CategorySchedule schedule = CategorySchedule.builder()
                .name(dto.getName())
                .handle(dto.getHandle())
                .description(dto.getDescription())
                .isEveryday(dto.getIsEveryday())
                .dayOfWeek(dto.getDayOfWeek() != null ?
                        dto.getDayOfWeek().toArray(new Integer[0]) : null)
                .startTime(LocalTime.parse(dto.getStartTime()))
                .endTime(LocalTime.parse(dto.getEndTime()))
                .isActive(dto.getIsActive())
                .build();

        CategorySchedule saved = scheduleRepo.save(schedule);

        return toResponse(saved);
    }


    @Override
    public List<CategoryScheduleResponseDTO> getAllSchedules() {
        return scheduleRepo.findAllByIsDeletedFalse()
                .stream()
                .map(this::toResponse)
                .toList();
    }

//    @Override
//    public List<CategoryScheduleResponseDTO> getSchedulesForCategory(Long categoryId) {
//
//        List<CategoryScheduleAssociations> assocList =
//                assocRepo.findAllByCategoryIdAndIsDeletedFalse(categoryId);
//
//        return assocList.stream()
//                .map(a -> scheduleRepo.findById(a.getScheduleId()).orElse(null))
//                .filter(Objects::nonNull)
//                .map(this::toResponse)
//                .toList();
//    }

    @Override
    public CategoryScheduleResponseDTO updateSchedule(Long scheduleId, CategoryScheduleRequestDTO dto) {
        CategorySchedule schedule = scheduleRepo.findById(scheduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule not found"));

        schedule.setName(dto.getName());
        schedule.setHandle(dto.getHandle());
        schedule.setDescription(dto.getDescription());
        schedule.setIsEveryday(dto.getIsEveryday());
        schedule.setDayOfWeek(dto.getDayOfWeek() != null ?
                dto.getDayOfWeek().toArray(new Integer[0]) : null);
        schedule.setStartTime(LocalTime.parse(dto.getStartTime()));
        schedule.setEndTime(LocalTime.parse(dto.getEndTime()));
        schedule.setIsActive(dto.getIsActive());

        CategorySchedule saved = scheduleRepo.save(schedule);

        return toResponse(saved);
    }

    @Override
    public void deleteSchedule(Long scheduleId) {
        CategorySchedule schedule = scheduleRepo.findById(scheduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule not found"));

        schedule.setIsDeleted(true);
        schedule.setDeletedAt(LocalDateTime.now());
        schedule.setDeletedBy(SecurityContextHolder.getContext().getAuthentication().getName());

        scheduleRepo.save(schedule);
    }

    @Override
    public CategoryScheduleResponseDTO getScheduleById(Long scheduleId) {
        CategorySchedule schedule = scheduleRepo.findByScheduleIdAndIsDeletedFalse(scheduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule not found with ID: " + scheduleId));

        return toResponse(schedule);
    }

    private CategoryScheduleResponseDTO toResponse(CategorySchedule s) {
        return CategoryScheduleResponseDTO.builder()
                .scheduleId(s.getScheduleId())
                .name(s.getName())
                .handle(s.getHandle())
                .description(s.getDescription())
                .isEveryday(s.getIsEveryday())
                .dayOfWeek(s.getDayOfWeek() != null ?
                        Arrays.asList(s.getDayOfWeek()) : null)
                .startTime(s.getStartTime().toString())
                .endTime(s.getEndTime().toString())
                .isActive(s.getIsActive())
                .createdAt(s.getCreatedAt())
                .updatedAt(s.getUpdatedAt())
                .build();
    }
}

