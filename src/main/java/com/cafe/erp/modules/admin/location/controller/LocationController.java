package com.cafe.erp.modules.admin.location.controller;



import com.cafe.erp.common.model.ApiResponse;
import com.cafe.erp.modules.admin.location.dto.LocationRequestDTO;
import com.cafe.erp.modules.admin.location.dto.LocationResponseDTO;
import com.cafe.erp.modules.admin.location.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;


    @PostMapping
    public ResponseEntity<ApiResponse<LocationResponseDTO>> createLocation(@RequestBody LocationRequestDTO req) {
        return ResponseEntity.ok(ApiResponse.success("Location created", locationService.createLocation(req), 201));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<LocationResponseDTO>> updateLocation(@PathVariable Long id, @RequestBody LocationRequestDTO req) {
        return ResponseEntity.ok(ApiResponse.success("Location updated", locationService.updateLocation(id, req), 200));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<LocationResponseDTO>> deleteLocation(@PathVariable Long id) {
        locationService.deleteLocation(id);
        return ResponseEntity.ok(ApiResponse.success("Location deleted", null,200));
    }



    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LocationResponseDTO>> getLocation(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Location fetched", locationService.getLocation(id), 200));
    }



    @GetMapping
    public ResponseEntity<ApiResponse<List<LocationResponseDTO>>> getAllLocations() {
        return ResponseEntity.ok(ApiResponse.success("Locations fetched", locationService.getAllLocations(), 200));
    }
}

