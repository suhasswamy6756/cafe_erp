package com.cafe.erp.modules.inventory.supplier.service.implementation;


import com.cafe.erp.modules.inventory.supplier.dto.SupplierCreateRequest;
import com.cafe.erp.modules.inventory.supplier.dto.SupplierDTO;
import com.cafe.erp.modules.inventory.supplier.dto.SupplierUpdateRequest;
import com.cafe.erp.modules.inventory.supplier.entity.Supplier;
import com.cafe.erp.modules.inventory.supplier.entity.SupplierAddress;
import com.cafe.erp.modules.inventory.supplier.entity.SupplierContact;
import com.cafe.erp.modules.inventory.supplier.mapper.SupplierAddressMapper;
import com.cafe.erp.modules.inventory.supplier.mapper.SupplierContactMapper;
import com.cafe.erp.modules.inventory.supplier.mapper.SupplierMapper;
import com.cafe.erp.modules.inventory.supplier.repository.SupplierAddressRepository;
import com.cafe.erp.modules.inventory.supplier.repository.SupplierContactRepository;
import com.cafe.erp.modules.inventory.supplier.repository.SupplierRepository;
import com.cafe.erp.modules.inventory.supplier.service.SupplierService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.cafe.erp.common.utils.AuditUtils.getCurrentUser;

@Service
@RequiredArgsConstructor
@Transactional
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierAddressRepository addressRepository;
    private final SupplierContactRepository contactRepository;

    private final SupplierMapper supplierMapper;
    private final SupplierAddressMapper addressMapper;
    private final SupplierContactMapper contactMapper;

    // ----------------------------------------------------
    // CREATE SUPPLIER
    @Override
    public SupplierDTO createSupplier(SupplierCreateRequest request) {

        // 1️⃣ Convert to Supplier entity (parent)
        Supplier supplier = supplierMapper.toEntity(request);

        // Initialize empty lists so JPA doesn't throw null issues
        supplier.setContacts(new ArrayList<>());
        supplier.setAddresses(new ArrayList<>());

        // 2️⃣ First save supplier (so it gets supplierId)
        Supplier saved = supplierRepository.save(supplier);

        // 3️⃣ Save contacts and attach them to parent
        if (request.getContacts() != null) {
            request.getContacts().forEach(reqContact -> {
                SupplierContact contact = contactMapper.toEntity(saved, reqContact);
                saved.getContacts().add(contact);     // attach to parent
            });
        }

        // 4️⃣ Save addresses and attach to parent
        if (request.getAddresses() != null) {
            request.getAddresses().forEach(reqAddress -> {
                SupplierAddress address = addressMapper.toEntity(saved, reqAddress);
                saved.getAddresses().add(address);    // attach to parent
            });
        }

        // 5️⃣ Save again so children persist with supplier_id foreign key
        supplierRepository.save(saved);

        // 6️⃣ Reload full supplier WITH children to ensure accuracy
        Supplier fullSupplier = supplierRepository.findById(saved.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier missing after save"));

        // 7️⃣ Convert to DTO and return
        return supplierMapper.toDTO(fullSupplier);
    }


    // ----------------------------------------------------
    // UPDATE SUPPLIER
    // ----------------------------------------------------
    @Override
    public SupplierDTO updateSupplier(Long supplierId, SupplierUpdateRequest request) {

        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new EntityNotFoundException("Supplier not found with ID " + supplierId));

        supplierMapper.updateEntity(supplier, request);

        Supplier updated = supplierRepository.save(supplier);

        // Update contacts
        // Clear old contacts
        contactRepository.deleteBySupplier_SupplierId(supplierId);

// Add new contacts
        if (request.getContacts() != null) {
            request.getContacts().forEach(c -> {
                SupplierContact contact = contactMapper.toEntity(updated, c); // <-- NOW OK
                contactRepository.save(contact);
            });
        }


        // Update addresses
        addressRepository.deleteBySupplier_SupplierId(supplierId);
        if (request.getAddresses() != null) {
            request.getAddresses().forEach(a -> {
                SupplierAddress address = addressMapper.toEntity(updated, a);
                address.setSupplier(updated);
                addressRepository.save(address);
            });
        }

        return supplierMapper.toDTO(updated);
    }

    // ----------------------------------------------------
    // GET ONE SUPPLIER
    // ----------------------------------------------------
    @Override
    public SupplierDTO getSupplier(Long supplierId) {

        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new EntityNotFoundException("Supplier not found"));

        return supplierMapper.toDTO(supplier);
    }

    // ----------------------------------------------------
    // GET ALL SUPPLIERS
    // ----------------------------------------------------
    @Override
    public List<SupplierDTO> getAllSuppliers() {

        return supplierRepository.findAllByIsDeletedFalse()
                .stream()
                .map(supplierMapper::toDTO)
                .toList();
    }

    // ----------------------------------------------------
    // DELETE SUPPLIER
    // ----------------------------------------------------
    @Override
    public void deleteSupplier(Long supplierId) {

        if (!supplierRepository.existsById(supplierId)) {
            throw new EntityNotFoundException("Supplier not found");
        }

        // Soft delete handled by BaseEntity
        Supplier supplier = supplierRepository.findById(supplierId).get();
        supplier.setIsDeleted(true);
        supplier.setDeletedAt(LocalDateTime.now());
        supplier.setDeletedBy(getCurrentUser());

        supplierRepository.save(supplier);
    }
}

