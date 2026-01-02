package com.cafe.erp.modules.inventory.supplier.service.implementation;

import com.cafe.erp.modules.inventory.supplier.dto.UomCreateRequest;
import com.cafe.erp.modules.inventory.supplier.dto.UomDTO;
import com.cafe.erp.modules.inventory.supplier.dto.UomUpdateRequest;
import com.cafe.erp.modules.inventory.supplier.entity.Uom;
import com.cafe.erp.modules.inventory.supplier.mapper.UomMapper;
import com.cafe.erp.modules.inventory.supplier.repository.UomRepository;
import com.cafe.erp.modules.inventory.supplier.service.UomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UomServiceImpl implements UomService {

    private final UomRepository repo;
    private final UomMapper mapper;

    @Override
    public UomDTO create(UomCreateRequest req) {
        Uom u = mapper.toEntity(req);
        return mapper.toDTO(repo.save(u));
    }

    @Override
    public UomDTO update(String code, UomUpdateRequest req) {
        Uom u = repo.findById(code)
                .filter(uom -> !Boolean.TRUE.equals(uom.getIsDeleted()))
                .orElseThrow(() -> new RuntimeException("UOM not found or deleted"));

        mapper.updateEntity(u, req);

        return mapper.toDTO(repo.save(u));
    }

    @Override
    public UomDTO get(String code) {
        Uom u = repo.findById(code)
                .filter(uom -> !Boolean.TRUE.equals(uom.getIsDeleted()))
                .orElseThrow(() -> new RuntimeException("UOM not found or deleted"));

        return mapper.toDTO(u);
    }

    @Override
    public List<UomDTO> getAll() {
        return repo.findByIsDeletedFalse()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }


    @Override
    public void delete(String code) {
        Uom u = repo.findById(code)
                .filter(uom -> !Boolean.TRUE.equals(uom.getIsDeleted()))
                .orElseThrow(() -> new RuntimeException("UOM not found or already deleted"));

        u.setIsDeleted(true);
        u.setDeletedAt(LocalDateTime.now());
        u.setDeletedBy("SYSTEM"); // you can set current user here

        repo.save(u);
    }
}

