package com.cafe.erp.modules.inventory.supplier.service.implementation;


import com.cafe.erp.modules.inventory.supplier.dto.*;
import com.cafe.erp.modules.inventory.supplier.entity.Supplier;
import com.cafe.erp.modules.inventory.supplier.entity.SupplierContact;
import com.cafe.erp.modules.inventory.supplier.mapper.SupplierContactMapper;
import com.cafe.erp.modules.inventory.supplier.repository.SupplierContactRepository;
import com.cafe.erp.modules.inventory.supplier.repository.SupplierRepository;
import com.cafe.erp.modules.inventory.supplier.service.SupplierContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierContactServiceImpl implements SupplierContactService {

    private final SupplierRepository supplierRepository;
    private final SupplierContactRepository contactRepository;
    private final SupplierContactMapper mapper;

    @Override
    public SupplierContactDTO create(SupplierContactCreateRequest req) {
        Supplier supplier = supplierRepository.findById(req.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        SupplierContact contact = mapper.toEntity(supplier, req);
        contactRepository.save(contact);
        return mapper.toDTO(contact);
    }

    @Override
    public SupplierContactDTO update(Long contactId, SupplierContactUpdateRequest req) {
        SupplierContact c = contactRepository.findById(contactId)
                .orElseThrow(() -> new RuntimeException("Contact not found"));

        mapper.updateEntity(c, req);
        contactRepository.save(c);
        return mapper.toDTO(c);
    }

    @Override
    public SupplierContactDTO get(Long contactId) {
        SupplierContact c = contactRepository.findById(contactId)
                .orElseThrow(() -> new RuntimeException("Contact not found"));
        return mapper.toDTO(c);
    }

    @Override
    public List<SupplierContactDTO> listBySupplier(Long supplierId) {
        return contactRepository.findBySupplier_SupplierIdAndIsDeletedFalse(supplierId)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public void softDelete(Long contactId) {
        SupplierContact c = contactRepository.findById(contactId)
                .orElseThrow(() -> new RuntimeException("Contact not found"));

        c.setIsDeleted(true);
        c.setDeletedAt(LocalDateTime.now());
        c.setDeletedBy("system");
        contactRepository.save(c);
    }
}
