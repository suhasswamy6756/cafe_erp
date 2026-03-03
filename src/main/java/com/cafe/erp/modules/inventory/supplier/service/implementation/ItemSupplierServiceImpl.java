package com.cafe.erp.modules.inventory.supplier.service.implementation;


import com.cafe.erp.modules.inventory.material.entity.Material;
import com.cafe.erp.modules.inventory.material.repository.MaterialRepository;
import com.cafe.erp.modules.inventory.supplier.dto.MaterialSupplierDTO;
import com.cafe.erp.modules.inventory.supplier.dto.MaterialSupplierRequest;
import com.cafe.erp.modules.inventory.supplier.entity.MaterialSupplier;
import com.cafe.erp.modules.inventory.supplier.entity.Supplier;
import com.cafe.erp.modules.inventory.supplier.mapper.ItemSupplierMapper;
import com.cafe.erp.modules.inventory.supplier.repository.MaterialSupplierRepository;
import com.cafe.erp.modules.inventory.supplier.repository.SupplierRepository;
import com.cafe.erp.modules.inventory.supplier.service.ItemSupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.cafe.erp.common.utils.AuditUtils.getCurrentUser;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemSupplierServiceImpl implements ItemSupplierService {

    private final MaterialSupplierRepository repo;
    private final SupplierRepository supplierRepo;
    private final MaterialRepository materialRepository;
    private final ItemSupplierMapper mapper;

    @Override
    public MaterialSupplierDTO assign(MaterialSupplierRequest req) {

        Supplier supplier = supplierRepo.findById(req.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        Material material = materialRepository.findById(req.getMaterialId())
                .orElseThrow(() -> new RuntimeException("Item not found"));

        MaterialSupplier e = new MaterialSupplier();
        e.setMaterial(material);
        e.setSupplier(supplier);

        repo.save(e);

        return mapper.toDTO(e);
    }

    @Override
    public List<MaterialSupplierDTO> listByItem(Long itemId) {
        return repo.findByMaterial_MaterialIdAndIsDeletedFalse(itemId)
                .stream().map(mapper::toDTO).toList();
    }

    @Override
    public List<MaterialSupplierDTO> listBySupplier(Long supplierId) {
        return repo.findBySupplier_SupplierIdAndIsDeletedFalse(supplierId)
                .stream().map(mapper::toDTO).toList();
    }

    @Override
    public void delete(Long id) {
        MaterialSupplier e = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Item supplier not found"));
        e.setIsDeleted(true);
        e.setDeletedAt(LocalDateTime.now());
        e.setDeletedBy(getCurrentUser());

        repo.save(e);
    }
}

