package com.cafe.erp.modules.admin.location.service.implementation;

import com.cafe.erp.modules.admin.location.dto.LocationScheduleRequestDTO;
import com.cafe.erp.modules.admin.location.dto.LocationScheduleResponseDTO;
import com.cafe.erp.modules.admin.location.entity.LocationSchedule;
import com.cafe.erp.modules.admin.location.repository.LocationScheduleRepository;
import com.cafe.erp.modules.admin.location.service.LocationScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationScheduleServiceImpl implements LocationScheduleService {

    private final LocationScheduleRepository repository;

    @Override
    public LocationScheduleResponseDTO create(LocationScheduleRequestDTO dto) {
        LocationSchedule schedule = mapToEntity(dto);
        repository.save(schedule);
        return mapToDTO(schedule);
    }

    @Override
    public LocationScheduleResponseDTO update(Long id, LocationScheduleRequestDTO dto) {
        LocationSchedule schedule = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        schedule.setName(dto.getName());
        schedule.setHandle(dto.getHandle());
        schedule.setDescription(dto.getDescription());
        schedule.setIsEveryday(dto.isEveryday());
        schedule.setDayOfWeek(dto.getDayOfWeek());
        schedule.setStartTime(dto.getStartTime());
        schedule.setEndTime(dto.getEndTime());
        schedule.setIsActive(dto.isActive());

        repository.save(schedule);
        return mapToDTO(schedule);
    }

    @Override
    public void delete(Long id) {
        LocationSchedule schedule = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
        schedule.setIsDeleted(true);
        repository.save(schedule);
    }

    @Override
    public LocationScheduleResponseDTO getById(Long id) {
        LocationSchedule schedule = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
        return mapToDTO(schedule);
    }

    @Override
    public List<LocationScheduleResponseDTO> getAll() {
        return repository.findAll()
                .stream()
                .filter(s -> !Boolean.TRUE.equals(s.getIsDeleted()))
                .map(this::mapToDTO)
                .toList();
    }

    private LocationSchedule mapToEntity(LocationScheduleRequestDTO dto) {
        LocationSchedule s = new LocationSchedule();
        s.setName(dto.getName());
        s.setHandle(dto.getHandle());
        s.setDescription(dto.getDescription());
        s.setIsEveryday(dto.isEveryday());
        s.setDayOfWeek(dto.getDayOfWeek());
        s.setStartTime(dto.getStartTime());
        s.setEndTime(dto.getEndTime());
        s.setIsActive(dto.isActive());
        return s;
    }

    private LocationScheduleResponseDTO mapToDTO(LocationSchedule s) {
        LocationScheduleResponseDTO dto = new LocationScheduleResponseDTO();
        dto.setScheduleId(s.getScheduleId());
        dto.setName(s.getName());
        dto.setHandle(s.getHandle());
        dto.setDescription(s.getDescription());
        dto.setEveryday(s.getIsEveryday());
        dto.setDayOfWeek(s.getDayOfWeek());
        dto.setStartTime(s.getStartTime());
        dto.setEndTime(s.getEndTime());
        dto.setActive(s.getIsActive());
        dto.setCreatedAt(s.getCreatedAt());
        dto.setUpdatedAt(s.getUpdatedAt());
        return dto;
    }
}

