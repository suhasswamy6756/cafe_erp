package com.cafe.erp.modules.inventory.supplier.service.implementation;


import com.cafe.erp.modules.inventory.supplier.dto.*;
import com.cafe.erp.modules.inventory.supplier.entity.Supplier;
import com.cafe.erp.modules.inventory.supplier.entity.SupplierAddress;
import com.cafe.erp.modules.inventory.supplier.mapper.SupplierAddressMapper;
import com.cafe.erp.modules.inventory.supplier.repository.SupplierAddressRepository;
import com.cafe.erp.modules.inventory.supplier.repository.SupplierRepository;
import com.cafe.erp.modules.inventory.supplier.service.SupplierAddressService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SupplierAddressServiceImpl implements SupplierAddressService {

    private final SupplierRepository supplierRepository;
    private final SupplierAddressRepository addressRepository;
    private final SupplierAddressMapper mapper;

    @Override
    public SupplierAddressDTO create(SupplierAddressCreateRequest req) {
        Supplier supplier = supplierRepository.findById(req.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        SupplierAddress address = mapper.toEntity(supplier, req);
        addressRepository.save(address);
        return mapper.toDTO(address);
    }

    @Override
    public SupplierAddressDTO update(Long addressId, SupplierAddressUpdateRequest req) {
        SupplierAddress address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        mapper.updateEntity(address, req);
        addressRepository.save(address);
        return mapper.toDTO(address);
    }

    @Override
    public SupplierAddressDTO get(Long addressId) {
        SupplierAddress address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));
        return mapper.toDTO(address);
    }

    @Override
    public List<SupplierAddressDTO> listBySupplier(Long supplierId) {
        return addressRepository.findBySupplier_SupplierIdAndIsDeletedFalse(supplierId)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public void softDelete(Long addressId) {
        SupplierAddress address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        address.setIsDeleted(true);
        address.setDeletedAt(LocalDateTime.now());
        address.setDeletedBy("system");

        addressRepository.save(address);
    }
}

