package com.cafe.erp.modules.inventory.supplier.service.implementation;


import com.cafe.erp.modules.catalogue.taxes.entity.Tax;
import com.cafe.erp.modules.catalogue.taxes.respository.TaxRepository;
import com.cafe.erp.modules.inventory.supplier.dto.SupplierTaxCreateRequest;
import com.cafe.erp.modules.inventory.supplier.dto.SupplierTaxDTO;
import com.cafe.erp.modules.inventory.supplier.dto.SupplierTaxUpdateRequest;
import com.cafe.erp.modules.inventory.supplier.entity.Supplier;
import com.cafe.erp.modules.inventory.supplier.entity.SupplierMultipleTax;
import com.cafe.erp.modules.inventory.supplier.mapper.SupplierTaxMapper;
import com.cafe.erp.modules.inventory.supplier.repository.SupplierRepository;
import com.cafe.erp.modules.inventory.supplier.repository.SupplierTaxRepository;
import com.cafe.erp.modules.inventory.supplier.service.SupplierTaxService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.cafe.erp.common.utils.AuditUtils.getCurrentUser;

@Service
@RequiredArgsConstructor
public class SupplierTaxServiceImpl implements SupplierTaxService {

    private final SupplierTaxRepository repo;
    private final SupplierRepository supplierRepo;
    private final TaxRepository taxRepo;
    private final SupplierTaxMapper mapper;

    @Override
    public SupplierTaxDTO create(SupplierTaxCreateRequest req) {

        Supplier supplier = supplierRepo.findById(req.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        Tax tax = taxRepo.findById(req.getTaxId())
                .orElseThrow(() -> new RuntimeException("Tax not found"));

        SupplierMultipleTax e = mapper.toEntity(supplier, tax);
        repo.save(e);

        return mapper.toDTO(e);
    }

    @Override
    public SupplierTaxDTO update(Long id, SupplierTaxUpdateRequest req) {
        SupplierMultipleTax e = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier tax not found"));

        Tax tax = taxRepo.findById(req.getTaxId())
                .orElseThrow(() -> new RuntimeException("Tax not found"));

        mapper.updateEntity(e, tax);
        repo.save(e);

        return mapper.toDTO(e);
    }

    @Override
    public SupplierTaxDTO get(Long id) {
        SupplierMultipleTax e = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));
        return mapper.toDTO(e);
    }

    @Override
    public List<SupplierTaxDTO> listBySupplier(Long supplierId) {
        return repo.findBySupplier_SupplierIdAndIsDeletedFalse(supplierId)
                .stream().map(mapper::toDTO).toList();
    }

    @Override
    public void softDelete(Long id) {
        SupplierMultipleTax e = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        e.setIsDeleted(true);
        e.setDeletedAt(LocalDateTime.now());
        e.setDeletedBy(getCurrentUser());

        repo.save(e);
    }
}
