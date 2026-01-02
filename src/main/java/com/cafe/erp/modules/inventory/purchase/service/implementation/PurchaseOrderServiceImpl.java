package com.cafe.erp.modules.inventory.purchase.service.implementation;


import com.cafe.erp.common.enums.PurchaseStatus;
import com.cafe.erp.common.exception.ResourceNotFoundException;
import com.cafe.erp.modules.inventory.material.entity.Material;
import com.cafe.erp.modules.inventory.material.repository.MaterialRepository;
import com.cafe.erp.modules.inventory.purchase.dto.PurchaseOrderCreateRequest;
import com.cafe.erp.modules.inventory.purchase.dto.PurchaseOrderDTO;
import com.cafe.erp.modules.inventory.purchase.entity.PurchaseOrder;
import com.cafe.erp.modules.inventory.purchase.entity.PurchaseOrderItem;
import com.cafe.erp.modules.inventory.purchase.mapper.PurchaseOrderMapper;
import com.cafe.erp.modules.inventory.purchase.repository.PurchaseOrderItemRepository;
import com.cafe.erp.modules.inventory.purchase.repository.PurchaseOrderRepository;
import com.cafe.erp.modules.inventory.purchase.service.PurchaseOrderService;
import com.cafe.erp.modules.inventory.supplier.entity.Supplier;
import com.cafe.erp.modules.inventory.supplier.repository.SupplierRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private final PurchaseOrderRepository poRepo;
    private final PurchaseOrderItemRepository poItemRepo;
    private final SupplierRepository supplierRepo;
    private final MaterialRepository materialRepo;
    private final PurchaseOrderMapper mapper;

    @Override
    public PurchaseOrderDTO createPurchaseOrder(PurchaseOrderCreateRequest req) {

        Supplier supplier = supplierRepo.findById(req.getSupplierId())
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

        PurchaseOrder po = mapper.toEntity(req, supplier);
        poRepo.save(po);

        req.getItems().forEach(itemReq -> {
            Material material = materialRepo.findById(itemReq.getMaterialId())
                    .orElseThrow(() -> new ResourceNotFoundException("Material not found"));

            PurchaseOrderItem item = mapper.toEntity(itemReq, po, material);
            poItemRepo.save(item);
        });

        return mapper.toDTO(poRepo.findById(po.getPoId()).get());
    }

    @Override
    public List<PurchaseOrderDTO> getAll() {
        return poRepo.findAll().stream().map(mapper::toDTO).toList();
    }

    @Override
    public PurchaseOrderDTO getById(Long id) {
        return mapper.toDTO(poRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase Order not found")));
    }

    @Override
    public PurchaseOrderDTO approve(Long id) {
        PurchaseOrder po = poRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase Order not found"));

        if (po.getStatus() != PurchaseStatus.SUBMITTED) {
            throw new IllegalStateException("Only SUBMITTED orders can be approved!");
        }

        po.setStatus(PurchaseStatus.APPROVED);
        poRepo.save(po);

        return mapper.toDTO(po);
    }


    @Override
    public PurchaseOrderDTO submit(Long id) {
        PurchaseOrder po = poRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase Order not found"));

        if (po.getStatus() != PurchaseStatus.DRAFT) {
            throw new IllegalStateException("Only DRAFT orders can be submitted!");
        }

        po.setStatus(PurchaseStatus.SUBMITTED);
        poRepo.save(po);

        return mapper.toDTO(po);
    }

}

