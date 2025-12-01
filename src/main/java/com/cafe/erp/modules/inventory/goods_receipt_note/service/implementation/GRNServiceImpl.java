package com.cafe.erp.modules.inventory.goods_receipt_note.service.implementation;

import com.cafe.erp.common.enums.GRNStatus;
import com.cafe.erp.common.enums.PurchaseStatus;
import com.cafe.erp.common.exception.ResourceNotFoundException;

import com.cafe.erp.modules.admin.location.entity.Location;
import com.cafe.erp.modules.admin.location.repository.LocationsRepository;
import com.cafe.erp.modules.inventory.goods_receipt_note.dto.GRNCreateRequestDTO;
import com.cafe.erp.modules.inventory.goods_receipt_note.dto.GRNCreateResponseDTO;
import com.cafe.erp.modules.inventory.goods_receipt_note.dto.GRNResponseDTO;
import com.cafe.erp.modules.inventory.goods_receipt_note.dto.GRNSubmitRequestDTO;
import com.cafe.erp.modules.inventory.goods_receipt_note.entity.GRNHeader;
import com.cafe.erp.modules.inventory.goods_receipt_note.entity.GRNItem;
import com.cafe.erp.modules.inventory.goods_receipt_note.entity.GRNRejectionLog;
import com.cafe.erp.modules.inventory.goods_receipt_note.mapper.GRNMapper;
import com.cafe.erp.modules.inventory.goods_receipt_note.repository.GRNHeaderRepository;
import com.cafe.erp.modules.inventory.goods_receipt_note.repository.GRNItemRepository;
import com.cafe.erp.modules.inventory.goods_receipt_note.repository.GRNRejectionLogRepository;


import com.cafe.erp.modules.inventory.goods_receipt_note.service.GRNService;
import com.cafe.erp.modules.inventory.material.repository.MaterialRepository;
import com.cafe.erp.modules.inventory.purchase.entity.PurchaseOrder;
import com.cafe.erp.modules.inventory.purchase.repository.PurchaseOrderRepository;
import com.cafe.erp.modules.inventory.stock.entity.Stock;
import com.cafe.erp.modules.inventory.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.cafe.erp.common.utils.AuditUtils.getCurrentUser;

@Service
@RequiredArgsConstructor
@Transactional
public class GRNServiceImpl implements GRNService {

    private final GRNHeaderRepository grnRepo;
    private final GRNItemRepository grnItemRepo;
    private final GRNRejectionLogRepository rejectRepo;
    private final PurchaseOrderRepository poRepo;
    private final LocationsRepository locationRepo;
    private final MaterialRepository materialRepo;
    private final StockRepository stockRepo;
    private final GRNMapper mapper;

