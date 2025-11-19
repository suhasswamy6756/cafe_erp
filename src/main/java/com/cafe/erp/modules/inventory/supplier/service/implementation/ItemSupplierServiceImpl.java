package com.cafe.erp.modules.inventory.supplier.service.implementation;



import com.cafe.erp.modules.catalogue.item.entity.Item;
import com.cafe.erp.modules.catalogue.item.repository.ItemRepository;
import com.cafe.erp.modules.inventory.supplier.dto.ItemSupplierDTO;
import com.cafe.erp.modules.inventory.supplier.dto.ItemSupplierRequest;
import com.cafe.erp.modules.inventory.supplier.entity.ItemSupplier;
import com.cafe.erp.modules.inventory.supplier.entity.Supplier;
import com.cafe.erp.modules.inventory.supplier.mapper.ItemSupplierMapper;
import com.cafe.erp.modules.inventory.supplier.repository.ItemSupplierRepository;
import com.cafe.erp.modules.inventory.supplier.repository.SupplierRepository;
import com.cafe.erp.modules.inventory.supplier.service.ItemSupplierService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemSupplierServiceImpl implements ItemSupplierService {

    private final ItemSupplierRepository repo;
    private final SupplierRepository supplierRepo;
    private final ItemRepository itemRepo;
    private final ItemSupplierMapper mapper;

    @Override
    public ItemSupplierDTO assign(ItemSupplierRequest req) {

        Supplier supplier = supplierRepo.findById(req.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        Item item = itemRepo.findById(req.getItemId())
                .orElseThrow(() -> new RuntimeException("Item not found"));

        ItemSupplier e = new ItemSupplier();
        e.setItem(item);
        e.setSupplier(supplier);

        repo.save(e);

        return mapper.toDTO(e);
    }

    @Override
    public List<ItemSupplierDTO> listByItem(Long itemId) {
        return repo.findByItem_IdAndIsDeletedFalse(itemId)
                .stream().map(mapper::toDTO).toList();
    }

    @Override
    public List<ItemSupplierDTO> listBySupplier(Long supplierId) {
        return repo.findBySupplier_SupplierIdAndIsDeletedFalse(supplierId)
                .stream().map(mapper::toDTO).toList();
    }

    @Override
    public void delete(Long id) {
       repo.deleteById(id);
    }
}

