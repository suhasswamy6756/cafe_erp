package com.cafe.erp.modules.catalogue.category_timings.controller;


import com.cafe.erp.common.model.ApiResponse;
import com.cafe.erp.modules.catalogue.category_timings.dto.CategoryScheduleAssignDTO;
import com.cafe.erp.modules.catalogue.category_timings.dto.CategoryScheduleRequestDTO;
import com.cafe.erp.modules.catalogue.category_timings.dto.CategoryScheduleResponseDTO;
import com.cafe.erp.modules.catalogue.category_timings.service.CategoryScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/category-schedules")
@RequiredArgsConstructor
public class CategoryScheduleController {

    private final CategoryScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryScheduleResponseDTO>> createSchedule(
            @RequestBody CategoryScheduleRequestDTO dto) {

        return ResponseEntity.ok(
                ApiResponse.success("Schedule created", scheduleService.createSchedule(dto), 201)
        );
    }



    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryScheduleResponseDTO>>> getAllSchedules() {
        return ResponseEntity.ok(
                ApiResponse.success("Schedules fetched", scheduleService.getAllSchedules(), 200)
        );
    }

//    @GetMapping("/category/{id}")
//    public ResponseEntity<ApiResponse<List<CategoryScheduleResponseDTO>>> getCategorySchedules(
//            @PathVariable Long id) {
//
//        return ResponseEntity.ok(
//                ApiResponse.success("Category schedules fetched",
//                        scheduleService.getSchedulesForCategory(id), 200)
//        );
//    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryScheduleResponseDTO>> updateSchedule(
            @PathVariable Long id,
            @RequestBody CategoryScheduleRequestDTO dto) {

        return ResponseEntity.ok(
                ApiResponse.success("Schedule updated", scheduleService.updateSchedule(id, dto), 200)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.ok(ApiResponse.success("Schedule deleted successfully", null, 200));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryScheduleResponseDTO>> getScheduleById(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.success("Schedule fetched", scheduleService.getScheduleById(id), 200)
        );
    }
}
