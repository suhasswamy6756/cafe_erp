package com.cafe.erp.modules.inventory.goods_receipt_note.controller;

import com.cafe.erp.common.model.ApiResponse;
import com.cafe.erp.modules.inventory.goods_receipt_note.dto.GRNCreateRequestDTO;
import com.cafe.erp.modules.inventory.goods_receipt_note.dto.GRNCreateResponseDTO;
import com.cafe.erp.modules.inventory.goods_receipt_note.dto.GRNResponseDTO;
import com.cafe.erp.modules.inventory.goods_receipt_note.dto.GRNSubmitRequestDTO;
import com.cafe.erp.modules.inventory.goods_receipt_note.service.GRNService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory/grn")
@RequiredArgsConstructor
public class GRNController {

    private final GRNService grnService;

    // --------------------------------------------------------
    // CREATE GRN FROM PURCHASE ORDER
    // --------------------------------------------------------
    @PostMapping
    public ResponseEntity<ApiResponse<GRNCreateResponseDTO>> createGRN(@RequestBody GRNCreateRequestDTO request) {
        GRNCreateResponseDTO result = grnService.createGRN(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("GRN created successfully", result, 201));
    }

    // --------------------------------------------------------
    // UPDATE A GRN ITEM
    // --------------------------------------------------------
//    @PutMapping("/{grnId}/items/{itemId}")
//    public ResponseEntity<ApiResponse<GRNItemDTO>> updateGRNItem(
//            @PathVariable Long grnId,
//            @PathVariable Long itemId,
//            @RequestBody GRNItemUpdateRequest request
//    ) {
//        GRNItemDTO result = grnService.updateGRNItem(grnId, itemId, request);
//        return ResponseEntity.ok(ApiResponse.success("GRN item updated", result));
//    }

    // --------------------------------------------------------
    // REJECT ITEM
    // --------------------------------------------------------
//    @PostMapping("/{grnItemId}/reject")
//    public ResponseEntity<ApiResponse<GRNRejectionDTO>> rejectItem(
//            @PathVariable Long grnItemId,
//            @RequestBody GRNRejectReq request
//    ) {
//        GRNRejectionDTO result = grnService.rejectItem(grnItemId, request);
//        return ResponseEntity.ok(ApiResponse.success("Item rejected successfully", result));
//    }

    // --------------------------------------------------------
    // SUBMIT GRN
    // --------------------------------------------------------
    @PutMapping("/{grnId}/submit")
    public ResponseEntity<ApiResponse<GRNResponseDTO>> submitGRN(@RequestBody GRNSubmitRequestDTO req) {
        GRNResponseDTO response = grnService.submitGRN(req);
        return ResponseEntity.ok(ApiResponse.success("GRN submitted successfully", response, 200));
    }

    // --------------------------------------------------------
    // APPROVE (CLOSE) GRN
    // --------------------------------------------------------
    @PutMapping("/{grnId}/approve")
    public ResponseEntity<ApiResponse<GRNResponseDTO>> approveGRN(@PathVariable Long grnId) {
        GRNResponseDTO response = grnService.approveGRN(grnId);
        return ResponseEntity.ok(ApiResponse.success("GRN approved successfully", response, 200));
    }

    // --------------------------------------------------------
    // GET ONE GRN
    // --------------------------------------------------------
    @GetMapping("/{grnId}")
    public ResponseEntity<ApiResponse<GRNResponseDTO>> getGRN(@PathVariable Long grnId) {
        GRNResponseDTO dto = grnService.getGRN(grnId);
        return ResponseEntity.ok(ApiResponse.success("GRN fetched successfully", dto, 200));
    }

    // --------------------------------------------------------
    // LIST ALL GRNs (Optional filters)
    // --------------------------------------------------------
    @GetMapping
    public ResponseEntity<ApiResponse<List<GRNResponseDTO>>> getAllGRNs() {
        List<GRNResponseDTO> list = grnService.listGRN();
        return ResponseEntity.ok(ApiResponse.success("GRN List fetched", list, 200));
    }
}

