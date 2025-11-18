package com.cafe.erp.modules.admin.location.controller;

import com.cafe.erp.common.model.ApiResponse;
import com.cafe.erp.modules.admin.location.dto.LocationScheduleRequestDTO;
import com.cafe.erp.modules.admin.location.dto.LocationScheduleResponseDTO;
import com.cafe.erp.modules.admin.location.service.LocationScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/location-schedules")
@RequiredArgsConstructor
public class LocationScheduleController {

    private final LocationScheduleService service;

    @PostMapping
    public ResponseEntity<ApiResponse<LocationScheduleResponseDTO>> create(@RequestBody LocationScheduleRequestDTO dto) {
        return ResponseEntity.ok(ApiResponse.success("Location schedule created", service.create(dto), 201));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<LocationScheduleResponseDTO>> update(
            @PathVariable Long id,
            @RequestBody LocationScheduleRequestDTO dto
    ) {
        return ResponseEntity.ok(ApiResponse.success("Location schedule updated", service.update(id, dto), 200));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LocationScheduleResponseDTO>> getById(@PathVariable Long id) {
    return ResponseEntity.ok(ApiResponse.success("Location schedule retrieved", service.getById(id), 200));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<LocationScheduleResponseDTO>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success("Location schedules retrieved", service.getAll(), 200));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Location schedule deleted", null, 200));
    }
}