    // --------------------  CREATE GRN --------------------
    @Override
    public GRNCreateResponseDTO createGRN(GRNCreateRequestDTO req) {

        PurchaseOrder po = poRepo.findById(req.getPurchaseOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Purchase Order not found"));

        if (po.getStatus() != PurchaseStatus.APPROVED) {
            throw new IllegalStateException("PO must be APPROVED to create GRN");
        }

        Location location = locationRepo.findById(req.getLocationId())
                .orElseThrow(() -> new ResourceNotFoundException("Location not found"));

        GRNHeader header = GRNHeader.builder()
                .purchaseOrder(po)
                .supplier(po.getSupplier())
                .locationId(location.getLocationId())
                .status(GRNStatus.DRAFT)
                .grnNumber("GRN-" + UUID.randomUUID().toString().substring(0, 8))
                .receivedBy(getCurrentUser())
                .receivedDate(LocalDateTime.now())
                .build();

        GRNHeader savedHeader = grnRepo.save(header);

        // 🔥 Automatically create GRN items from PO items
        List<GRNItem> grnItems = po.getItems().stream().map(poItem -> {

            GRNItem item = GRNItem.builder()
                    .grnHeader(savedHeader)
                    .material(poItem.getMaterial())
                    .orderedQty(poItem.getOrderedQty())
                    .deliveredQty(BigDecimal.ZERO)
                    .acceptedQty(BigDecimal.ZERO)
                    .rejectedQty(BigDecimal.ZERO)
                    .unitCost(poItem.getUnitPrice())
                    .uomCode(poItem.getUomCode())
                    .totalCost(BigDecimal.ZERO)
                    .build();

            return item;
        }).collect(Collectors.toList());

        grnItemRepo.saveAll(grnItems);
        savedHeader.setItems(grnItems);

        return mapper.toCreateResponse(savedHeader);
    }


    // -------------------- SUBMIT GRN --------------------
    @Override
    public GRNResponseDTO submitGRN(GRNSubmitRequestDTO req) {

        GRNHeader grn = grnRepo.findById(req.getGrnId())
                .orElseThrow(() -> new ResourceNotFoundException("GRN not found"));

        if (grn.getStatus() != GRNStatus.DRAFT)
            throw new IllegalStateException("Only DRAFT GRN can be submitted");

        req.getItems().forEach(update -> {
            GRNItem item = grnItemRepo.findById(update.getGrnItemId())
                    .orElseThrow(() -> new ResourceNotFoundException("GRN Item not found"));

            BigDecimal delivered = update.getDeliveredQty();
            BigDecimal sumAcceptedRejected = update.getAcceptedQty().add(update.getRejectedQty());

            if (delivered.compareTo(sumAcceptedRejected) < 0) {
                throw new RuntimeException("Accepted + Rejected cannot exceed Delivered quantity");
            }

//            throw new IllegalArgumentException("Accepted + Rejected cannot exceed Delivered Qty");

            item.setDeliveredQty(update.getDeliveredQty());
            item.setAcceptedQty(update.getAcceptedQty());
            item.setRejectedQty(update.getRejectedQty());
            item.setBatchNumber(update.getBatchNo());
            item.setExpiryDate(update.getExpiryDate());
            item.setTotalCost((item.getAcceptedQty()).multiply(item.getUnitCost()));

            if (item.getRejectedQty().compareTo(BigDecimal.ZERO) > 0) {
                rejectRepo.save(GRNRejectionLog.builder()
                        .grnItem(item)
                        .rejectedQty(item.getRejectedQty())
                        .reason(null)
                        .actionTaken(null)
                        .build());
            }

            grnItemRepo.save(item);
        });

        grn.setStatus(GRNStatus.APPROVED);
        return mapper.toResponse(grnRepo.save(grn));
    }

    // -------------------- APPROVE GRN --------------------
    @Override
    public GRNResponseDTO approveGRN(Long grnId) {

        GRNHeader grn = grnRepo.findById(grnId)
                .orElseThrow(() -> new ResourceNotFoundException("GRN not found"));

        if (grn.getStatus() != GRNStatus.APPROVED)
            throw new IllegalStateException("Only SUBMITTED GRN can be APPROVED");

        // Apply stock update
        grn.getItems().forEach(item -> {

            Stock stock = Stock.builder()
                    .material(item.getMaterial())
                    .location(locationRepo.findById(grn.getLocationId())
                            .orElseThrow(() -> new ResourceNotFoundException("Location not found")))
                    .batchNo(item.getBatchNumber())
                    .expiryDate(item.getExpiryDate())
                    .quantity(item.getAcceptedQty())
                    .unitCost(item.getUnitCost())
                    .uomCode(item.getUomCode())
//                    .sourceType("GRN")
//                    .sourceId(grn.getGrnId())
                    .build();

            stockRepo.save(stock);
        });

        grn.setStatus(GRNStatus.APPROVED);
        return mapper.toResponse(grnRepo.save(grn));
    }

    // -------------------- GET GRN --------------------
    @Override
    public GRNResponseDTO getGRN(Long grnId) {
        return mapper.toResponse(grnRepo.findById(grnId)
                .orElseThrow(() -> new ResourceNotFoundException("GRN not found")));
    }

    // -------------------- LIST GRN --------------------
    @Override
    public List<GRNResponseDTO> listGRN() {
        return grnRepo.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
    }
}
